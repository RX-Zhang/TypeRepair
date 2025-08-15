public boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }
    if (!(obj instanceof ShapeList)) {
        return false;
    }
    ShapeList that = (ShapeList) obj;

    int size = this.size();
    if (size != that.size()) {
        return false;
    }

    for (int i = 0; i < size; i++) {
        Object shape1 = this.getShape(i);
        Object shape2 = that.getShape(i);
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
