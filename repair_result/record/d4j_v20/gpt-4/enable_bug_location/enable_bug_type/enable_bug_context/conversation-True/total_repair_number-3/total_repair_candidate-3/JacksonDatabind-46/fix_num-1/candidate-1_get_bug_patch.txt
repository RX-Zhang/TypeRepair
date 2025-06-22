public StringBuilder getGenericSignature(StringBuilder sb)
{
    _classSignature(_class, sb, false);
    JavaType refType = getReferencedType();
    if (refType.containedTypeCount() > 0) {
        sb.append('<');
        refType.getGenericSignature(sb);
        sb.append('>');
    }
    sb.append(';');
    return sb;
}
