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
            // Adding a general exception catch to handle any unanticipated errors
            error = new AssertionError("An unexpected error occurred during verification.", e);
            break; // Exiting the loop if an unexpected error occurs
        }
    }

    if (error != null) {
        throw error;
    }
}
