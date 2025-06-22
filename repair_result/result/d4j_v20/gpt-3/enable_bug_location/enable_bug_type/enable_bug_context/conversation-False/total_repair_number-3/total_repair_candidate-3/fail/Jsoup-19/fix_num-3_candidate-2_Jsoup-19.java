private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());
    
    // If the absolute URL is empty, treat as relative URL.
    // If preserveRelativeLinks is true, don't change attr value; else set it to abs URL (which may be empty)
    if (!preserveRelativeLinks) {
        attr.setValue(value);
    }
    
    // If absolute URL is empty, and preserveRelativeLinks is true,
    // use the original attribute value for checking protocols instead of empty string
    String checkValue = value.isEmpty() ? attr.getValue() : value;

    for (Protocol protocol : protocols) {
        String prot = protocol.toString().toLowerCase() + ":";
        if (checkValue.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
