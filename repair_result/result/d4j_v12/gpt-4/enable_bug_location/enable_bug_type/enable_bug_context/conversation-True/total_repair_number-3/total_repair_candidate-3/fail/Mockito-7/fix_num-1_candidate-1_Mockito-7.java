private void readTypeVariables() {
    for (Type type : typeVariable.getBounds()) {
        if (type != null) {
            registerTypeVariablesOn(type);
        }
    }
    Type actualType = getActualTypeArgumentFor(typeVariable);
    if (actualType != null) {
        registerTypeVariablesOn(actualType);
    }
}
