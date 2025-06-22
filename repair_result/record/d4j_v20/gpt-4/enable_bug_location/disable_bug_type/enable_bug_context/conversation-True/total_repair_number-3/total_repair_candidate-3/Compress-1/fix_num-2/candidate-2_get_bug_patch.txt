public void close() throws IOException {
    try {
        if (!this.closed) {
            super.close();
            this.closed = true;
        }
    } catch (IOException e) {
        // Handle the exception to prevent runtime errors
        throw new IOException("Failed to close the archive output stream", e);
    }
}
