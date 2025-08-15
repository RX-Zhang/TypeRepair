protected String buildCanonicalName()
{
    StringBuilder sb = new StringBuilder();
    sb.append(_class.getName());
    JavaType refType = getReferencedType();
    if (refType != null) {
        sb.append('<');
        sb.append(refType.toCanonical());
        sb.append('>');
    }
    return sb.toString();
}
