package com.pantykina.io.streams.tests;

import com.pantykina.io.ByteArrayInputStream;
import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;
public class ByteArrayInputStreamTest {

    @Test
    public void readArrayOfBytesByteArrayWithParameters() throws IOException {
        byte[] array = new byte[5];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Java".getBytes());
        byteArrayInputStream.read(array, 0, 4);
        String actual = new String(array, 0, 1);
        String actual1 = new String(array, 0, 2);
        String actual2 = new String(array, 0, 3);
        String actual3 = new String(array, 0, 4);
        assertEquals("J", actual);
        assertEquals("Ja", actual1);
        assertEquals("Jav", actual2);
        assertEquals("Java", actual3);
    }

    @Test
    public void testReadBytes() throws IOException {
        String content = "Java";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        assertEquals('J', (char) byteArrayInputStream.read());
        assertEquals('a', (char) byteArrayInputStream.read());
        assertEquals('v', (char) byteArrayInputStream.read());
        assertEquals('a', (char) byteArrayInputStream.read());
        assertEquals(-1, byteArrayInputStream.read());
    }

    @Test
    public void whenReadNotAvailableBytesThenMinusOneReturned() throws IOException {
        String content = "";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(content.getBytes());
        int actual = byteArrayInputStream.read();
        assertEquals(-1, actual);
    }

    @Test
    public void readArrayOfBytes() throws IOException {
        byte[] array = new byte[4];
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Java".getBytes());
        byteArrayInputStream.read(array);
        String actual = new String(array);
        assertEquals("Java", actual);
    }

    @Test
    public void readByByteArrayWithIncorrectParameterOff() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[4];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Java".getBytes());
            byteArrayInputStream.read(array, 0, 5);
        });
    }

    @Test
    public void whenReadEmptyArrayOfBytesThenReturnedNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            byte[] buffer = null;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("".getBytes());
            byteArrayInputStream.read(buffer);
        });
    }

    @Test

    public void readArrayOfBytes_byByteArrayInputStream_withIncorrectParameterLength() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("Hello".getBytes());
            byteArrayInputStream.read(array, 1, 5);
        });
    }
}
