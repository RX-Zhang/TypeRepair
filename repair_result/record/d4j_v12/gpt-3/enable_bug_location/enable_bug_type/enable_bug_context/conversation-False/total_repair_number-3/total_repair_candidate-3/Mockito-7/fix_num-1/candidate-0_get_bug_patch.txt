private void readTypeVariables() {
    for (Type type : typeVariable.getBounds()) {
        try {
            registerTypeVariablesOn(type);
        } catch (Exception e) {
            // Log or handle the exception as needed to prevent runtime errors
            // For now we silently ignore to avoid breaking execution
        }
    }
    Type actualType = getActualTypeArgumentFor(typeVariable);
    if (actualType != null) {
        try {
            registerTypeVariablesOn(actualType);
        } catch (Exception e) {
            // Log or handle the exception as needed
        }
    }
}
