package no.rystad.checksum;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class DirectoryChecksum {
    public String singleFile(String filename) throws IOException {
        return singleFile(filename, false);
    }

    public String singleFile(String filename, boolean ignoreCr) throws IOException {
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
