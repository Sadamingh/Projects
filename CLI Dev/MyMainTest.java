package edu.gatech.seclass.txted;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MyMainTest {

    @Rule
    public final TemporaryFolder temporaryFolder = new TemporaryFolder();
    private final Charset charset = StandardCharsets.UTF_8;
    private ByteArrayOutputStream outStream;
    private ByteArrayOutputStream errStream;
    private PrintStream outOrig;
    private PrintStream errOrig;

    @Before
    public void setUp() throws Exception {
        outStream = new ByteArrayOutputStream();
        PrintStream out = new PrintStream(outStream);
        errStream = new ByteArrayOutputStream();
        PrintStream err = new PrintStream(errStream);
        outOrig = System.out;
        errOrig = System.err;
        System.setOut(out);
        System.setErr(err);
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(outOrig);
        System.setErr(errOrig);
    }

    /*
     *  TEST UTILITIES
     */

    // Create File Utility
    private File createTmpFile() throws Exception {
        File tmpfile = temporaryFolder.newFile();
        tmpfile.deleteOnExit();
        return tmpfile;
    }

    // Write File Utility
    private File createInputFile(String input) throws Exception {
        File file = createTmpFile();
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
        fileWriter.write(input);
        fileWriter.close();
        return file;
    }

    private String getFileContent(String filename) {
        String content = null;
        try {
            content = Files.readString(Paths.get(filename), charset);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    // Place all  of your tests in this class, optionally using MainTest.java as an example.
    // Frame #: 1
    @Test
    public void txtedTest1() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 2
    @Test
    public void txtedTest2() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 3
    @Test
    public void txtedTest3() throws Exception {
        String input = "01234$*ϕ∴↘" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "$*ϕ∴↘01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789def" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "$*ϕ∴↘", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 4
    @Test
    public void txtedTest4() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-i", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 5
    @Test
    public void txtedTest5() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 6
    @Test
    public void txtedTest6() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789def" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "1", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 7
    @Test
    public void txtedTest7() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "2", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 8
    @Test
    public void txtedTest8() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "a", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 9
    @Test
    public void txtedTest9() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 10
    @Test
    public void txtedTest10() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar$*ϕ∴↘" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", "$*ϕ∴↘", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 11
    @Test
    public void txtedTest11() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 12
    // Update: -n 0 should not throw an error, just left padding
    @Test
    public void txtedTest12() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = " alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 13
    @Test
    public void txtedTest13() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "-1", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 14
    @Test
    public void txtedTest14() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "a", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 15
    // Update: probably we should remove the empty string
    @Test
    public void txtedTest15() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobar" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 16
    @Test
    public void txtedTest16() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234abc" + System.lineSeparator() +
                "002 56789def" + System.lineSeparator() +
                "003 01234ABC" + System.lineSeparator() +
                "004 56789DEF" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 17
    @Test
    public void txtedTest17() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789DEF" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234abc" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 18
    @Test
    public void txtedTest18() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 56789DEF" + System.lineSeparator() +
                "002 01234ABC" + System.lineSeparator() +
                "003 56789def" + System.lineSeparator() +
                "004 01234abc" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 19
    @Test
    public void txtedTest19() throws Exception {
        String input = "alphanumeric123foobar" + System.lineSeparator();
        String expected = "alphanumeric123foobarabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 20
    @Test
    public void txtedTest20() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234abcabc123!!" + System.lineSeparator() +
                "002 56789defabc123!!" + System.lineSeparator() +
                "003 01234ABCabc123!!" + System.lineSeparator() +
                "004 56789DEFabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 21
    @Test
    public void txtedTest21() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "56789DEFabc123!!" + System.lineSeparator() +
                "01234ABCabc123!!" + System.lineSeparator() +
                "56789defabc123!!" + System.lineSeparator() +
                "01234abcabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-r", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 22
    @Test
    public void txtedTest22() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 56789DEFabc123!!" + System.lineSeparator() +
                "002 01234ABCabc123!!" + System.lineSeparator() +
                "003 56789defabc123!!" + System.lineSeparator() +
                "004 01234abcabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", "-r", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 23
    @Test
    public void txtedTest23() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234abc" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 24
    @Test
    public void txtedTest24() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234abc" + System.lineSeparator() +
                "002 01234ABC" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 25
    @Test
    public void txtedTest25() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234ABC" + System.lineSeparator() +
                "01234abc" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 26
    @Test
    public void txtedTest26() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234ABC" + System.lineSeparator() +
                "002 01234abc" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 27
    @Test
    public void txtedTest27() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234abcabc123!!" + System.lineSeparator() +
                "01234ABCabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 28
    @Test
    public void txtedTest28() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234abcabc123!!" + System.lineSeparator() +
                "002 01234ABCabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-x", "abc123!!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 29
    @Test
    public void txtedTest29() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "01234ABCabc123!!" + System.lineSeparator() +
                "01234abcabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-x", "abc123!!", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 30
    @Test
    public void txtedTest30() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "001 01234ABCabc123!!" + System.lineSeparator() +
                "002 01234abcabc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-s", "0", "-x", "abc123!!", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 31
    @Test
    public void txtedTest31() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 32
    @Test
    public void txtedTest32() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789def123" + System.lineSeparator() +
                "002 01234ABC123" + System.lineSeparator() +
                "003 56789DEF123" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 33
    @Test
    public void txtedTest33() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789DEF123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 34
    @Test
    public void txtedTest34() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789DEF123" + System.lineSeparator() +
                "002 01234ABC123" + System.lineSeparator() +
                "003 56789def123" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 35
    @Test
    public void txtedTest35() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789def123abc123!!" + System.lineSeparator() +
                "01234ABC123abc123!!" + System.lineSeparator() +
                "56789DEF123abc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 36
    @Test
    public void txtedTest36() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789def123abc123!!" + System.lineSeparator() +
                "002 01234ABC123abc123!!" + System.lineSeparator() +
                "003 56789DEF123abc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-x", "abc123!!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 37
    @Test
    public void txtedTest37() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789DEF123abc123!!" + System.lineSeparator() +
                "01234ABC123abc123!!" + System.lineSeparator() +
                "56789def123abc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-x", "abc123!!", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 38
    // Update: should not have numbers
    @Test
    public void txtedTest38() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789DEF123abc123!!" + System.lineSeparator() +
                "01234ABC123abc123!!" + System.lineSeparator() +
                "56789def123abc123!!" + System.lineSeparator();

        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-x", "abc123!!", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 39
    @Test
    public void txtedTest39() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "01234ABC123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 40
    @Test
    public void txtedTest40() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 01234ABC123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 41
    @Test
    public void txtedTest41() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "01234ABC123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 42
    @Test
    public void txtedTest42() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 01234ABC123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 43
    @Test
    public void txtedTest43() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "01234ABC123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 44
    @Test
    public void txtedTest44() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 01234ABC123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-x", "abc123!!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 45
    @Test
    public void txtedTest45() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "01234ABC123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-x", "abc123!!", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 46
    @Test
    public void txtedTest46() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 01234ABC123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-s", "0", "-x", "abc123!!", "-r", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 47
    @Test
    public void txtedTest47() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789def123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 48
    @Test
    public void txtedTest48() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789def123" + System.lineSeparator() +
                "002 56789DEF123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 49
    @Test
    public void txtedTest49() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789DEF123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 50
    @Test
    public void txtedTest50() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789DEF123" + System.lineSeparator() +
                "002 56789def123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 51
    @Test
    public void txtedTest51() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789def123abc123!!" + System.lineSeparator() +
                "56789DEF123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 52
    @Test
    public void txtedTest52() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789def123abc123!!" + System.lineSeparator() +
                "002 56789DEF123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-x", "abc123!!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 53
    @Test
    public void txtedTest53() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "56789DEF123abc123!!" + System.lineSeparator() +
                "56789def123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-x", "abc123!!", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 54
    @Test
    public void txtedTest54() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator();
        String expected = "001 56789DEF123abc123!!" + System.lineSeparator() +
                "002 56789def123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-x", "abc123!!", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 55
    // Update: Update input and expected
    @Test
    public void txtedTest55() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();
        String expected = "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 56
    // Update: Update input and expected
    @Test
    public void txtedTest56() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "001 56789XYZ123" + System.lineSeparator() +
                "002 56789xyz123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 57
    // Update: Update input and expected
    @Test
    public void txtedTest57() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "56789xyz123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 58
    // Update: Update input and expected
    @Test
    public void txtedTest58() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "001 56789xyz123" + System.lineSeparator() +
                "002 56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-n", "3", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 59
    // Update: Update input and expected
    @Test
    public void txtedTest59() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "56789XYZ123abc123!!" + System.lineSeparator() +
                "56789xyz123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 60
    // Update: Update input and expected
    @Test
    public void txtedTest60() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "001 56789XYZ123abc123!!" + System.lineSeparator() +
                "002 56789xyz123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-x", "abc123!!", "-n", "3", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 61
    // Update: Update input and expected
    @Test
    public void txtedTest61() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "56789xyz123abc123!!" + System.lineSeparator() +
                "56789XYZ123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-r", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 62
    // Update: Update input and expected
    @Test
    public void txtedTest62() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "001 56789xyz123abc123!!" + System.lineSeparator() +
                "002 56789XYZ123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-n", "3", "-r", "-x", "abc123!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 63
    // Edge case: Empty "-e" parameter
    @Test
    public void txtedTest63() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 64
    // Edge case: Negative "-s" parameter
    @Test
    public void txtedTest64() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-s", "-1", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 65
    // Edge case: Negative "-n" parameter
    @Test
    public void txtedTest65() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "-1", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 66
    // Edge case: Empty "-x" parameter
    @Test
    public void txtedTest66() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-x", "", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 67
    // Edge case: Repeated "-n"
    @Test
    public void txtedTest67() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();
        String expected = "01 01234abc123" + System.lineSeparator() +
                "02 56789def123" + System.lineSeparator() +
                "03 01234ABC123" + System.lineSeparator() +
                "04 56789DEF123" + System.lineSeparator() +
                "05 56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", "-n", "2", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 68
    // Edge case: Repeated "-e"
    @Test
    public void txtedTest68() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();
        String expected = "01234abc123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc", "-e", "def", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 69
    // Edge case: Repeated "-s"
    @Test
    public void txtedTest69() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();
        String expected = "01234abc123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-s", "1", "-s", "0", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 70
    // Edge case: Repeated "-x"
    @Test
    public void txtedTest70() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();
        String expected = "01234abc123?" + System.lineSeparator() +
                "56789def123?" + System.lineSeparator() +
                "01234ABC123?" + System.lineSeparator() +
                "56789DEF123?" + System.lineSeparator() +
                "56789XYZ123?" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-x", "!", "-x", "?", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 71
    // Edge case: Repeated "-r"
    @Test
    public void txtedTest71() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator();
        String expected = "56789def123" + System.lineSeparator() +
                "01234abc123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-r", "-r", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 72
    // Edge case: Empty "-n" parameter
    @Test
    public void txtedTest72() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 73
    // New Case: write to file with all the params
    @Test
    public void txtedTest73() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator() +
                "56789RST123" + System.lineSeparator() +
                "56789xyz123" + System.lineSeparator();
        String expected = "001 56789xyz123abc123!!" + System.lineSeparator() +
                "002 56789XYZ123abc123!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", "abc123", "-i", "-s", "0", "-n", "3", "-r", "-x", "abc123!!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 74
    // New Case: Example case 9
    @Test
    public void txtedTest74() throws Exception {
        String input = "alphanumeric123foo" + System.lineSeparator() +
                "alphanumeric123Foo" + System.lineSeparator() +
                "alphanumeric123FOO" + System.lineSeparator() +
                "alphanumeric123bar" + System.lineSeparator() +
                "alphanumeric123Bar" + System.lineSeparator() +
                "alphanumeric123BAR" + System.lineSeparator() +
                "alphanumeric123foobar" + System.lineSeparator() +
                "alphanumeric123Foobar" + System.lineSeparator() +
                "alphanumeric123fooBar" + System.lineSeparator() +
                "alphanumeric123FooBar" + System.lineSeparator() +
                "alphanumeric123FOOBar" + System.lineSeparator() +
                "alphanumeric123FooBAR" + System.lineSeparator() +
                "alphanumeric123FOOBAR" + System.lineSeparator();
        String expected = "01 alphanumeric123FOOBAR!" + System.lineSeparator() +
                "02 alphanumeric123foobar!" + System.lineSeparator() +
                "03 alphanumeric123FOO!" + System.lineSeparator() +
                "04 alphanumeric123foo!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "3", "-r", "-e", "Bar", "-s", "0", "-n", "2", "-x", "!", "-f", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("File differs from expected", expected, getFileContent(inputFile.getPath()));
    }

    // Frame #: 75
    // New Case: write to file with all the params
    @Test
    public void txtedTest75() throws Exception {
        String input = "01234abc" + System.lineSeparator() +
                "56789def" + System.lineSeparator() +
                "01234ABC" + System.lineSeparator() +
                "56789DEF" + System.lineSeparator();
        String expected = "0001 56789DEF!!!" + System.lineSeparator() +
                "0002 01234ABC!!!" + System.lineSeparator() +
                "0003 56789def!!!" + System.lineSeparator() +
                "0004 01234abc!!!" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "4", "-r", "-x", "!!!", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 76
    // New Case: Left padding -n
    @Test
    public void txtedTest76() throws Exception {
        String input = "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator() +
                "*" + System.lineSeparator();
        String expected = "1 *" + System.lineSeparator() +
                "2 *" + System.lineSeparator() +
                "3 *" + System.lineSeparator() +
                "4 *" + System.lineSeparator() +
                "5 *" + System.lineSeparator() +
                "6 *" + System.lineSeparator() +
                "7 *" + System.lineSeparator() +
                "8 *" + System.lineSeparator() +
                "9 *" + System.lineSeparator() +
                "0 *" + System.lineSeparator() +
                "1 *" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", "1", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 77
    // Edge case: Non-option error
    @Test
    public void txtedTest77() throws Exception {
        String input = "01234abc123" + System.lineSeparator() +
                "56789def123" + System.lineSeparator() +
                "01234ABC123" + System.lineSeparator() +
                "56789DEF123" + System.lineSeparator() +
                "56789XYZ123" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-t", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 78
    // Edge case: -e <Space> string
    @Test
    public void txtedTest78() throws Exception {
        String input = "Anything" + System.lineSeparator();
        String expected = "Anything" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-e", " ", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 79
    // Edge case: -x <Space> string
    @Test
    public void txtedTest79() throws Exception {
        String input = "Anything" + System.lineSeparator();
        String expected = "Anything " + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-x", " ", inputFile.getPath()};
        Main.main(args);
        assertTrue("Unexpected stderr output", errStream.toString().isEmpty());
        assertEquals("Output differs from expected", expected, outStream.toString());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 80
    // Edge case: -n <Space> string
    @Test
    public void txtedTest80() throws Exception {
        String input = "Anything" + System.lineSeparator();
        String expected = "Anything" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-n", " ", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

    // Frame #: 81
    // Edge case: -s <Space> string
    @Test
    public void txtedTest81() throws Exception {
        String input = "Anything" + System.lineSeparator();
        String expected = "Anything" + System.lineSeparator();


        File inputFile = createInputFile(input);
        String[] args = {"-s", " ", inputFile.getPath()};
        Main.main(args);
        assertEquals("Usage: txted [ -f | -i | -s integer | -e string | -r | -x string | -n integer ] FILE", errStream.toString().trim());
        assertTrue("Unexpected stdout output", outStream.toString().isEmpty());
        assertEquals("input file modified", input, getFileContent(inputFile.getPath()));
    }

}
