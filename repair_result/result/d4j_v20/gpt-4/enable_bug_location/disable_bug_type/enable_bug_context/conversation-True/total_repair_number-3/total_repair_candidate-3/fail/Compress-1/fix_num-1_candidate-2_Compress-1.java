public void close() throws IOException {
    if (!this.closed) {
        try {
            super.close();
            this.closed = true;
        } catch (IOException e) {
            // Handle exception, possibly rethrow or log
            throw new IOException("Failed to close the stream", e);
        }
    }
}
