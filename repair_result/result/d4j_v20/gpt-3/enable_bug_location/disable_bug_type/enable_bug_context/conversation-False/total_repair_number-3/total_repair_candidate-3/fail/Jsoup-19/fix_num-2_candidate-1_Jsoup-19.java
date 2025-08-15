private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());
    if (value.isEmpty()) {
        // no absolute URL, so check the original attribute value for relative URLs if preserveRelativeLinks is true
        value = attr.getValue();
        if (!preserveRelativeLinks) {
            // If not preserving relative links and no abs URL, then value is empty, so no valid protocol
            return false;
        }
    } else if (!preserveRelativeLinks) {
        attr.setValue(value);
    }

    String valueLower = value.toLowerCase();
    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (valueLower.startsWith(prot)) {
            return true;
        }
    }
    return false;
}
