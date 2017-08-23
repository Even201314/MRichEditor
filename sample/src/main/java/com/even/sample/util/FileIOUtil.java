package com.even.sample.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * <pre>
 *     author : Even
 *     time   : 2017/07/17
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class FileIOUtil {

    private static int sBufferSize = 8192;

    /**
     * 读取文件到字节数组中
     *
     * @param filePath 文件路径
     * @return 字符数组
     */
    public static byte[] readFile2BytesByStream(final String filePath) {
        return readFile2BytesByStream(getFileByPath(filePath));
    }

    public static byte[] readFile2BytesByStream(final File file) {
        if (!isFileExists(file)) {
            return null;
        }
        FileInputStream fis = null;
        ByteArrayOutputStream os = null;
        try {
            fis = new FileInputStream(file);
            os = new ByteArrayOutputStream();
            byte[] b = new byte[sBufferSize];
            int len;
            while ((len = fis.read(b, 0, sBufferSize)) != -1) {
                os.write(b, 0, len);
            }
            return os.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeIO(fis, os);
        }
    }

    public static void closeIO(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static File getFileByPath(final String filePath) {
        return isSpace(filePath) ? null : new File(filePath);
    }

    private static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static boolean isFileExists(final File file) {
        return file != null && file.exists();
    }
}
