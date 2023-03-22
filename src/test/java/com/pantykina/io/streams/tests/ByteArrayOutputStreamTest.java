package com.pantykina.io.streams.tests;

import com.pantykina.io.BufferedOutputStream;
import com.pantykina.io.ByteArrayInputStream;
import com.pantykina.io.ByteArrayOutputStream;
import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import static org.junit.Assert.*;

public class ByteArrayOutputStreamTest {
    private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

    @Test
    public void writeArrayOfBytesByByteArrayOutputStream() throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOutputStream);
        byte[] bytes = {1, 2, 3, 4, 5};
        outputStream.write(bytes);
        outputStream.close();
        assertEquals("[1, 2, 3, 4, 5]", Arrays.toString(byteArrayOutputStream.toByteArray()));
    }

    @Test
    public void writeArrayOfBytesByByteArrayOutputStreamWithParameters() throws IOException {
        BufferedOutputStream outputStream = new BufferedOutputStream(byteArrayOutputStream);
        byte[] b = {1, 2, 3, 4, 5};
        outputStream.write(b, 0, 3);
        outputStream.close();
        assertEquals("[1, 2, 3]", Arrays.toString(byteArrayOutputStream.toByteArray()));
    }

    @Test
    public void whenWriteArrayWithParameterLengthLessThanOffSetPlusLength() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            byteArrayOutputStream.write(array, 6, 1);
        });
    }

    @Test
    public void whenWriteArrayWithParameterOffSetEqualsZero() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            byte[] array = new byte[5];
            byteArrayOutputStream.write(array, 0, 6);
        });
    }

    @Test
    public void whenWriteEmptyArrayThanNullPointerExceptionReturned() throws Exception {
        assertThrows(NullPointerException.class, () -> {
            byteArrayOutputStream.write(null);
        });
    }

    @Test
    public void whenReadEmptyArrayOfBytes_thenNullPointerException_Returned() {
        assertThrows(NullPointerException.class, () -> {
            byte[] buffer = null;
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("".getBytes());
            byteArrayInputStream.read(buffer);
        });
    }
}