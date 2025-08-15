public ArchiveInputStream createArchiveInputStream(final InputStream in)
        throws ArchiveException {
    if (in == null) {
        throw new IllegalArgumentException("Stream must not be null.");
    }

    if (!in.markSupported()) {
        throw new IllegalArgumentException("Mark is not supported.");
    }

    try {
        // Read first 12 bytes for initial signature check
        final byte[] signature = new byte[12];
        in.mark(signature.length);
        int signatureLength = in.read(signature);
        in.reset();

        if (ZipArchiveInputStream.matches(signature, signatureLength)) {
            return new ZipArchiveInputStream(in);
        } else if (JarArchiveInputStream.matches(signature, signatureLength)) {
            return new JarArchiveInputStream(in);
        } else if (ArArchiveInputStream.matches(signature, signatureLength)) {
            return new ArArchiveInputStream(in);
        } else if (CpioArchiveInputStream.matches(signature, signatureLength)) {
            return new CpioArchiveInputStream(in);
        }

        // Dump needs a bigger buffer to check the signature;
        final byte[] dumpsig = new byte[32];
        in.mark(dumpsig.length);
        signatureLength = in.read(dumpsig);
        in.reset();

        if (DumpArchiveInputStream.matches(dumpsig, signatureLength)) {
            return new DumpArchiveInputStream(in);
        }

        // Tar needs an even bigger buffer to check the signature; read the first block
        final byte[] tarheader = new byte[512];
        in.mark(tarheader.length);
        signatureLength = in.read(tarheader);
        in.reset();

        if (TarArchiveInputStream.matches(tarheader, signatureLength)) {
            return new TarArchiveInputStream(in);
        }
        
        // COMPRESS-117 - improve auto-recognition
        try {
            // Use the available tarheader bytes to test whether it's a tar archive
            TarArchiveInputStream tais = new TarArchiveInputStream(new ByteArrayInputStream(tarheader));
            ArchiveEntry entry = tais.getNextEntry();

            if (entry != null) {
                // If we can read an entry, assume this is a TAR archive
                return new TarArchiveInputStream(in);
            }
        } catch (Exception e) { // NOPMD
            // can generate IllegalArgumentException as well as IOException
            // autodetection, simply not a TAR
            // ignored
        }

    } catch (IOException e) {
        throw new ArchiveException("Could not use reset and mark operations.", e);
    }

    throw new ArchiveException("No Archiver found for the stream signature");
}
