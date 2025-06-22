public void close() throws IOException {
    if (!this.closed) {
        try {
            if (!this.finished) {
                finish();
            }
        } finally {
            super.close();
            this.closed = true;
        }
    }
}
