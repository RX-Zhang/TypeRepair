public void stop() {
    if(this.runningState != STATE_RUNNING && this.runningState != STATE_SUSPENDED) {
        throw new IllegalStateException("Stopwatch is not running. ");
    }
    if (this.runningState == STATE_RUNNING) {
        stopTime = System.currentTimeMillis();
    }
    // if suspended, stopTime is already set in suspend()
    this.runningState = STATE_STOPPED;
}
