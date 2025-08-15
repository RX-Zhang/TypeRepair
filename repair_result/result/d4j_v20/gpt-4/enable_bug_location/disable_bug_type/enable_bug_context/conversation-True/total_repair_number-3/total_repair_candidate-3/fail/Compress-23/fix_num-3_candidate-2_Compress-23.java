InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    return addDecoder(in, coder, password);
}
