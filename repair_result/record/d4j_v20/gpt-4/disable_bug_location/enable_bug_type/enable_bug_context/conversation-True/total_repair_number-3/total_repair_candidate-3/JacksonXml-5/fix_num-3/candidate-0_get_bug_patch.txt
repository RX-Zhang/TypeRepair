protected XmlSerializerProvider(XmlSerializerProvider src) {
    super(src);
    // 21-May-2018, tatu: As per [dataformat-xml#282], should copy
    //    root name lookup to ensure correct behavior with name serialization
    _rootNameLookup = (src == null) ? new XmlRootNameLookup() : src._rootNameLookup;
}
