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
            error = handleVerifyException(new AssertionError("Arguments are different", e));
        } catch (Exception e) {
            // Adding a catch-all for any unexpected exceptions
            error = new AssertionError("Unexpected error during verification", e);
        }
    }

    if (error != null) {
        throw error;
    }
}
