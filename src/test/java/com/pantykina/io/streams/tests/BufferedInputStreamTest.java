package com.pantykina.io.streams.tests;

import org.junit.Before;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class BufferedInputStreamTest {
    private String content = "JAVA UpSkill";
    private InputStream inputStream;
    private BufferedInputStream bufferedInputStream;
    private File file;
    private int count;

    @Before
    public void before() throws IOException {
        file = new File("File.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(content);
        }
        inputStream = new FileInputStream(file.getAbsolutePath());
        bufferedInputStream = new BufferedInputStream(inputStream);
    }

    @Test
    public void readSomeBytesFromInputStream() throws Exception {
        byte[] array = new byte[3];
        assertEquals(3, bufferedInputStream.read(array));
        assertEquals('J', array[0]);
        assertEquals('A', array[1]);
        assertEquals('V', array[2]);
        assertEquals(3, bufferedInputStream.read(array));
        assertEquals('A', array[0]);
        assertEquals(' ', array[1]);
        assertEquals('U', array[2]);
        assertEquals(3, bufferedInputStream.read(array));
        assertEquals('p', array[0]);
        assertEquals('S', array[1]);
        assertEquals('k', array[2]);
        assertEquals(3, bufferedInputStream.read(array));
        assertEquals('i', array[0]);
        assertEquals('l', array[1]);
        assertEquals('l', array[2]);
    }

    @Test
    public void readArrayOfBytes_byByteArrayInputStream() throws IOException {
        byte[] array = new byte[4];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Java".getBytes());
        byteArrayInputStream.read(array);
        String actual = new String(array);
        assertEquals("Java", actual);
    }

    @Test
    public void readBytesByBufferedInputStream() throws Exception {
        for (int i = 0; i < content.length(); i++) {
            assertEquals(content.charAt(i), bufferedInputStream.read());
            count++;
        }
        assertEquals(count, content.length());
    }

    @Test
    public void readArrayOfBytesByBufferedInputStream() throws Exception {
        byte[] array = new byte[5];
        assertEquals(5, bufferedInputStream.read(array));
    }

    @Test
    public void readByBufferedInputStreamWhenIncorrectParameterLengthIsIncorrect() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            int offset = 3;
            byte[] array = new byte[2];
            bufferedInputStream.read(array, offset, 1);
        });
    }

    @Test
    public void readByBufferedInputStreamWhenParameterOffIsIncorrect() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[3];
            bufferedInputStream.read(array, 0, 4);
        });
    }

    @Test
    public void whenReadEmptyArrayOfBytesThenNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            bufferedInputStream.read(null);
        });
    }

    @Test
    public void whenInputStreamIsClosedBufferedInputStreamCantReadAndReturnedIOExceptionReturned() {
        assertThrows(IOException.class, () -> {
            bufferedInputStream.close();
            bufferedInputStream.read();
        });
    }

    @Test
    public void whenReadArrayOfBytesTheEndOfStreamIsReachedThenReturnMinusOne() throws Exception {
        for (int i = 0; i < 0; i++) {
            assertEquals(-1, bufferedInputStream.read());
            count++;
        }
    }

    @Test
    public void whenReadArrayOfBytesIsNullThenReturnedNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            bufferedInputStream.read(null, 0, 6);
        });
    }

    @Test
    public void readByBufferedInputStreamWithIncorrectParameterLength() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            int offset = 6;
            byte[] array = new byte[5];
            bufferedInputStream.read(array, offset, 1);
        });
    }

    @Test
    public void readByBufferedInputStreamWithIncorrectParameterOff() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[3];
            bufferedInputStream.read(array, 0, 4);
        });
    }

    @Test
    public void readArrayOfBytesWithParameterLengthLessThanZero() {
        assertThrows(NegativeArraySizeException.class, () -> {
            byte[] array = new byte[-1];
            bufferedInputStream.read(array, 0, array.length);
        });
    }
}