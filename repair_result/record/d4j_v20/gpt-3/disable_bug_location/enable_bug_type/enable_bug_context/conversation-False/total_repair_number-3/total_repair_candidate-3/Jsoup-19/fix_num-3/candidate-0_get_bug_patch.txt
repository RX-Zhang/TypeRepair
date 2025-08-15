private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = attr.getValue();
    if (!preserveRelativeLinks) {
        // try to get abs URL if possible, else keep original value
        String absUrl = el.absUrl(attr.getKey());
        if (!absUrl.isEmpty()) {
            attr.setValue(absUrl);
            value = absUrl;
        }
    }
    
    for (Protocol protocol : protocols) {
        String prot = protocol.toString().toLowerCase() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
