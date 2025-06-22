public void close() throws IOException {
    if (!this.closed) {
        try {
            super.close();
        } catch (IOException e) {
            // Handle the exception or log it
            throw new IOException("Failed to close the stream", e);
        }
        this.closed = true;
    }
}
