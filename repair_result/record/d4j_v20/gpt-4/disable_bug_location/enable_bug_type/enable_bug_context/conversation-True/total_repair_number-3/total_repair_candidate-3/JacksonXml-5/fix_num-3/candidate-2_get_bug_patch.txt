protected XmlSerializerProvider(XmlSerializerProvider src) {
    super(src);
    // 21-May-2018, tatu: As per [dataformat-xml#282], should copy
    //    root name lookup to have consistent behavior in copies
    _rootNameLookup = src._rootNameLookup == null ? new XmlRootNameLookup() : src._rootNameLookup;
}
