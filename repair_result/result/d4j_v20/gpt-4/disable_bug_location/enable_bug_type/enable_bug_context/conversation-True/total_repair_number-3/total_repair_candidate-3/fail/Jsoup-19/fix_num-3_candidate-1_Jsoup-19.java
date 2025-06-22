private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = attr.getValue();  // use actual attribute value, not absUrl directly
    String absValue = el.absUrl(attr.getKey());

    if (!preserveRelativeLinks && absValue.length() > 0) {
        attr.setValue(absValue);
        value = absValue;
    }

    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
