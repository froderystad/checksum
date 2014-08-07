package no.rystad.checksum;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DirectoryChecksum {
    public String md5SumOfFileFromClassPath(String filename) throws IOException {
        return md5SumOfFileFromClassPath(filename, false);
    }

    public String md5SumOfFileFromClassPathIgnoreCr(String filename) throws IOException {
        return md5SumOfFileFromClassPath(filename, true);
    }

    private String md5SumOfFileFromClassPath(String filename, boolean ignoreCr) throws IOException {
        URL lfFileUrl = getClass().getClassLoader().getResource(filename);

        try (InputStream is = getInputStream(lfFileUrl, ignoreCr)) {
            MessageDigest digest = getMd5();
            byte[] buffer = new byte[8192];
            int len = 0;
            while (-1 != (len = is.read(buffer))) {
                digest.update(buffer,0,len);
            }
            byte[] md5hash = digest.digest();

            return toHashString(md5hash);
        }
    }

    private InputStream getInputStream(URL lfFileUrl, boolean ignoreCr) throws IOException {
        InputStream inputStream = lfFileUrl.openStream();

        if (ignoreCr) {
            return new NoCrInputStream(inputStream);
        }

        return inputStream;
    }

    private MessageDigest getMd5() {
        try {
            return MessageDigest.getInstance("md5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private String toHashString(byte[] md5hash) {
        return new BigInteger(1, md5hash).toString(16);
    }
}
