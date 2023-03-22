package com.pantykina.io;

import java.io.IOException;
import java.io.OutputStream;

public class BufferedOutputStream extends OutputStream {
    private static final int DEFAULT_BUFFER_CAPACITY = 4;
    private final OutputStream outputStream;
    private int index;
    private byte[] buffer;
    private boolean isClosed = false;

    public BufferedOutputStream(OutputStream outputStream) {
        this(outputStream, DEFAULT_BUFFER_CAPACITY);
    }

    public BufferedOutputStream(OutputStream target, int customCapacity) {
        if (customCapacity <= 0) {
            throw new IllegalArgumentException("Incorrect buffer size: " + customCapacity);
        }
        this.outputStream = target;
        buffer = new byte[customCapacity];
    }

    @Override
    public void write(int b) throws IOException {
        insureStreamIsNotClosed();
        buffer[index] = (byte) b;
        index++;
        if (index == DEFAULT_BUFFER_CAPACITY) {
            flush();
        }
    }

    @Override
    public void write(byte[] array, int offset, int length) throws IOException {
        insureStreamIsNotClosed();
        validateParameters(array, offset, length);
        if (buffer.length - index < length) {
            flush();
        }
        if (length > buffer.length) {
            outputStream.write(array, offset, length);
        } else {
            System.arraycopy(array, offset, buffer, index, length);
            index += length;
        }
    }

    @Override
    public void flush() throws IOException {
        outputStream.write(buffer, 0, index);

    }

    @Override
    public void close() throws IOException {
        try (outputStream) {
            flush();
        }
        isClosed = true;
    }

    private void insureStreamIsNotClosed() throws IOException {
        if (isClosed) {
            throw new IOException("The input stream has been closed");
        }
    }

    private void validateParameters(byte[] array, int offset, int length) {
        if (array == null) {
            throw new NullPointerException("array is null");
        } else if (offset < 0 || length < 0 || offset + length > array.length) {
            throw new IndexOutOfBoundsException("offset or length can`t be less than zero. Offset+length can`t be more than " + array.length);
        }
    }
}
