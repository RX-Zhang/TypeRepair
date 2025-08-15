public JsonToken nextToken() throws IOException
{
    // Check if multiple matches are disallowed and something is buffered
    if (!_allowMultipleMatches && _matchCount > 0) {
        // Need to fail fast if no multiple matches allowed and we already matched something
        return null;
    }

    // Anything buffered?
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
                if (ctxt.inArray()) {
                    t = delegate.getCurrentToken();
                    if (t != JsonToken.START_ARRAY) {
                        _currToken = t;
                        return t;
                    }
                    // No token to return here, break out to continue reading from delegate
                }
                break;
            }
            // If not, traverse down the context chain
            ctxt = _headContext.findChildOf(ctxt);
            _exposedContext = ctxt;
            if (ctxt == null) { // should never occur
                throw _constructError("Unexpected problem: chain of filtered context broken");
            }
        }
    }

    // If not buffered, read next token from delegate
    JsonToken t = delegate.nextToken();
    if (t == null) {
        return (_currToken = null);
    }

    // otherwise... to include or not?
    TokenFilter f;

    switch (t.id()) {
    case ID_START_ARRAY:
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildArrayContext(f, true);
            _currToken = t;
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return t;
        }
        if (f == null) { // does this occur?
            delegate.skipChildren();
            break;
        }
        // Otherwise still iffy, need to check
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
            _currToken = t;
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return t;
        }
        _headContext = _headContext.createChildArrayContext(f, false);

        if (_includePath) {
            t = _nextTokenWithBuffering(_headContext);
            if (t != null) {
                _currToken = t;
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
                return t;
            }
        }
        break;

    case ID_START_OBJECT:
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildObjectContext(f, true);
            _currToken = t;
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return t;
        }
        if (f == null) { // does this occur?
            delegate.skipChildren();
            break;
        }
        // Otherwise still iffy, need to check
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
            _currToken = t;
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return t;
        }
        _headContext = _headContext.createChildObjectContext(f, false);

        if (_includePath) {
            t = _nextTokenWithBuffering(_headContext);
            if (t != null) {
                _currToken = t;
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
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
            _itemFilter = _headContext.getFilter();
            if (returnEnd) {
                _currToken = t;
                return t;
            }
        }
        break;

    case ID_FIELD_NAME:
        {
            final String name = delegate.getCurrentName();
            f = _headContext.setFieldName(name);
            if (f == TokenFilter.INCLUDE_ALL) {
                _itemFilter = f;
                if (!_includePath) {
                    if (_includeImmediateParent && !_headContext.isStartHandled()) {
                        t = _headContext.nextTokenToRead(); // returns START_OBJECT but also marks it handled
                        _exposedContext = _headContext;
                    }
                }
                _currToken = t;
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
                return t;
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
                if (_includePath) {
                    _currToken = t;
                    if (!_allowMultipleMatches) {
                        _matchCount++;
                    }
                    return t;
                }
            }
            if (_includePath) {
                t = _nextTokenWithBuffering(_headContext);
                if (t != null) {
                    _currToken = t;
                    if (!_allowMultipleMatches) {
                        _matchCount++;
                    }
                    return t;
                }
            }
        }
        break;

    default: // scalar value
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _currToken = t;
            if (!_allowMultipleMatches) {
                _matchCount++;
            }
            return t;
        }
        if (f != null) {
            f = _headContext.checkValue(f);
            if ((f == TokenFilter.INCLUDE_ALL)
                    || ((f != null) && f.includeValue(delegate))) {
                _currToken = t;
                if (!_allowMultipleMatches) {
                    _matchCount++;
                }
                return t;
            }
        }
        // Otherwise not included (leaves must be explicitly included)
        break;
    }

    // We get here if token was not yet emitted; try again with deeper check
    return _nextToken2();
}
