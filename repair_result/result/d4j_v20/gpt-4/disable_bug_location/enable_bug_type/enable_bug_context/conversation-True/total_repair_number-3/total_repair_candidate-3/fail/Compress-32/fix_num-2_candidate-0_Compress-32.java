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
                // Use Long to prevent NumberFormatException on large gids
                long gidLong = Long.parseLong(val);
                if (gidLong > Integer.MAX_VALUE || gidLong < Integer.MIN_VALUE) {
                    currEntry.setGroupId((int)(gidLong & 0xFFFFFFFFL));
                } else {
                    currEntry.setGroupId((int)gidLong);
                }
            } else if ("gname".equals(key)){
                currEntry.setGroupName(val);
            } else if ("uid".equals(key)){
                long uidLong = Long.parseLong(val);
                if (uidLong > Integer.MAX_VALUE || uidLong < Integer.MIN_VALUE) {
                    currEntry.setUserId((int)(uidLong & 0xFFFFFFFFL));
                } else {
                    currEntry.setUserId((int)uidLong);
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
            // Log or ignore invalid number formats to avoid crashing
            // Could consider setting default or skipping
            // For now, ignore silently; alternatively could log warning
        }
    }
}
