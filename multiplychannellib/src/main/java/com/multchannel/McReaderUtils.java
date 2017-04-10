package com.multchannel;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import static sun.security.x509.X509CertImpl.SIG;

/**
 * Created by anshu.wang on 2017/4/10.
 */

public class McReaderUtils {
    /**
     * 数据结构体的签名标记
     */
    private static final String SIG = "MCPT";
    /**
     * 数据结构的版本号
     */
    private static final String VERSION_1_1 = "1.1";
    /**
     * 数据编码格式
     */
    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 加密用的IvParameterSpec参数
     */
    private static final byte[] IV = new byte[]{1, 3, 1, 4, 5, 2, 0, 1};

    /**
     * Android平台读取渠道号
     *
     * @param context         Android中的android.content.Context对象
     * @param mcptoolPassword mcptool解密密钥
     * @param defValue        读取不到时用该值作为默认值
     * @return
     */
    public static String getChannelId(Object context, String mcptoolPassword, String defValue) {
        String content = readContent(new File(getPackageCodePath(context)), mcptoolPassword);
        return content == null || content.length() == 0 ? defValue : content;
    }

    /**
     * 读取数据（如：渠道号）
     *
     * @param path     文件路径
     * @param password 解密密钥
     * @return 被该工具写入的数据（如：渠道号）
     */
    public static String readContent(File path, String password) {
        try {
            return new String(read(path, password), CHARSET_NAME);
        } catch (Exception ignore) {
        }
        return null;
    }

    /**
     * 获取已安装apk文件的存储路径（这里使用反射，因为MCPTool项目本身不需要导入Android的运行库）
     *
     * @param context Android中的Context对象
     * @return
     */
    private static String getPackageCodePath(Object context) {
        try {
            return (String) context.getClass().getMethod("getPackageCodePath").invoke(context);
        } catch (Exception ignore) {
        }
        return null;
    }

    /**
     * 读取数据
     *
     * @param path     文件路径
     * @param password 解密密钥
     * @return 被该工具写入的数据（如：渠道号）
     * @throws Exception
     */
    private static byte[] read(File path, String password) throws Exception {
        byte[] bytesContent = null;
        byte[] bytesMagic = SIG.getBytes(CHARSET_NAME);
        byte[] bytes = new byte[bytesMagic.length];
        RandomAccessFile raf = new RandomAccessFile(path, "r");
        Object[] versions = getVersion(raf);
        long index = (long) versions[0];
        String version = (String) versions[1];
        if (VERSION_1_1.equals(version)) {
            bytes = new byte[1];
            index -= bytes.length;
            readFully(raf, index, bytes); // 读取内容长度；
            boolean isEncrypt = bytes[0] == 1;

            bytes = new byte[2];
            index -= bytes.length;
            readFully(raf, index, bytes); // 读取内容长度；
            int lengthContent = stream2Short(bytes, 0);

            bytesContent = new byte[lengthContent];
            index -= lengthContent;
            readFully(raf, index, bytesContent); // 读取内容；

            if (isEncrypt && password != null && password.length() > 0) {
                bytesContent = decrypt(password, bytesContent);
            }
        }
        raf.close();
        return bytesContent;
    }

    /**
     * 读取数据结构的版本号
     *
     * @param raf RandomAccessFile
     * @return 数组对象，[0] randomAccessFile.seek的index，[1] 数据结构的版本号
     * @throws IOException
     */
    private static Object[] getVersion(RandomAccessFile raf) throws IOException {
        String version = null;
        byte[] bytesMagic = SIG.getBytes(CHARSET_NAME);
        byte[] bytes = new byte[bytesMagic.length];
        long index = raf.length();
        index -= bytesMagic.length;
        readFully(raf, index, bytes); // 读取SIG标记；
        if (Arrays.equals(bytes, bytesMagic)) {
            bytes = new byte[2];
            index -= bytes.length;
            readFully(raf, index, bytes); // 读取版本号长度；
            int lengthVersion = stream2Short(bytes, 0);
            index -= lengthVersion;
            byte[] bytesVersion = new byte[lengthVersion];
            readFully(raf, index, bytesVersion); // 读取内容；
            version = new String(bytesVersion, CHARSET_NAME);
        }
        return new Object[]{index, version};
    }

    /**
     * RandomAccessFile seek and readFully
     *
     * @param raf
     * @param index
     * @param buffer
     * @throws IOException
     */
    private static void readFully(RandomAccessFile raf, long index, byte[] buffer) throws IOException {
        raf.seek(index);
        raf.readFully(buffer);
    }

    /**
     * short转换成字节数组（小端序）
     *
     * @return
     */
    private static short stream2Short(byte[] stream, int offset) {
        ByteBuffer buffer = ByteBuffer.allocate(2);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.put(stream[offset]);
        buffer.put(stream[offset + 1]);
        return buffer.getShort(0);
    }

    /**
     * 解密
     *
     * @param password
     * @param content
     * @return
     * @throws Exception
     */
    private static byte[] decrypt(String password, byte[] content) throws Exception {
        return cipher(Cipher.DECRYPT_MODE, password, content);
    }

    /**
     * 加解密
     *
     * @param cipherMode
     * @param password
     * @param content
     * @return
     * @throws Exception
     */
    private static byte[] cipher(int cipherMode, String password, byte[] content) throws Exception {
        DESKeySpec dks = new DESKeySpec(password.getBytes(CHARSET_NAME));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        Key secretKey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        IvParameterSpec spec = new IvParameterSpec(IV);
        cipher.init(cipherMode, secretKey, spec);
        return cipher.doFinal(content);
    }
}
