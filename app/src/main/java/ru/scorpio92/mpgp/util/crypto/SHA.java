package ru.scorpio92.mpgp.util.crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.MessageDigest;

/**
 * Класс для удобной работы с алгоритами хеширования SHA-1, SHA-256, SHA-512
 */
public class SHA {

    /**
     * Строка для которой надо вычислить хеш сумму
     */
    private String stringToHash;
    /**
     * Файл для которого нужно вычислить хеш сумму
     */
    private File file;
    /**
     * Соль
     */
    private String salt;
    /**
     * Если хешируем с солью, параметр withSalt принимает значение true
     */
    private Boolean withSalt;
    /**
     * Дефолтная соль
     */
    public static final String DEFAULT_SALT = "44532627fd9a05bd04c4a09cf649174202fb825d6809040d3eaa7617589ddf3c47191a8ad73d21e1e5b7daa4534c839283f4a0d231c275fc158d7b5b21e598f2";
    /**
     * Если вычисляем хеш файла, этот параметр автоматически принимает значение true
     */
    private boolean isFile;
    /**
     * Размер буффера в байтах для чтения файла
     */
    private final int DEFAULT_FILE_BUFFER = 8092;
    /**
     * Алгоритм SHA-1
     */
    public static final String ALGORITHM_SHA1 = "SHA-1";
    /**
     * Алгоритм SHA-256
     */
    public static final String ALGORITHM_SHA256 = "SHA-256";
    /**
     * Алгоритм SHA-512
     */
    public static final String ALGORITHM_SHA512 = "SHA-512";
    /**
     * Указываем что нужно вычислить хеш сумму строки по алгоритму SHA-1
     */
    public static final int GET_SHA1_OF_STRING = 0;
    /**
     * Указываем что нужно вычислить хеш сумму строки по алгоритму SHA-256
     */
    public static final int GET_SHA256_OF_STRING = 1;
    /**
     * Указываем что нужно вычислить хеш сумму строки по алгоритму SHA-512
     */
    public static final int GET_SHA512_OF_STRING = 2;
    /**
     * Указываем что нужно вычислить хеш сумму файла по алгоритму SHA-1
     */
    public static final int GET_SHA1_OF_FILE = 0;
    /**
     * Указываем что нужно вычислить хеш сумму файла по алгоритму SHA-512
     */
    public static final int GET_SHA512_OF_FILE = 1;

    /**
     * Конструктор
     * @param stringToHash строка по которой нужно вычислить хеш сумму
     */
    public SHA(String stringToHash) {
        this.stringToHash = stringToHash;
        withSalt = false;
        isFile = false;
    }

    /**
     * Конструктор
     * @param stringToHash строка по которой нужно вычислить хеш сумму
     * @param salt соль
     */
    public SHA(String stringToHash, String salt) {
        this.stringToHash = stringToHash;
        this.salt = salt;
        withSalt = true;
        isFile = false;
    }

    /**
     * Конструктор
     * @param file файл по которому нужно вычислить хеш сумму
     */
    public SHA(File file) {
        this.file = file;
        withSalt = false;
        isFile = true;
    }

    /**
     * Конструктор
     * @param file файл по которому нужно вычислить хеш сумму
     * @param salt соль
     */
    public SHA(File file, String salt) {
        this.file = file;
        this.salt = salt;
        withSalt = true;
        isFile = true;
    }

    /**
     * Получение хеш суммы
     * @param operation параметр определяет сумму чего и по какому алгоритму нужно вычислить
     * @return хеш строка или null (если необходимые параметры null или передана несуществующая операция)
     */
    public String getHash(int operation) {
        if(isFile && file == null)
            return null;
        if(!isFile && stringToHash == null)
            return null;
        if(withSalt && salt == null)
            return null;
        if(isFile) {
            switch (operation) {
                case GET_SHA1_OF_FILE:
                    return getFileHash("SHA-1");
                case GET_SHA512_OF_FILE:
                    return getFileHash("SHA-512");
            }
        } else {
            switch (operation) {
                case GET_SHA1_OF_STRING:
                    return byteArrayToString(getByteHash("SHA-1"));
                case GET_SHA256_OF_STRING:
                    return byteArrayToString(getByteHash("SHA-256"));
                case GET_SHA512_OF_STRING:
                    return byteArrayToString(getByteHash("SHA-512"));
            }
        }
        return null;
    }

    /**
     *
     * @param alg алгоритм хеширования
     * @return возвращает MessageDigest
     */
    private MessageDigest getMessageDigest(String alg) {
        try {
            MessageDigest md = MessageDigest.getInstance(alg);
            if (withSalt)
                md.update(salt.getBytes("UTF-8"));
            return md;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Получить массив байтов хеша
     * @param alg алгоритм хеширования
     * @return массив байтов хеша или null
     */
    public byte[] getByteHash(String alg) {
        try {
            return getMessageDigest(alg).digest(stringToHash.getBytes("UTF-8"));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Получить хеш файла
     * @param alg алгоритм хеширования
     * @return хеш строка или null
     */
    private String getFileHash(String alg) {
        try {
            MessageDigest md = getMessageDigest(alg);
            InputStream fis = new FileInputStream(file);
            int n = 0;
            byte[] buffer = new byte[DEFAULT_FILE_BUFFER];
            while (n != -1) {
                n = fis.read(buffer);
                if (n > 0) {
                    md.update(buffer, 0, n);
                }
            }
            return byteArrayToString(md.digest());
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Преобразование массива байтов в строку
     * @param bytes результат digest.digest()
     * @return возвращается строка с хеш суммой
     */
    private String byteArrayToString(byte[] bytes) {
        try {
            StringBuilder sb = new StringBuilder();
            for (byte aByte : bytes) {
                sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}