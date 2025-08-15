public JsonToken nextToken() throws IOException
{
    // Check for _allowMultipleMatches - false and atleast there is one token - which is _currToken
    // check for no buffered context _exposedContext - null
    // If all the conditions matches then check for scalar / non-scalar property
    //  if not scalar and ended successfully, then return null
    // else if scalar, and scalar not present in obj/array and !includePath and INCLUDE_ALL matched once
    // then return null
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
                    // This is guaranteed to work without further checks?
                    if (t != JsonToken.START_ARRAY) {
                        _currToken = t;
                        return t;
                    }

                    // Almost! Most likely still have the current token;
                    // with the sole exception of 
                    /*
                    t = delegate.getCurrentToken();
                    if (t != JsonToken.FIELD_NAME) {
                        _currToken = t;
                        return t;
                    }
                    */
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

    // If not, need to read more. If we got any:
    JsonToken t = delegate.nextToken();
    if (t == null) {
        // no strict need to close, since we have no state here
        return (_currToken = t);
    }

    // If _allowMultipleMatches is false, and we've matched at least one token before,
    // and the current candidate is a scalar value, we may opt to skip it.
    if (!_allowMultipleMatches && _matchCount > 0) {
        // We want to suppress multiple matches, so skip anything that is scalar or possibly included.
        // But if token is structural (start/end), we allow traversal.
        if (!t.isStructStart() && !t.isStructEnd() && t != JsonToken.FIELD_NAME) {
            // Skip this token, advance to next
            // Use a loop to skip tokens until next acceptable
            while (true) {
                t = delegate.nextToken();
                if (t == null) {
                    return (_currToken = null);
                }
                // Allow structural tokens or FIELD_NAME or tokens accepted by filter
                if (t.isStructStart() || t.isStructEnd() || t == JsonToken.FIELD_NAME) {
                    break;
                }
            }
        }
    }

    // otherwise... to include or not?
    TokenFilter f;

    switch (t.id()) {
    case ID_START_ARRAY:
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _headContext = _headContext.createChildArrayContext(f, true);
            _matchCount++;
            return (_currToken = t);
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
            _matchCount++;
            _headContext = _headContext.createChildArrayContext(f, true);
            return (_currToken = t);
        }
        _headContext = _headContext.createChildArrayContext(f, false);

        // Also: only need buffering if parent path to be included
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
            _matchCount++;
            _headContext = _headContext.createChildObjectContext(f, true);
            return (_currToken = t);
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
            _matchCount++;
            _headContext = _headContext.createChildObjectContext(f, true);
            return (_currToken = t);
        }
        _headContext = _headContext.createChildObjectContext(f, false);
        // Also: only need buffering if parent path to be included
        if (_includePath) {
            t = _nextTokenWithBuffering(_headContext);
            if (t != null) {
                _currToken = t;
                return t;
            }
        }
        // note: inclusion of surrounding Object handled separately via
        // FIELD_NAME
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
                return (_currToken = t);
            }
        }
        break;

    case ID_FIELD_NAME:
        {
            final String name = delegate.getCurrentName();
            // note: this will also set 'needToHandleName'
            f = _headContext.setFieldName(name);
            if (f == TokenFilter.INCLUDE_ALL) {
                _itemFilter = f;
                _matchCount++;
                if (!_includePath) {
                    // Minor twist here: if parent NOT included, may need to induce output of
                    // surrounding START_OBJECT/END_OBJECT
                    if (_includeImmediateParent && !_headContext.isStartHandled()) {
                        t = _headContext.nextTokenToRead(); // returns START_OBJECT but also marks it handled
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
                _matchCount++;
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
            break;
        }

    default: // scalar value
        f = _itemFilter;
        if (f == TokenFilter.INCLUDE_ALL) {
            _matchCount++;
            return (_currToken = t);
        }
        if (f != null) {
            f = _headContext.checkValue(f);
            if ((f == TokenFilter.INCLUDE_ALL)
                    || ((f != null) && f.includeValue(delegate))) {
                _matchCount++;
                return (_currToken = t);
            }
        }
        // Otherwise not included (leaves must be explicitly included)
        break;
    }

    // We get here if token was not yet found; offlined handling
    return _nextToken2();
}
