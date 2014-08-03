package no.rystad.checksum;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class NoCrInputStream extends FilterInputStream {

    public NoCrInputStream(InputStream is) {
        super(new BufferedInputStream(is));
    }

    @Override
    public int read(byte[] b) throws IOException {
        return this.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (b == null) {
            throw new NullPointerException();
        } else if (off < 0 || len < 0 || len > b.length - off) {
            throw new IndexOutOfBoundsException();
        } else if (len == 0) {
            return 0;
        }

        int c = read();
        if (c == -1) {
            return -1;
        }
        b[off] = (byte) c;

        int i = 1;
        try {
            for (; i < len; i++) {
                c = read();
                if (c == -1) {
                    break;
                }
                b[off + i] = (byte) c;
            }
        } catch (IOException ee) {
        }
        return i;
    }


    @Override
    public int read() throws IOException {
        int c;
        do {
            c = super.read();
        } while (c != -1 && c == '\r');
        return c;
    }
}
