package com.dfans.utils.GhtCommon;



import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by admin on 2016/12/27.
 */

public abstract class Digest {
    //SHA-1，SHA-224，SHA-256，SHA-384，和SHA-512
    public static final int NONE = 0;
    public static final int MD5 = 1;
    public static final int SHA1 = 2;
    public static final int SHA224 = 3;
    public static final int SHA256 = 4;
    public static final int SHA384 = 5;
    public static final int SHA512 = 6;
    public static final String charset = "utf-8";

    static MessageDigest[] messageDigests = new MessageDigest[]{null,null,null,null,null,null,null};
    static String[] DigestTypes = new String[]{"NONE","MD5","SHA-1","SHA-224","SHA-256","SHA-384","SHA-512"};

    private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    static MessageDigest getDigestInstance(int digestType) {
        if(digestType <= 0 || digestType > 6) return null;

        MessageDigest messageDigest = messageDigests[digestType];
        if(messageDigest == null) {
            try {
                messageDigest = MessageDigest.getInstance(DigestTypes[digestType]);
                messageDigests[digestType] = messageDigest;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return messageDigest;
    }

    public static byte[] encodeByString(String data, int digestType) throws Exception {
        checkSignParam(data, digestType);
        try {
            return encodeByBytes(data.getBytes(charset), digestType);
        } catch (Exception e) {
        }
        return "".getBytes();
    }

    public static synchronized byte[] encodeByBytes(byte[] data, int digestType) throws Exception {
        MessageDigest messageDigest = checkSignParam(data, digestType);
        try {
            messageDigest.reset();
            messageDigest.update(data);
            return messageDigest.digest();
        } catch (Exception e) {
        }
        return "".getBytes();
    }

    public static byte[] encodeByInputStream(InputStream is, int digestType) throws Exception,
            IOException {
        MessageDigest messageDigest = checkSignParam(is, digestType);
        try {
            byte[] buf = new byte[1024];
            messageDigest.reset();
            int size;
            while ((size = is.read(buf)) != -1) {
                messageDigest.update(buf, 0, size);
            }
            return messageDigest.digest();
        } catch (IOException e) {
            throw new IOException("MPCM013");
        }
    }

    /**
     * @param data
     * @param digestType
     * @return 加密后的16进制字符串
     * @throws Exception
     */
    public static String encode(Object data, int digestType) throws Exception {
        checkSignParam(data, digestType);
        byte[] nEncodeData = null;
        if (data instanceof String){
            nEncodeData = encodeByString((String)data, digestType);
        }else if(data instanceof byte[]){
            nEncodeData = encodeByBytes((byte[]) data, digestType);
        }else if(data instanceof InputStream){
            nEncodeData = encodeByInputStream((InputStream) data, digestType);
        }else{
            throw new Exception("未识别的数据类型");
        }

        return byteArrayToHexString(nEncodeData);
    }

    static MessageDigest checkSignParam(Object data, int digestType) throws Exception {
        if (data == null) {
            throw new Exception("待签名数据为空");
        }

        MessageDigest messageDigest = getDigestInstance(digestType);
        if(messageDigest == null){
            throw new Exception("未知的签名类型");
        }

        return messageDigest;
    }

    /**
     * 检查签名值是否相等
     *
     * @param sign
     *            签名字符串
     * @param str
     *            待检测字符
     * @return
     */
    public final static boolean checkSign(String sign, String str, int digestType) {
        try {
            return sign.equals(encode(str, digestType));
        } catch (Exception e) {
            return  false;
        }
    }

    /**
     * 转换字节数组为16进制字串
     *
     * @param b
     *            字节数组
     *
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     *
     * @param b
     * @return
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
}
