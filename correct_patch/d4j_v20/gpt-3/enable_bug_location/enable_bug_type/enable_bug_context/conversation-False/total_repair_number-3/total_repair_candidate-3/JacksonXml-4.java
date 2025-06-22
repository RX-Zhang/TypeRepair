protected void _serializeXmlNull(JsonGenerator jgen) throws IOException
{
    // 14-Nov-2016, tatu: As per [dataformat-xml#213], we may have explicitly
    //    configured root name...
    if (jgen instanceof ToXmlGenerator) {
        QName rootNameFromConfig = _rootNameFromConfig();
        if (rootNameFromConfig == null) {
            // fallback to default NULL root name
            rootNameFromConfig = ROOT_NAME_FOR_NULL;
        }
        _initWithRootName((ToXmlGenerator) jgen, rootNameFromConfig);
    }
    super.serializeValue(jgen, null);
}
