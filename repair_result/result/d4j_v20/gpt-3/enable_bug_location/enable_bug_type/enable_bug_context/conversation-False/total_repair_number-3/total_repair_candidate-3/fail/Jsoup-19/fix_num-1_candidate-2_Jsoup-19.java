private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
    // rels without a baseuri get removed
    String value = el.absUrl(attr.getKey());
    if (!preserveRelativeLinks) {
        if (!value.isEmpty()) {
            attr.setValue(value);
        } else {
            // No abs URL found, keep original value (relative)
            value = attr.getValue();
        }
    } else {
        // preserveRelativeLinks is true, keep original value
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
