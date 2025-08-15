public Base64() {
    this(false);
}

@Override
public byte[] encode(byte[] pArray) {
    if (pArray == null || pArray.length == 0) {
        return new byte[0];
    }
    encode(pArray, 0, pArray.length);
    if (buffer == null) {
        return new byte[0];
    }
    byte[] result = new byte[pos];
    System.arraycopy(buffer, 0, result, 0, pos);
    reset();
    return result;
}

@Override
public Object encode(Object pObject) {
    if (!(pObject instanceof byte[])) {
        throw new EncoderException("Parameter supplied to Base64 encode is not a byte[]");
    }
    return encode((byte[]) pObject);
}
