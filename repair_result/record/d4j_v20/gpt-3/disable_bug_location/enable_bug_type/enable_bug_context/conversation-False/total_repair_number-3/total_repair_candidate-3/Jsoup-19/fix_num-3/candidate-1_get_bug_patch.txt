private boolean testValidProtocol(Element el, Attribute attr, Set<Protocol> protocols) {
    String value = attr.getValue();
    String absUrl = el.absUrl(attr.getKey());
    if (!absUrl.isEmpty()) {
        value = absUrl;
        if (!preserveRelativeLinks)
            attr.setValue(absUrl);
    } else if (!preserveRelativeLinks) {
        // If no abs URL and not preserving relative links, clear attr value
        attr.setValue("");
        value = "";
    }

    for (Protocol protocol : protocols) {
        String prot = protocol.toString() + ":";
        if (value.toLowerCase().startsWith(prot)) {
            return true;
        }
    }
    return false;
}
