private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
        } else {
            StringBuilder valueAccum = new StringBuilder();
            // no ' or " to look for, so scan to end tag or space (or end of stream)
            while (!tq.matchesAny("<", "/>", ">") && !tq.matchesWhitespace() && !tq.isEmpty()) {
                valueAccum.append(tq.consume());
            }
            value = valueAccum.toString();
        }
        tq.consumeWhitespace();
    }
    if (key.length() != 0)
        return Attribute.createFromEncoded(key, value);
    else {
        tq.consume();
        return null;
    }
}

// Adding boundary checks and exception handling for potential runtime errors
private void parseStartTag() {
    try {
        tq.consume("<");
        String tagName = tq.consumeTagName();
        Validate.notEmpty(tagName, "Unexpectedly empty tagname. (This should not occur, please report!)");

        tq.consumeWhitespace();
        Attributes attributes = new Attributes();
        while (!tq.matchesAny("<", "/>", ">") && !tq.isEmpty()) {
            Attribute attribute = parseAttribute();
            if (attribute != null)
                attributes.put(attribute);
        }

        Tag tag = Tag.valueOf(tagName);
        Element child = new Element(tag, baseUri, attributes);

        boolean isEmptyElement = tag.isEmpty(); // empty element if empty tag (e.g. img) or self-closed el (<div/>
        if (tq.matchChomp("/>")) { // close empty element or tag
            isEmptyElement = true;
            if (!tag.isKnownTag()) // if unknown and a self closed, allow it to be self closed on output. this doesn't force all instances to be empty
                tag.setSelfClosing();
        } else {
            tq.matchChomp(">");
        }
        addChildToParent(child, isEmptyElement);

        // pc data only tags (textarea, script): chomp to end tag, add content as text node
        if (tag.isData()) {
            String data = tq.chompToIgnoreCase("</" + tagName);
            tq.chompTo(">");
            popStackToClose(tag);

            Node dataNode;
            if (tag.equals(titleTag) || tag.equals(textareaTag)) // want to show as text, but not contain inside tags (so not a data tag?)
                dataNode = TextNode.createFromEncoded(data, baseUri);
            else
                dataNode = new DataNode(data, baseUri); // data not encoded but raw (for " in script)
            child.appendChild(dataNode);
        }

        // <base href>: update the base uri
        if (child.tagName().equals("base")) {
            String href = child.absUrl("href");
            if (href.length() != 0) { // ignore <base target> etc
                baseUri = href;
                doc.setBaseUri(href); // set on the doc so doc.createElement(Tag) will get updated base
            }
        }
    } catch (RuntimeException e) {
        // Handle potential runtime exceptions
        System.err.println("Error parsing start tag: " + e.getMessage());
    }
}
