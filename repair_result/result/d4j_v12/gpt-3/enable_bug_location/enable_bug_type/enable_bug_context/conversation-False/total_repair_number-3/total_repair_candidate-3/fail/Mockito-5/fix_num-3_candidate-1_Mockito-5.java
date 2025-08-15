public void verify(VerificationData data) {
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
        } catch (Exception e) {
            // Added to handle any unexpected exceptions and prevent runtime errors
            error = new AssertionError("Unexpected exception occurred: " + e.getMessage(), e);
        }
    }

    if (error != null) {
        throw error;
    }
}
