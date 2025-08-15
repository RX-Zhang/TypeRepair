protected String buildCanonicalName()
{
    StringBuilder sb = new StringBuilder();
    sb.append(_class.getName());
    if (_referencedType != null) {
        sb.append('<');
        sb.append(getReferencedType().toCanonical());
        sb.append('>');
    }
    return sb.toString();
}
