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
                // Use Long.parseLong and cast to int, or handle large group IDs safely
                // Use Long.parseLong and set via appropriate method if available
                long gidLong = Long.parseLong(val);
                if (gidLong > Integer.MAX_VALUE || gidLong < Integer.MIN_VALUE) {
                    // If groupId is too large for int, handle accordingly
                    // If currEntry supports setLongGroupId, use it
                    try {
                        currEntry.getClass()
                            .getMethod("setLongGroupId", long.class)
                            .invoke(currEntry, gidLong);
                    } catch (ReflectiveOperationException ex) {
                        // Fall back to setGroupId with int casting - may lose data
                        currEntry.setGroupId((int) gidLong);
                    }
                } else {
                    currEntry.setGroupId((int)gidLong);
                }
            } else if ("gname".equals(key)){
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)){
                long uidLong = Long.parseLong(val);
                if (uidLong > Integer.MAX_VALUE || uidLong < Integer.MIN_VALUE) {
                    try {
                        currEntry.getClass()
                            .getMethod("setLongUserId", long.class)
                            .invoke(currEntry, uidLong);
                    } catch (ReflectiveOperationException ex) {
                        currEntry.setUserId((int) uidLong);
                    }
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
        } catch (NumberFormatException e) {
            // log or handle invalid numeric format, skip setting field
            // Here we just skip to avoid breaking the process
        } catch (Exception e) {
            // Catch-all for reflection or other exceptions, ignore to continue processing other headers
        }
    }
}
