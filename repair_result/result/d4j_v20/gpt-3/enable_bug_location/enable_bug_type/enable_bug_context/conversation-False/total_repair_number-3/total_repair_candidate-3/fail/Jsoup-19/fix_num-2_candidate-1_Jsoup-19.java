private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());

    // If absUrl() returns empty string, it means no baseUri or not absolute
    // So only set attr value if we have a non-empty absolute url or preserveRelativeLinks is false
    if (!preserveRelativeLinks) {
        attr.setValue(value);
    } else if (value.isEmpty()) {
        value = attr.getValue();
    }

    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
