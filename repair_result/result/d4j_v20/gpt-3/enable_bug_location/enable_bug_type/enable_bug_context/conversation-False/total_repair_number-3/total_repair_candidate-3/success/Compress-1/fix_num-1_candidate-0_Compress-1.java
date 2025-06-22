public void close() throws IOException {
    if (!this.closed) {
        try {
            if (!this.finished) {
                finish();
            }
        } finally {
            try {
                super.close();
            } finally {
                this.closed = true;
            }
        }
    }
}
