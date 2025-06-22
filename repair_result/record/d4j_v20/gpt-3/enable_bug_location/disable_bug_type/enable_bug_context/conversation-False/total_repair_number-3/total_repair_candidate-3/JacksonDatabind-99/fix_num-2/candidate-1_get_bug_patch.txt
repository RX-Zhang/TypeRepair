protected String buildCanonicalName()
{
    StringBuilder sb = new StringBuilder();
    sb.append(_class.getName());
    sb.append('<');
    sb.append(_referencedType.toCanonical());
    sb.append('>'); // Added closing angle bracket to complete the canonical name
    return sb.toString();
}
