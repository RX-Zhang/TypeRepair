private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());

    // if absUrl returns an empty string (meaning no base or abs url), 
    // fall back to original attribute value
    if (value.isEmpty()) {
        value = attr.getValue();
    } else if (!preserveRelativeLinks) {
        attr.setValue(value);
    }
    
    for (Protocol protocol : protocols) {
        String prot = protocol.toString().toLowerCase() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
