package com.pantykina.io;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

public class ByteArrayOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER_CAPACITY = 1028;
    private final int GROW_FACTOR = 2;
    private boolean isClosed = false;

    private byte[] buffer;
    private int size;
    private int index = 0;

    public ByteArrayOutputStream() {
        this(DEFAULT_BUFFER_CAPACITY);
    }

    public ByteArrayOutputStream(int customCapacity) {
        this.size = customCapacity;
        buffer = new byte[customCapacity];
    }

    @Override
    public void write(int b) throws IOException {
        if (isClosed) {
            throw new IOException("The output stream is closed");
        }
        buffer[index] = (byte) b;
        index++;
        if (index == DEFAULT_BUFFER_CAPACITY) {
            flush();
        }
    }

    @Override
    public void write(byte[] array) throws IOException {
        write(array, 0, array.length);
    }

    @Override
    public void write(byte[] array, int off, int length) throws IOException {
        if (isClosed) {
            throw new IOException("The output stream is closed");
        }
        validateParameters(array, off, length);

        if (length > size - index) {
            byte[] newArray = new byte[(size * GROW_FACTOR)];
            System.arraycopy(buffer, 0, newArray, 0, size);
            buffer = newArray;
        }
        System.arraycopy(array, off, buffer, index, length);
        index += length;
    }

    @Override
    public void close() throws IOException {
        super.close();
        isClosed = true;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, index);
    }

    private void validateParameters(byte[] array, int off, int length) {
        if (array == null) {
            throw new NullPointerException("array of bytes is null");
        } else if (off < 0 || length < 0 || length + off > array.length) {
            throw new IndexOutOfBoundsException("Position or length can`t be less than zero. Length+off can`t be more than " + array.length);
        }
    }
}