private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    boolean isAbsUrl = false;

    if (value == null)
        return false;

    String valueLower = value.toLowerCase();

    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (valueLower.startsWith(prot)) {
            isAbsUrl = true;
            break;
        }
    }

    if (!isAbsUrl) {
        // try to resolve relative urls to abs, and optionally update the attribute so output html has abs.
        // rels without a baseuri get removed
        value = el.absUrl(attr.getKey());
        if (value.isEmpty())
            return false;
        if (!preserveRelativeLinks)
            attr.setValue(value);

        valueLower = value.toLowerCase();
        for (Protocol protocol : protocols) {
            String prot = protocol.toString() + ":";
            if (valueLower.startsWith(prot)) {
                return true;
            }
        }
        return false;
    }

    return true;
}
