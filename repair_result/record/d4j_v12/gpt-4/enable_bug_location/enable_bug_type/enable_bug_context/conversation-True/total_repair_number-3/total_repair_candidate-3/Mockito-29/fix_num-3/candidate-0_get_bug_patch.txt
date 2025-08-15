public void describeTo(Description description) {
    description.appendText("same(");
    appendQuoting(description);
    if (wanted == null) {
        description.appendText("null");
    } else {
        description.appendText(wanted.toString());
    }
    appendQuoting(description);
    description.appendText(")");
}
