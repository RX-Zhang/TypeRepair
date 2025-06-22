public ArchiveInputStream createArchiveInputStream(final InputStream in)
        throws ArchiveException {
    if (in == null) {
        throw new IllegalArgumentException("Stream must not be null.");
    }

    if (!in.markSupported()) {
        throw new IllegalArgumentException("Mark is not supported.");
    }

    final byte[] signature = new byte[12];
    in.mark(signature.length);
    try {
        int signatureLength = in.read(signature);
        in.reset();
        if (ZipArchiveInputStream.matches(signature, signatureLength)) {
            return createArchiveInputStream(ZIP, in);
        } else if (JarArchiveInputStream.matches(signature, signatureLength)) {
            return createArchiveInputStream(JAR, in);
        } else if (ArArchiveInputStream.matches(signature, signatureLength)) {
            return createArchiveInputStream(AR, in);
        } else if (CpioArchiveInputStream.matches(signature, signatureLength)) {
            return createArchiveInputStream(CPIO, in);
        }

        // Dump needs a bigger buffer to check the signature;
        final byte[] dumpsig = new byte[32];
        in.mark(dumpsig.length);
        signatureLength = in.read(dumpsig);
        in.reset();
        if (DumpArchiveInputStream.matches(dumpsig, signatureLength)) {
            return createArchiveInputStream(DUMP, in);
        }

        // Tar needs an even bigger buffer to check the signature; read the first block
        final byte[] tarheader = new byte[512];
        in.mark(tarheader.length);
        signatureLength = in.read(tarheader);
        in.reset();
        if (TarArchiveInputStream.matches(tarheader, signatureLength)) {
            return createArchiveInputStream(TAR, in);
        }
        // COMPRESS-117 - improve auto-recognition
        try {
            TarArchiveInputStream tais = new TarArchiveInputStream(new ByteArrayInputStream(tarheader));
            tais.getNextEntry();
            return createArchiveInputStream(TAR, in);
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
