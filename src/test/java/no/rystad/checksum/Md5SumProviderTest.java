package no.rystad.checksum;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Md5SumProviderTest {

    private static final String TESTFILES_FOLDER = "no/rystad/checksum/";
    private final Md5SumProvider md5Calculator = new Md5SumProvider();

    @Test
    public void readingCr_whenInvokedWithLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = md5Calculator.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-lf.txt");
        assertThat(md5Hex).isEqualTo("a67375254c3ab7986e0ab1844c03a36c");
    }

    @Test
    public void readingCr_whenInvokedWithCrLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = md5Calculator.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-cr-lf.txt");
        assertThat(md5Hex).isEqualTo("ba6fe2e3fe961b25c0e6951ac396b459");
    }

    @Test
    public void ignoringCr_whenInvokedWithCrLfEndings_shouldCalculateCorrectMd5Sum() throws Exception {
        String md5Hex = md5Calculator.md5SumOfFileFromClassPathIgnoreCr(TESTFILES_FOLDER + "file-with-cr-lf.txt");
        assertThat(md5Hex).isEqualTo("a67375254c3ab7986e0ab1844c03a36c");
    }

    @Test
    public void readingCr_twoFilesWithSameContentAndDifferentNames_haveSameChecksum() throws Exception {
        assertThat(md5Calculator.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-lf.txt"))
                .isEqualTo(md5Calculator.md5SumOfFileFromClassPath(TESTFILES_FOLDER + "file-with-lf-copy.txt"));
    }

    @Test
    public void ignoringCr_oneFileWithCrLfAndOneFileWithCr_haveSameChecksum() throws Exception {
        assertThat(md5Calculator.md5SumOfFileFromClassPathIgnoreCr(TESTFILES_FOLDER + "file-with-cr-lf.txt"))
                .isEqualTo(md5Calculator.md5SumOfFileFromClassPathIgnoreCr(TESTFILES_FOLDER + "file-with-lf.txt"));
    }

    @Test
    public void multipleFiles_shouldCalculateChecksum() throws Exception {
        List<String> filesetA = Arrays.asList(
                TESTFILES_FOLDER + "file-with-cr-lf.txt",
                TESTFILES_FOLDER + "file-with-lf.txt");
        List<String> filesetB = Arrays.asList(
                TESTFILES_FOLDER + "file-with-cr-lf.txt",
                TESTFILES_FOLDER + "file-with-lf-copy.txt");

        assertThat(md5Calculator.md5SumOfFileFromClassPathIgnoreCr(filesetA))
                .isEqualTo(md5Calculator.md5SumOfFileFromClassPathIgnoreCr(filesetB));
    }

}
