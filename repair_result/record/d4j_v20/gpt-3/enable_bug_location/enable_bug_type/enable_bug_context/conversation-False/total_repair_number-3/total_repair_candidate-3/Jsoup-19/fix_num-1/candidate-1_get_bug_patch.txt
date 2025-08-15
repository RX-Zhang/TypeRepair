private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String absUrl = el.absUrl(attr.getKey());
    if (!absUrl.isEmpty()) {
        if (!preserveRelativeLinks)
            attr.setValue(absUrl);
        for (Protocol protocol : protocols) {
            String prot = protocol.toString().toLowerCase() + ":";
            if (absUrl.toLowerCase().startsWith(prot)) {
                return true;
            }
        }
        return false;
    } else {
        // No absolute URL, check the attribute's raw value for allowed protocols (for relative URLs)
        if (preserveRelativeLinks) {
            String val = attr.getValue();
            for (Protocol protocol : protocols) {
                String prot = protocol.toString().toLowerCase() + ":";
                if (val.toLowerCase().startsWith(prot)) {
                    return true;
                }
            }
            return false;
        }
        // Not preserving relative, and no abs url, so invalid
        return false;
    }
}
