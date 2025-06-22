public void verify(VerificationData data) {
    AssertionError error = null;

    timer.start();
    while (timer.isCounting()) {
        try {
            delegate.verify(data);

            if (returnOnSuccess) {
                return;
            } else {
                error = null; // Reset error if verification is successful
            }
        } catch (MockitoAssertionError e) {
            error = handleVerifyException(e);
        } catch (org.mockito.exceptions.verification.junit.ArgumentsAreDifferent e) {
            error = handleVerifyException(e);
        } catch (Exception e) { // Added to handle unexpected exceptions
            error = new AssertionError("Unexpected exception during verification", e);
            break; // Exit the loop on unexpected exception
        }
    }

    if (error != null) {
        throw error;
    }
}
