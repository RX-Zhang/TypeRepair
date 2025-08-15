public StringBuilder getGenericSignature(StringBuilder sb)
{
    _classSignature(_class, sb, false);
    if (containedTypeCount() > 0) {
        sb.append('<');
        for (int i = 0, len = containedTypeCount(); i < len; i++) {
            sb = containedType(i).getGenericSignature(sb);
        }
        sb.append('>');
    }
    sb.append(';');
    return sb;
}
