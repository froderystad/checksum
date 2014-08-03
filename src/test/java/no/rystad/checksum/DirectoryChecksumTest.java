package no.rystad.checksum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryChecksumTest {

    @Test
    public void singleFile_whenInvokedWithLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = new DirectoryChecksum().singleFile("file-with-lf.txt");
        assertThat(md5Hex).isEqualTo("a67375254c3ab7986e0ab1844c03a36c");
    }

    @Test
    public void singleFile_whenInvokedWithCrLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = new DirectoryChecksum().singleFile("file-with-cr-lf.txt");
        assertThat(md5Hex).isEqualTo("ba6fe2e3fe961b25c0e6951ac396b459");
    }

    @Test
    public void singleFile_twoFilesWithSameContentAndDifferentNames_haveSameChecksum() throws Exception{
        DirectoryChecksum checksumProvider = new DirectoryChecksum();
        assertThat(checksumProvider.singleFile("file-with-lf.txt"))
                .isEqualTo(checksumProvider.singleFile("file-with-lf-copy.txt"));
    }

    @Test
    public void singleFile_whenIgnoringCr_checksumShouldEqualLf() throws Exception {
        DirectoryChecksum checksumProvider = new DirectoryChecksum();
        assertThat(checksumProvider.singleFile("file-with-cr-lf.txt", true))
                .isEqualTo(checksumProvider.singleFile("file-with-lf.txt"));
    }

}
