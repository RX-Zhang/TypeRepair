protected String buildCanonicalName()
{
    StringBuilder sb = new StringBuilder();
    sb.append(_class.getName());
    sb.append('<');
    sb.append(_referencedType.toCanonical());
    sb.append('>'); // Fixing the missing closing bracket for generic type
    return sb.toString();
}
