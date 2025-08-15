public void close() throws IOException {
    if (!this.closed) {
        try {
            if (this.cpioEntry != null) {
                closeArchiveEntry();
            }
            finish();
        } finally {
            super.close();
            this.closed = true;
        }
    }
}
