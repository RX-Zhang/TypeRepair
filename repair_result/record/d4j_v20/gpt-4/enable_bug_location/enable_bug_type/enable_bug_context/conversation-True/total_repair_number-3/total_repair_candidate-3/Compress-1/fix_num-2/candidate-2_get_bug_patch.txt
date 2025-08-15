public void close() throws IOException {
    if (!this.closed) {
        if (!this.finished) {
            finish();
        }
        super.close();
        this.closed = true;
    }
}
