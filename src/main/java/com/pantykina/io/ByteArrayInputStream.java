package com.pantykina.io;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayInputStream extends InputStream {
    private int count;
    private byte[] buffer;
    private boolean isClosed = false;

    public ByteArrayInputStream(byte[] buffer) {
        this.buffer = buffer;
    }

    @Override
    public int read() {
        if (count == buffer.length) {
            return -1;
        }
        return buffer[count++];
    }

    @Override
    public int read(byte[] array) throws IOException {
        insureStreamIsNotClosed();
        return read(array, 0, array.length);
    }

    @Override
    public int read(byte[] array, int off, int length) throws IOException {
        insureStreamIsNotClosed();
        validateParameters(array, off, length);
        if (count == buffer.length) {
            return -1;
        }
        while (read() != -1) {
            System.arraycopy(buffer, 0, array, off, length);
        }
        return count;
    }

    @Override
    public void close() throws IOException {
        isClosed = true;
        super.close();
    }

    private void insureStreamIsNotClosed() throws IOException {
        if (isClosed) {
            throw new IOException("The input stream is not closed");
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