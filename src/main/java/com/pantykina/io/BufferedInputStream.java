package com.pantykina.io;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {
    private static final int DEFAULT_BUFFER_CAPACITY = 4096;
    private final InputStream inputStream;
    private byte[] buffer;
    private int position;
    private int count;
    private boolean isClosed = false;

    public BufferedInputStream(InputStream inputStream) {
        this(inputStream, DEFAULT_BUFFER_CAPACITY);
    }

    public BufferedInputStream(InputStream inputStream, int customCapacity) {
        if (customCapacity <= 0) {
            throw new IllegalArgumentException("Incorrect buffer size: " + customCapacity + ", should be more than 0");
        }
        this.inputStream = inputStream;
        this.buffer = new byte[customCapacity];
    }

    @Override
    public int read() throws IOException {
        insureStreamIsNotClosed();
        fillBuffer();
        if (count == -1) {
            return -1;
        }
        return buffer[position++];
    }

    @Override
    public int read(byte[] array) throws IOException {
        insureStreamIsNotClosed();
        if (array == null) {
            throw new NullPointerException("array of bytes is null");
        }
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int offset, int length) throws IOException {
        int copiedBytes;
        insureStreamIsNotClosed();
        validateParameters(array, offset, length);
        if (fillBuffer() == -1) {
            return -1;
        }
        if (length == 0) {
            return 0;
        }
        if (length > buffer.length) {
            System.arraycopy(new byte[array.length], 0, array, 0, length);
            return inputStream.read(array, offset, length);
        }
        if (length <= count - position) {
            System.arraycopy(buffer, position, array, offset, length);
            position += length;
            copiedBytes = length;
        } else {
            System.arraycopy(buffer, position, array, offset, count - position);
            position += count - position;
            copiedBytes = count - position;
        }
        int extraBytes = length - copiedBytes;
        if (fillBuffer() == -1) {
            System.arraycopy(new byte[array.length], offset + copiedBytes, array, offset + copiedBytes, extraBytes);
        } else {
            if (count < extraBytes) {
                System.arraycopy(buffer, position, array, offset + copiedBytes, count);
                copiedBytes += count;
                position += count;
                System.arraycopy(new byte[array.length], offset + copiedBytes, array, offset + copiedBytes, offset + length);
            } else {
                System.arraycopy(buffer, position, array, offset + copiedBytes, extraBytes);
                copiedBytes += extraBytes;
                position += extraBytes;
            }
        }

        return copiedBytes;
    }

    @Override
    public void close() throws IOException {
        position = 0;
        count = 0;
        buffer = null;
        inputStream.close();
        isClosed = true;
    }

    private int fillBuffer() throws IOException {
        if (position == count) {
            count = inputStream.read(buffer);
            position = 0;
        }
        return count;
    }

    private void insureStreamIsNotClosed() throws IOException {
        if (isClosed) {
            throw new IOException("The input stream has been closed");
        }
    }

    private void validateParameters(byte[] array, int off, int length) {
        if (array == null) {
            throw new NullPointerException("array of bytes is null");
        } else if (off < 0 || length < 0 || length > array.length - off) {
            throw new IndexOutOfBoundsException("Position or length can`t be less than zero. Length can`t be more than " + (array.length - off));
        }
    }
}
