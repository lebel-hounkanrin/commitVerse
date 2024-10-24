package com.dev.utils;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Crypto {
    public static String computeFileSHA1(String filePath) throws IOException {
        try(
                InputStream fis = new FileInputStream(filePath);
                DigestInputStream digestInputStream = new DigestInputStream(fis, MessageDigest.getInstance("SHA-1"))
        ) {
            byte[] buffer = new byte[1024];
            while (digestInputStream.read(buffer) > 0);
            byte[] resultByteArry = digestInputStream.getMessageDigest().digest();
            return bytesToHex(resultByteArry);
        }
        catch (Exception e) {
            throw new IOException("Failed to compute SHA-1 hash", e);
        }
    }

    public static String computeStringSHA1(String args) throws NoSuchAlgorithmException, IOException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        try (DigestInputStream digestInputStream = new DigestInputStream(new ByteArrayInputStream(args.getBytes()), sha1)) {
            byte[] buffer = new byte[1024];
            while (digestInputStream.read(buffer) != -1) {}
        }
        return bytesToHex(sha1.digest());
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xff & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(0xff & bytes[i]));
        }
        return sb.toString();
    }
}
