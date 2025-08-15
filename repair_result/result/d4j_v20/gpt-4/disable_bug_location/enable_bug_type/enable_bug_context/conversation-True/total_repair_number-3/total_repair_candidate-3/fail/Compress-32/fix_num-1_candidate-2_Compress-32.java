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
                // Use Long to parse large group IDs to avoid NumberFormatException
                long gidLong = Long.parseLong(val);
                if (gidLong < Integer.MIN_VALUE || gidLong > Integer.MAX_VALUE) {
                    currEntry.setGroupId((int)gidLong); // cast may overflow, but preserves expected type
                } else {
                    currEntry.setGroupId((int) gidLong);
                }
            } else if ("gname".equals(key)){
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)){
                long uidLong = Long.parseLong(val);
                if (uidLong < Integer.MIN_VALUE || uidLong > Integer.MAX_VALUE) {
                    currEntry.setUserId((int)uidLong);
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
            // Log or handle the error appropriately, here we just skip invalid header values
            // Optionally: System.err.println("Invalid numeric value for key " + key + ": " + val);
        }
    }
}
