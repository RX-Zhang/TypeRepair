    public void testForgottenImaginaryCharacter() {
        ParsePosition pos = new ParsePosition(0);
        assertNull(new ComplexFormat().parse("1 + 1", pos));
        assertEquals(5, pos.getErrorIndex());
    }