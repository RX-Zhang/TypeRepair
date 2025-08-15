public StringBuilder getGenericSignature(StringBuilder sb)
{
    _classSignature(_class, sb, false);
    if (_referencedType.containedTypeCount() > 0) {
        sb.append('<');
        for (int i = 0, len = _referencedType.containedTypeCount(); i < len; i++) {
            sb = _referencedType.containedType(i).getGenericSignature(sb);
        }
        sb.append('>');
    }
    sb.append(';');
    return sb;
}
