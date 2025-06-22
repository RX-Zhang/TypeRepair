protected Size2D arrangeFF(BlockContainer container, Graphics2D g2,
                           RectangleConstraint constraint) {
    double[] w = new double[5];
    double[] h = new double[5];
    w[0] = constraint.getWidth();
    double constraintHeight = constraint.getHeight();
    h[0] = 0.0;
    h[1] = 0.0;
    w[2] = 0.0;
    w[3] = 0.0;
    w[4] = 0.0;
    h[4] = 0.0;

    if (this.topBlock != null) {
        RectangleConstraint c1 = new RectangleConstraint(w[0], null,
                LengthConstraintType.FIXED, 0.0,
                new Range(0.0, constraintHeight),
                LengthConstraintType.RANGE);
        Size2D size = this.topBlock.arrange(g2, c1);
        h[0] = size.height;
    }
    if (this.bottomBlock != null) {
        double bottomMaxHeight = constraintHeight - h[0];
        if (bottomMaxHeight < 0.0) {
            bottomMaxHeight = 0.0;
        }
        RectangleConstraint c2 = new RectangleConstraint(w[0], null,
                LengthConstraintType.FIXED, 0.0,
                new Range(0.0, bottomMaxHeight),
                LengthConstraintType.RANGE);
        Size2D size = this.bottomBlock.arrange(g2, c2);
        h[1] = size.height;
    }
    double middleHeight = constraintHeight - h[0] - h[1];
    if (middleHeight < 0.0) {
        middleHeight = 0.0;
    }
    h[2] = middleHeight;

    if (this.leftBlock != null) {
        RectangleConstraint c3 = new RectangleConstraint(0.0,
                new Range(0.0, w[0]),
                LengthConstraintType.RANGE, h[2], null,
                LengthConstraintType.FIXED);
        Size2D size = this.leftBlock.arrange(g2, c3);
        w[2] = size.width;
    }
    if (this.rightBlock != null) {
        double rightMaxWidth = w[0] - w[2];
        if (rightMaxWidth < 0.0) {
            rightMaxWidth = 0.0;
        }
        RectangleConstraint c4 = new RectangleConstraint(0.0,
                new Range(0.0, rightMaxWidth),
                LengthConstraintType.RANGE, h[2], null,
                LengthConstraintType.FIXED);
        Size2D size = this.rightBlock.arrange(g2, c4);
        w[3] = size.width;
    }
    double centerWidth = w[0] - w[2] - w[3];
    if (centerWidth < 0.0) {
        centerWidth = 0.0;
    }
    w[4] = centerWidth;
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
    return new Size2D(w[0], constraintHeight);
}
