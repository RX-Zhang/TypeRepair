public boolean equals(Object obj) {

    if (obj == this) {
        return true;
    }
    if (!(obj instanceof ShapeList)) {
        return false;
    }
    ShapeList other = (ShapeList) obj;
    int size = this.size();
    if (size != other.size()) {
        return false;
    }
    for (int i = 0; i < size; i++) {
        Shape shape1 = this.getShape(i);
        Shape shape2 = other.getShape(i);
        if (shape1 == null) {
            if (shape2 != null) {
                return false;
            }
        } else {
            if (!shape1.equals(shape2)) {
                return false;
            }
        }
    }
    return true;
}
