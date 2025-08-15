public ValueMarker(double value, Paint paint, Stroke stroke, 
                   Paint outlinePaint, Stroke outlineStroke, float alpha) {
    super(paint, stroke, outlinePaint, outlineStroke, alpha); // Corrected to use outlinePaint and outlineStroke in the superclass call
    this.value = value;
}
