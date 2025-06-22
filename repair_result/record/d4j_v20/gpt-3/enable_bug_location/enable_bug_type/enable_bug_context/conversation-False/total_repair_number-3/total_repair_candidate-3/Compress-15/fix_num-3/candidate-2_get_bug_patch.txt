public boolean equals(Object obj) {
    if (this == obj) {
        return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
        return false;
    }
    ZipArchiveEntry other = (ZipArchiveEntry) obj;
    String myName = getName();
    String otherName = other.getName();
    if (myName == null) {
        if (otherName != null) {
            return false;
        }
    } else if (!myName.equals(otherName)) {
        return false;
    }
    String myComment = getComment();
    String otherComment = other.getComment();
    // Correct logical comparison for null and empty strings
    if ((myComment == null && otherComment != null && !otherComment.isEmpty()) ||
        (otherComment == null && myComment != null && !myComment.isEmpty())) {
        return false;
    } else if (myComment != null && otherComment != null && !myComment.equals(otherComment)) {
        return false;
    }
    return getTime() == other.getTime()
        && getInternalAttributes() == other.getInternalAttributes()
        && getPlatform() == other.getPlatform()
        && getExternalAttributes() == other.getExternalAttributes()
        && getMethod() == other.getMethod()
        && getSize() == other.getSize()
        && getCrc() == other.getCrc()
        && getCompressedSize() == other.getCompressedSize()
        && Arrays.equals(getCentralDirectoryExtra(),
                         other.getCentralDirectoryExtra())
        && Arrays.equals(getLocalFileDataExtra(),
                         other.getLocalFileDataExtra())
        && gpb.equals(other.gpb);
}
