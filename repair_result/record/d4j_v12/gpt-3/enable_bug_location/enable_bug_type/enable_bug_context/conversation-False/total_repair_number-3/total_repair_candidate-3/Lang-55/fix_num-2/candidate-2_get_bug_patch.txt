public void stop() {
    if(this.runningState != STATE_RUNNING && this.runningState != STATE_SUSPENDED) {
        throw new IllegalStateException("Stopwatch must be running or suspended to stop. ");
    }
    stopTime = System.currentTimeMillis();
    this.runningState = STATE_STOPPED;
}
