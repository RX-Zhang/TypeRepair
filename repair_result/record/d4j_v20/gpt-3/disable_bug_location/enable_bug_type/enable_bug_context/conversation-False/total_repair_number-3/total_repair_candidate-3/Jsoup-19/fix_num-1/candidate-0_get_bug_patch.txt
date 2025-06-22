private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());
    
    // If absUrl returned empty string, the attr value is a relative url (or no base), so:
    // if we want to preserve relative links, do not overwrite attribute
    // if not preserving, and absUrl is empty, remove attribute by returning false immediately
    if (value.isEmpty()) {
        if (preserveRelativeLinks) {
            value = attr.getValue(); // keep original relative value
        } else {
            return false;
        }
    } else if (!preserveRelativeLinks) {
        attr.setValue(value);
    }
    
    String lowerVal = value.toLowerCase();
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (lowerVal.startsWith(prot)) {
            return true;
        }
    }
    return false;
}
