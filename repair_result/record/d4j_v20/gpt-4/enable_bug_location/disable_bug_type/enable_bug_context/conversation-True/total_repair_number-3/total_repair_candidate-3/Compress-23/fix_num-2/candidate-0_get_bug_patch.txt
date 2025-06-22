InputStream decode(final InputStream in, final Coder coder,
        byte[] password) throws IOException {
    return Coders.addDecoder(in, coder, password);
}
