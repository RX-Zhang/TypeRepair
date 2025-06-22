public void verify(VerificationData data) {
    if (data == null) {
        throw new IllegalArgumentException("VerificationData cannot be null");
    }

    AssertionError error = null;

    timer.start();
    while (timer.isCounting()) {
        try {
            delegate.verify(data);

            if (returnOnSuccess) {
                return;
            } else {
                error = null;
            }
        } catch (MockitoAssertionError e) {
            error = handleVerifyException(e);
        } catch (org.mockito.exceptions.verification.junit.ArgumentsAreDifferent e) {
            error = handleVerifyException(e);
        } catch (RuntimeException e) {
            // Handle any unexpected runtime exceptions
            throw new RuntimeException("Unexpected error during verification", e);
        }
    }

    if (error != null) {
        throw error;
    }
}
