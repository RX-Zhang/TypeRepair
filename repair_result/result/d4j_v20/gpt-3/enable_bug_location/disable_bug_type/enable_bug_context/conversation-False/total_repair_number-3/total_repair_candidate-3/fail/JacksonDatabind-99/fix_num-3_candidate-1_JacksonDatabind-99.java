protected String buildCanonicalName()
{
    StringBuilder sb = new StringBuilder();
    sb.append(_class.getName());
    sb.append('<');
    sb.append(_referencedType.toCanonical());
    sb.append('>'); // Added closing angle bracket for the generic type
    return sb.toString();
}
