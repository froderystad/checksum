package no.rystad.checksum;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Md5SumProvider {

    public String md5SumOfFileFromClassPath(String filename) throws IOException {
        return md5SumOfFilesFromClassPath(Arrays.asList(filename), false);
    }

    public String md5SumOfFileFromClassPathIgnoreCr(String filename) throws IOException {
        return md5SumOfFilesFromClassPath(Arrays.asList(filename), true);
    }

    public String md5SumOfFileFromClassPathIgnoreCr(List<String> filenames) throws IOException {
        return md5SumOfFilesFromClassPath(filenames, true);
    }

    private String md5SumOfFilesFromClassPath(List<String> filenames, boolean ignoreCr) throws IOException {
        MessageDigest digest = getMd5();

        for (String filename : filenames) {
            URL lfFileUrl = getClass().getClassLoader().getResource(filename);

            try (InputStream is = getInputStream(lfFileUrl, ignoreCr)) {
                byte[] buffer = new byte[8192];
                int len;
                while (-1 != (len = is.read(buffer))) {
                    digest.update(buffer, 0, len);
                }
            }
        }

        byte[] md5hash = digest.digest();
        return toHashString(md5hash);
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
            throw new RuntimeException("This JVM has no registered MD5 provider", e);
        }
    }

    private String toHashString(byte[] md5hash) {
        return new BigInteger(1, md5hash).toString(16);
    }
}
