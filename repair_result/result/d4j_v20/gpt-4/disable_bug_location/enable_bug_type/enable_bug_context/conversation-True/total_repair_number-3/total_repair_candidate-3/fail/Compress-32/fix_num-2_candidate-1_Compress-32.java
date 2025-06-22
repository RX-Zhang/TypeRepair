private void applyPaxHeadersToCurrentEntry(Map<String, String> headers) {
    /*
     * The following headers are defined for Pax.
     * atime, ctime, charset: cannot use these without changing TarArchiveEntry fields
     * mtime
     * comment
     * gid, gname
     * linkpath
     * size
     * uid,uname
     * SCHILY.devminor, SCHILY.devmajor: don't have setters/getters for those
     */
    for (Entry<String, String> ent : headers.entrySet()){
        String key = ent.getKey();
        String val = ent.getValue();
        try {
            if ("path".equals(key)){
                currEntry.setName(val);
            } else if ("linkpath".equals(key)){
                currEntry.setLinkName(val);
            } else if ("gid".equals(key)){
                // Use Long to support big values, fallback to casting if needed
                long gid = Long.parseLong(val);
                if (gid <= Integer.MAX_VALUE && gid >= Integer.MIN_VALUE) {
                    currEntry.setGroupId((int) gid);
                } else {
                    currEntry.setGroupId((int) gid); // Note: this cast may lose info; ideally setGroupId should support long
                }
            } else if ("gname".equals(key)){
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)){
                long uid = Long.parseLong(val);
                if (uid <= Integer.MAX_VALUE && uid >= Integer.MIN_VALUE) {
                    currEntry.setUserId((int) uid);
                } else {
                    currEntry.setUserId((int) uid); // Similar cast caution as above
                }
            } else if ("uname".equals(key)){
                currEntry.setUserName(val);
            } else if ("size".equals(key)){
                currEntry.setSize(Long.parseLong(val));
            } else if ("mtime".equals(key)){
                currEntry.setModTime((long) (Double.parseDouble(val) * 1000));
            } else if ("SCHILY.devminor".equals(key)){
                currEntry.setDevMinor(Integer.parseInt(val));
            } else if ("SCHILY.devmajor".equals(key)){
                currEntry.setDevMajor(Integer.parseInt(val));
            }
        } catch (NumberFormatException e) {
            // Log or handle invalid number format gracefully, skip setting that field
            // e.g. System.err.println("Invalid number format for key " + key + " with value " + val);
        }
    }
}
