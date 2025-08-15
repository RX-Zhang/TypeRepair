public void close() throws IOException {
    if (!this.closed) {
        try {
            // Ensure any open archive entry is finished before closing
            if (this.cpioEntry != null) {
                closeArchiveEntry();
            }
            // Finish the archive if not already done
            if (!this.finished) {
                finish();
            }
        } finally {
            super.close();
            this.closed = true;
        }
    }
}
