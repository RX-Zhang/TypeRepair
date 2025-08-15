private void readTypeVariables() {
    for (Type type : typeVariable.getBounds()) {
        if (type != null) {
            registerTypeVariablesOn(type);
        }
    }
    Type actualTypeArg = getActualTypeArgumentFor(typeVariable);
    if (actualTypeArg != null) {
        registerTypeVariablesOn(actualTypeArg);
    }
}
