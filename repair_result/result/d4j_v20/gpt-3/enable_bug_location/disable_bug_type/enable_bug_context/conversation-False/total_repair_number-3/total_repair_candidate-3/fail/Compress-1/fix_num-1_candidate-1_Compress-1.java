public void close() throws IOException {
    if (!this.closed) {
        try {
            super.close();
        } catch (IOException e) {
            throw new IOException("Failed to close the stream", e);
        }
        this.closed = true;
    }
}
