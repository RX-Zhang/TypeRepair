public void close() throws IOException {
    if (!this.closed) {
        try {
            finish();
        } catch (IOException e) {
            // If finish fails, try to still close the stream
            // but propagate the exception after closing
            try {
                super.close();
            } catch (IOException closeException) {
                // Suppress or add suppressed exception
                e.addSuppressed(closeException);
            }
            this.closed = true;
            throw e;
        }
        super.close();
        this.closed = true;
    }
}
