public boolean equals(Object obj) {

    if (obj == this) {
        return true;
    }
    if (!(obj instanceof ShapeList)) {
        return false;
    }
    ShapeList other = (ShapeList) obj;
    int i = 0;
    while (true) {
        Shape s1, s2;
        try {
            s1 = this.getShape(i);
        } catch (IndexOutOfBoundsException e) {
            try {
                s2 = other.getShape(i);
            } catch (IndexOutOfBoundsException e2) {
                return true; // reached end of both lists
            }
            return false; // different lengths
        }
        try {
            s2 = other.getShape(i);
        } catch (IndexOutOfBoundsException e) {
            return false; // different lengths
        }
        if (s1 == null ? s2 != null : !s1.equals(s2)) {
            return false; // shapes differ
        }
        i++;
    }
}
