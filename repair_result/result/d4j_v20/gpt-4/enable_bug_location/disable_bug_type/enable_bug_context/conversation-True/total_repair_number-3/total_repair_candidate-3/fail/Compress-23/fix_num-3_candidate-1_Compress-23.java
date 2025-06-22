InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    // Use the generic addDecoder method to decode input stream with the coder and password,
    // this handles different LZMA dictionary sizes and other coder properties properly
    return Coders.addDecoder(in, coder, password);
}
