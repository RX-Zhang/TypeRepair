protected AxisState drawLabel(String label, Graphics2D g2, 
        Rectangle2D plotArea, Rectangle2D dataArea, RectangleEdge edge, 
        AxisState state, PlotRenderingInfo plotState) {

    // it is unlikely that 'state' will be null, but check anyway...
    if (state == null) {
        throw new IllegalArgumentException("Null 'state' argument.");
    }
    
    if ((label == null) || (label.equals(""))) {
        return state;
    }

    Font font = getLabelFont();
    RectangleInsets insets = getLabelInsets();
    g2.setFont(font);
    g2.setPaint(getLabelPaint());
    FontMetrics fm = g2.getFontMetrics();
    Rectangle2D labelBounds = TextUtilities.getTextBounds(label, g2, fm);
    Shape hotspot = null;
    double angle = getLabelAngle();
    
    if (edge == RectangleEdge.TOP) {
        AffineTransform t = AffineTransform.getRotateInstance(
                angle, labelBounds.getCenterX(), 
                labelBounds.getCenterY());
        Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
        labelBounds = rotatedLabelBounds.getBounds2D();
        float w = (float) labelBounds.getWidth();
        float h = (float) labelBounds.getHeight();
        float labelx = (float) dataArea.getCenterX();
        float labely = (float) (state.getCursor() - insets.getBottom() 
                - h / 2.0);
        TextUtilities.drawRotatedString(label, g2, labelx, labely, 
                TextAnchor.CENTER, angle, TextAnchor.CENTER);
        hotspot = new Rectangle2D.Float(labelx - w / 2.0f, 
                labely - h / 2.0f, w, h);
        // move cursor up by insets top + label height + insets bottom
        state.cursorUp(insets.getTop() + h + insets.getBottom());
    }
    else if (edge == RectangleEdge.BOTTOM) {
        AffineTransform t = AffineTransform.getRotateInstance(
                angle, labelBounds.getCenterX(), 
                labelBounds.getCenterY());
        Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
        labelBounds = rotatedLabelBounds.getBounds2D();
        float w = (float) labelBounds.getWidth();
        float h = (float) labelBounds.getHeight();
        float labelx = (float) dataArea.getCenterX();
        float labely = (float) (state.getCursor() + insets.getTop() 
                + h / 2.0);
        TextUtilities.drawRotatedString(label, g2, labelx, labely, 
                TextAnchor.CENTER, angle, TextAnchor.CENTER);
        hotspot = new Rectangle2D.Float(labelx - w / 2.0f, 
                labely - h / 2.0f, w, h);
        // move cursor down by insets top + label height + insets bottom
        state.cursorDown(insets.getTop() + h + insets.getBottom());
    }
    else if (edge == RectangleEdge.LEFT) {
        // rotate by (angle - PI/2)
        double rotatedAngle = angle - Math.PI / 2.0;
        AffineTransform t = AffineTransform.getRotateInstance(
                rotatedAngle, labelBounds.getCenterX(), 
                labelBounds.getCenterY());
        Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
        labelBounds = rotatedLabelBounds.getBounds2D();
        float w = (float) labelBounds.getWidth();
        float h = (float) labelBounds.getHeight();
        float labelx = (float) (state.getCursor() - insets.getRight() 
                - w / 2.0);
        float labely = (float) dataArea.getCenterY();
        TextUtilities.drawRotatedString(label, g2, labelx, labely, 
                TextAnchor.CENTER, rotatedAngle, TextAnchor.CENTER);
        hotspot = new Rectangle2D.Float(labelx - w / 2.0f, 
                labely - h / 2.0f, w, h);
        // move cursor left by insets left + label width + insets right
        state.cursorLeft(insets.getLeft() + w + insets.getRight());
    }
    else if (edge == RectangleEdge.RIGHT) {

        double rotatedAngle = angle + Math.PI / 2.0;
        AffineTransform t = AffineTransform.getRotateInstance(
                rotatedAngle, labelBounds.getCenterX(), labelBounds.getCenterY());
        Shape rotatedLabelBounds = t.createTransformedShape(labelBounds);
        labelBounds = rotatedLabelBounds.getBounds2D();
        float w = (float) labelBounds.getWidth();
        float h = (float) labelBounds.getHeight();
        float labelx = (float) (state.getCursor() 
                        + insets.getLeft() + w / 2.0);
        float labely = (float) (dataArea.getY() + dataArea.getHeight() / 2.0);
        TextUtilities.drawRotatedString(label, g2, labelx, labely, 
                TextAnchor.CENTER, rotatedAngle, TextAnchor.CENTER);
        hotspot = new Rectangle2D.Float(labelx - w / 2.0f, 
                labely - h / 2.0f, w, h);
        // move cursor right by insets left + label width + insets right
        state.cursorRight(insets.getLeft() + w + insets.getRight());

    }
    if (plotState != null && hotspot != null) {
        ChartRenderingInfo owner = plotState.getOwner();
        EntityCollection entities = owner.getEntityCollection();
        if (entities != null) {
            entities.add(new AxisLabelEntity(this, hotspot, 
                    this.labelToolTip, this.labelURL));
        }
    }
    return state;

}
