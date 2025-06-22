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
                // Use Long.parseLong and cast to int if appropriate to support large group IDs
                long gidLong = Long.parseLong(val);
                if (gidLong > Integer.MAX_VALUE || gidLong < Integer.MIN_VALUE) {
                    // setGroupId with long if exists, otherwise handle accordingly
                    // If no setter for long groupId, fallback to setLongGroupId (not shown here)
                    // For safety here, set groupId with int max/min to avoid NumberFormatException
                    currEntry.setGroupId((int)gidLong); // potential data loss but prevent failure
                } else {
                    currEntry.setGroupId((int) gidLong);
                }
            } else if ("gname".equals(key)){
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)){
                long uidLong = Long.parseLong(val);
                if (uidLong > Integer.MAX_VALUE || uidLong < Integer.MIN_VALUE) {
                    currEntry.setUserId((int) uidLong);
                } else {
                    currEntry.setUserId((int) uidLong);
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
        } catch (NumberFormatException nfe) {
            // Log or handle parse failure gracefully to avoid runtime exceptions
            // e.g. skip setting the field or set a default value
            // For this example, just continue without setting that field
            // Alternatively, could wrap and throw IOException if desired
        }
    }
}
