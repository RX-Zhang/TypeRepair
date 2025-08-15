protected Size2D arrangeFF(BlockContainer container, Graphics2D g2,
                           RectangleConstraint constraint) {
    double[] w = new double[5];
    double[] h = new double[5];
    
    w[0] = constraint.getWidth();

    // Initialize heights and widths to zero to avoid uninitialized use
    for (int i = 0; i < 5; i++) {
        h[i] = 0.0;
        w[i] = (i == 0) ? w[0] : 0.0;
    }

    if (this.topBlock != null) {
        RectangleConstraint c1 = new RectangleConstraint(w[0], null,
                LengthConstraintType.FIXED, 0.0,
                new Range(0.0, Math.max(0.0, constraint.getHeight())),
                LengthConstraintType.RANGE);
        Size2D size = this.topBlock.arrange(g2, c1);
        h[0] = size.height;
    }
    
    if (this.bottomBlock != null) {
        double bottomHeightRangeMax = constraint.getHeight() - h[0];
        if (bottomHeightRangeMax < 0) {
            bottomHeightRangeMax = 0.0; // Prevent invalid range
        }
        RectangleConstraint c2 = new RectangleConstraint(w[0], null,
                LengthConstraintType.FIXED, 0.0, 
                new Range(0.0, bottomHeightRangeMax),
                LengthConstraintType.RANGE);
        Size2D size = this.bottomBlock.arrange(g2, c2);
        h[1] = size.height;
    }
    
    h[2] = constraint.getHeight() - h[0] - h[1];
    if (h[2] < 0) {
        h[2] = 0.0; // prevent negative height for middle section
    }
    
    if (this.leftBlock != null) {
        RectangleConstraint c3 = new RectangleConstraint(0.0,
                new Range(0.0, w[0]),
                LengthConstraintType.RANGE, h[2], null,
                LengthConstraintType.FIXED);
        Size2D size = this.leftBlock.arrange(g2, c3);
        w[2] = size.width;
    } else {
        w[2] = 0.0;
    }
    
    if (this.rightBlock != null) {
        double rightWidthRangeMax = w[0] - w[2];
        if (rightWidthRangeMax < 0) {
            rightWidthRangeMax = 0.0; // Prevent invalid range
        }
        RectangleConstraint c4 = new RectangleConstraint(0.0,
                new Range(0.0, rightWidthRangeMax),
                LengthConstraintType.RANGE, h[2], null,
                LengthConstraintType.FIXED);
        Size2D size = this.rightBlock.arrange(g2, c4);
        w[3] = size.width;
    } else {
        w[3] = 0.0;
    }
    
    w[4] = w[0] - w[2] - w[3];
    if (w[4] < 0) {
        w[4] = 0.0; // prevent negative width for center block
    }
    h[4] = h[2];
    
    RectangleConstraint c5 = new RectangleConstraint(w[4], h[4]);
    if (this.centerBlock != null) {
        this.centerBlock.arrange(g2, c5);
    }

    if (this.topBlock != null) {
        this.topBlock.setBounds(new Rectangle2D.Double(0.0, 0.0, w[0],
                h[0]));
    }
    if (this.bottomBlock != null) {
        this.bottomBlock.setBounds(new Rectangle2D.Double(0.0, h[0] + h[2],
                w[0], h[1]));
    }
    if (this.leftBlock != null) {
        this.leftBlock.setBounds(new Rectangle2D.Double(0.0, h[0], w[2],
                h[2]));
    }
    if (this.rightBlock != null) {
        this.rightBlock.setBounds(new Rectangle2D.Double(w[2] + w[4], h[0],
                w[3], h[2]));
    }
    if (this.centerBlock != null) {
        this.centerBlock.setBounds(new Rectangle2D.Double(w[2], h[0], w[4],
                h[4]));
    }
    return new Size2D(constraint.getWidth(), constraint.getHeight());
}
