package no.rystad.checksum;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DirectoryChecksumTest {

    private static final String TESTFILES_FOLDER = "no/rystad/checksum/";
    private final DirectoryChecksum checksumCalculator = new DirectoryChecksum();

    @Test
    public void readingCr_whenInvokedWithLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = checksumCalculator.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-lf.txt");
        assertThat(md5Hex).isEqualTo("a67375254c3ab7986e0ab1844c03a36c");
    }

    @Test
    public void readingCr_whenInvokedWithCrLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = checksumCalculator.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-cr-lf.txt");
        assertThat(md5Hex).isEqualTo("ba6fe2e3fe961b25c0e6951ac396b459");
    }

    @Test
    public void ignoringCr_whenInvokedWithCrLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = checksumCalculator.md5SumOfFileFromClassPathIgnoreCr(TESTFILES_FOLDER + "file-with-cr-lf.txt");
        assertThat(md5Hex).isEqualTo("a67375254c3ab7986e0ab1844c03a36c");
    }

    @Test
    public void readingCr_twoFilesWithSameContentAndDifferentNames_haveSameChecksum() throws Exception{
        DirectoryChecksum checksumProvider = checksumCalculator;
        assertThat(checksumProvider.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-lf.txt"))
                .isEqualTo(checksumProvider.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-lf-copy.txt"));
    }

    @Test
    public void ignoringCr_oneFileWithCrLfAndOneFileWithCr_haveSameChecksum() throws Exception {
        DirectoryChecksum checksumProvider = checksumCalculator;
        assertThat(checksumProvider.md5SumOfFileFromClassPathIgnoreCr(TESTFILES_FOLDER + "file-with-cr-lf.txt"))
                .isEqualTo(checksumProvider.md5SumOfFileFromClassPathIgnoreCr(TESTFILES_FOLDER + "file-with-lf.txt"));
    }

}
