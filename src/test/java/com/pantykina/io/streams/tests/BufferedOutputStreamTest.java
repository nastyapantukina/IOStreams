package com.pantykina.io.streams.tests;

import com.pantykina.io.BufferedOutputStream;
import com.pantykina.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.*;

public class BufferedOutputStreamTest {
    private BufferedOutputStream bufferedOutputStream;
    private ByteArrayOutputStream byteArrayOutputStream;
    private byte[] content;

    @Before
    public void before() {
        content = "Java upskill".getBytes();
        byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedOutputStream = new BufferedOutputStream(byteArrayOutputStream);
    }

    @Test
    public void writeArrayOfBytesByBufferedOutputStream() throws IOException {
        byte[] b = {1, 2, 3, 4, 5};
        byteArrayOutputStream.write(b);
        String actual = Arrays.toString(byteArrayOutputStream.toByteArray());
        assertEquals("[1, 2, 3, 4, 5]", actual);
    }

    @Test
    public void writeBytesByBufferedOutputStreamToOutputStream() throws Exception {
        bufferedOutputStream.write(content);
        bufferedOutputStream.close();
        int i = 0;
        for (byte b : byteArrayOutputStream.toByteArray()) {
            assertEquals(content[i], b);
            i++;
        }
    }

    @Test
    public void writeWithParametersArrayOfBytesToOutputStream() throws Exception {
        bufferedOutputStream.write(content, 2, 2);
        bufferedOutputStream.close();
        assertEquals(118, byteArrayOutputStream.toByteArray()[0]);
        assertEquals(97, byteArrayOutputStream.toByteArray()[1]);
    }

    @Test
    public void whenWriteEmptyArrayOfBytesThanReturnedNullPointerException() {
        assertThrows(NullPointerException.class, () -> {
            bufferedOutputStream.write(null);
        });
    }

    @Test
    public void writeArrayOfBytesWithParameterLengthLessThanZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, 0, -1);
        });
    }

    @Test
    public void writeArrayOfBytesWithParameterOffSetLessThanZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, -1, 6);
        });
    }

    @Test
    public void writeArrayOfBytesWithParameterOffSetEqualsZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            bufferedOutputStream.write(array, 0, 6);
        });
    }
}

