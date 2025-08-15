protected XmlSerializerProvider(XmlSerializerProvider src) {
    super(src);
    // 21-May-2018, tatu: As per [dataformat-xml#282], should copy root name lookup
    //    to keep configuration consistent after copying provider
    _rootNameLookup = new XmlRootNameLookup(src._rootNameLookup);
}
