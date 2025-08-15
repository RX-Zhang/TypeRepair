public void close() throws IOException {
    if (!this.closed) {
        try {
            super.close();
        } catch (IOException e) {
            // Handle the exception (e.g., log the error)
            throw new IOException("Error while closing the stream: " + e.getMessage(), e);
        }
        this.closed = true;
    }
}
