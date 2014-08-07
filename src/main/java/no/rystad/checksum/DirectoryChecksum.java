package no.rystad.checksum;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

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
            return DigestUtils.md5Hex(is);
        }
    }

    private InputStream getInputStream(URL lfFileUrl, boolean ignoreCr) throws IOException {
        InputStream inputStream = lfFileUrl.openStream();

        if (ignoreCr) {
            return new NoCrInputStream(inputStream);
        }

        return inputStream;
    }
}
