@Override
public boolean equals(Object obj) {
    if (obj == this) {
        return true;
    }
    if (!(obj instanceof ShapeList)) {
        return false;
    }
    ShapeList that = (ShapeList) obj;
    if (this.size() != that.size()) {
        return false;
    }
    for (int i = 0; i < this.size(); i++) {
        if (this.getShape(i) == null) {
            if (that.getShape(i) != null) {
                return false;
            }
        }
        else {
            if (!this.getShape(i).equals(that.getShape(i))) {
                return false;
            }
        }
    }
    return true;
}
