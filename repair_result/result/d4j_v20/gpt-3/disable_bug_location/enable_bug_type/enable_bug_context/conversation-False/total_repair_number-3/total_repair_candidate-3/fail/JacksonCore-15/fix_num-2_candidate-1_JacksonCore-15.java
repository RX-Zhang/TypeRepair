public JsonToken nextToken() throws IOException
{
	// Check for _allowMultipleMatches - if false and at least one match found (_matchCount > 0),
	// and no buffered tokens (_exposedContext == null), then stop further matches according to filter rules.

    // First, if there are buffered tokens exposed, return tokens from there first.
    TokenFilterContext ctxt = _exposedContext;

    if (ctxt != null) {
        while (true) {
            JsonToken t = ctxt.nextTokenToRead();
            if (t != null) {
                _currToken = t;
                return t;
            }
            // all done with buffered stuff?
            if (ctxt == _headContext) {
                _exposedContext = null;

                // Need to handle possible situation where current token is still valid
                // Note: delegate.getCurrentToken() may return null
                JsonToken t2 = delegate.getCurrentToken();
                if (t2 != JsonToken.START_ARRAY && t2 != JsonToken.START_OBJECT) {
                    _currToken = t2;
                    return t2;
                }
                // else fall through to continue normal token processing
                break;
            }
            // If not done, traverse down the chain to find next token to read
            ctxt = _headContext.findChildOf(ctxt);
            _exposedContext = ctxt;
            if (ctxt == null) {
                throw _constructError("Unexpected problem: chain of filtered context broken");
            }
        }
    }

    // If multiple matches are not allowed and at least one match was found, and no buffering,
    // then skip further matching tokens and return null
    if (!_allowMultipleMatches && _matchCount > 0 && _exposedContext == null) {
        // Skip tokens until matching end or EOF
        while (true) {
            JsonToken t = delegate.nextToken();
            if (t == null) {
                _currToken = null;
                return null;
            }
            // If this token is structural end, and matches context, may end early
            if (t.isStructEnd()) {
                // If context stack is empty already, no more tokens to process
                if (_headContext == null || _headContext.getParent() == null) {
                    _currToken = null;
                    return null;
                }
                // else move up context (simulate)
                _headContext = _headContext.getParent();
            }
            // Keep skipping otherwise
        }
    }

    // Otherwise, get next token from delegate
    JsonToken t = delegate.nextToken();
    if (t == null) {
        return (_currToken = null);
    }

    TokenFilter f;

    switch (t.id()) {
    case ID_START_ARRAY:
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildArrayContext(f, true);
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return (_currToken = t);
        }
        if (f == null) {
            delegate.skipChildren();
            break;
        }
        f = _headContext.checkValue(f);
        if (f == null) {
            delegate.skipChildren();
            break;
        }
        if (f != TokenFilter.INCLUDE_ALL) {
            f = f.filterStartArray();
        }
        _itemFilter = f;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildArrayContext(f, true);
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return (_currToken = t);
        }
        _headContext = _headContext.createChildArrayContext(f, false);

        if (_includePath) {
            t = _nextTokenWithBuffering(_headContext);
            if (t != null) {
                _currToken = t;
                return t;
            }
        }
        break;

    case ID_START_OBJECT:
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildObjectContext(f, true);
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return (_currToken = t);
        }
        if (f == null) {
            delegate.skipChildren();
            break;
        }
        f = _headContext.checkValue(f);
        if (f == null) {
            delegate.skipChildren();
            break;
        }
        if (f != TokenFilter.INCLUDE_ALL) {
            f = f.filterStartObject();
        }
        _itemFilter = f;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildObjectContext(f, true);
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return (_currToken = t);
        }
        _headContext = _headContext.createChildObjectContext(f, false);

        if (_includePath) {
            t = _nextTokenWithBuffering(_headContext);
            if (t != null) {
                _currToken = t;
                return t;
            }
        }
        break;

    case ID_END_ARRAY:
    case ID_END_OBJECT:
        {
            boolean returnEnd = _headContext.isStartHandled();
            f = _headContext.getFilter();
            if ((f != null) && (f != TokenFilter.INCLUDE_ALL)) {
                f.filterFinishArray();
            }
            _headContext = _headContext.getParent();
            _itemFilter = _headContext == null ? null : _headContext.getFilter();
            if (returnEnd) {
                return (_currToken = t);
            }
        }
        break;

    case ID_FIELD_NAME:
        {
            final String name = delegate.getCurrentName();
            f = _headContext.setFieldName(name);
            if (f == TokenFilter.INCLUDE_ALL) {
                _itemFilter = f;
                // Count match if multiple matches not allowed
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
                if (!_includePath) {
                    if (_includeImmediateParent && !_headContext.isStartHandled()) {
                        t = _headContext.nextTokenToRead(); // returns START_OBJECT but marks it handled
                        _exposedContext = _headContext;
                    }
                }
                return (_currToken = t);
            }
            if (f == null) {
                delegate.nextToken();
                delegate.skipChildren();
                break;
            }
            f = f.includeProperty(name);
            if (f == null) {
                delegate.nextToken();
                delegate.skipChildren();
                break;
            }
            _itemFilter = f;
            if (f == TokenFilter.INCLUDE_ALL) {
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
                if (_includePath) {
                    return (_currToken = t);
                }
            }
            if (_includePath) {
                t = _nextTokenWithBuffering(_headContext);
                if (t != null) {
                    _currToken = t;
                    return t;
                }
            }
        }
        break;

    default:
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return (_currToken = t);
        }
        if (f != null) {
            f = _headContext.checkValue(f);
            if ((f == TokenFilter.INCLUDE_ALL)
                    || ((f != null) && f.includeValue(delegate))) {
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
                return (_currToken = t);
            }
        }
        // Otherwise not included (leaves must be explicitly included)
        break;
    }

    // If token was not returned, try to get it from the buffered chain or next token method
    return _nextToken2();
}
