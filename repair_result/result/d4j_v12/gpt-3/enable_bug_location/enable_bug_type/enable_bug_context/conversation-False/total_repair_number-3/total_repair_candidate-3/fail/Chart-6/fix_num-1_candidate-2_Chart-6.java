public boolean equals(Object obj) {

    if (obj == this) {
        return true;
    }
    if (!(obj instanceof ShapeList)) {
        return false;
    }
    ShapeList that = (ShapeList) obj;

    int size = this.size();
    if (that.size() != size) {
        return false;
    }

    for (int i = 0; i < size; i++) {
        Object thisShape = this.getShape(i);
        Object thatShape = that.getShape(i);
        if (thisShape == null) {
            if (thatShape != null) {
                return false;
            }
        }
        else {
            if (!thisShape.equals(thatShape)) {
                return false;
            }
        }
    }

    return true;
}
