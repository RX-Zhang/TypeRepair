public StringBuilder getGenericSignature(StringBuilder sb)
{
    _classSignature(_class, sb, false);
    JavaType refType = getReferencedType();
    if (containedTypeCount() > 0) {
        sb.append('<');
        sb = refType.getGenericSignature(sb);
        sb.append('>');
    }
    return sb;
}
