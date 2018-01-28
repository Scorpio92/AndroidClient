package ru.scorpio92.mpgp.util.crypto;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA {

    /**
     * @param alg алгоритм хеширования
     * @return возвращает MessageDigest
     */
    private static MessageDigest getMessageDigest(String alg) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(alg);
    }

    /**
     * Получить массив байтов хеша
     *
     * @param alg алгоритм хеширования
     * @return массив байтов хеша или null
     */
    private static byte[] getByteHash(String alg, String stringToHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return getMessageDigest(alg).digest(stringToHash.getBytes("UTF-8"));
    }

    /**
     * Преобразование массива байтов в строку
     *
     * @param bytes результат digest.digest()
     * @return возвращается строка с хеш суммой
     */
    private static String byteArrayToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    private static String getHash(String alg, String stringToHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return byteArrayToString(getByteHash(alg, stringToHash));
    }

    public static String getSHA1ofString(String stringToHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return getHash("SHA-1", stringToHash);
    }

    public static String getSHA256ofString(String stringToHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return getHash("SHA-256", stringToHash);
    }

    public static String getSHA512ofString(String stringToHash) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return getHash("SHA-512", stringToHash);
    }
}