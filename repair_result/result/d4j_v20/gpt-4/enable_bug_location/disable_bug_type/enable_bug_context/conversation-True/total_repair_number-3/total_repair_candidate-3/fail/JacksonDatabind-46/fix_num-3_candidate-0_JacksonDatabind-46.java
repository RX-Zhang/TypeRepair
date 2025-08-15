public StringBuilder getGenericSignature(StringBuilder sb)
{
    _classSignature(_class, sb, false);
    if (_referencedType.containedTypeCount() > 0) {
        sb.append('<');
        sb = _referencedType.getGenericSignature(sb);
        sb.append('>');
    }
    return sb;
}
