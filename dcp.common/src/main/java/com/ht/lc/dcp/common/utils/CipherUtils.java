package com.ht.lc.dcp.common.utils;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.constants.CipherConstant;
import com.ht.lc.dcp.common.exception.ServiceComException;
import org.apache.commons.codec.DecoderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

import org.apache.commons.codec.binary.Hex;
/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-14 17:21
 * @Version 1.0
 **/
public class CipherUtils {

    private CipherUtils() {
    }

    //private static final Logger LOG = LoggerFactory.getLogger(CipherUtils.class);

    public static SecretKey generateKey(int keyBitSize, String algorithmName) throws ServiceComException {
        KeyGenerator kg = null;
        try {
            kg = KeyGenerator.getInstance(algorithmName);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(), "generate secretkey error", e);
        }
        SecureRandom random = new SecureRandom();
        kg.init(keyBitSize, random);
        return kg.generateKey();
    }

    public static SecretKeySpec getSecretKeySpec(byte[] key, String algorithmName) {
        return new SecretKeySpec(key, algorithmName);
    }

    public static byte[] getInitializationVector(int ivBitSize) throws ServiceComException {
        if ((ivBitSize <= 0) || (ivBitSize % 8 != 0)) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "generate iv error error, input bytesize < 0 or not a multiple of 8");
        }
        int byteSize = ivBitSize / 8;
        byte[] ivBytes = new byte[byteSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        return ivBytes;
    }

    public static byte[] base64EncodeToByte(byte[] content) {
        return Base64.getEncoder().encode(content);
    }

    public static String base64EncodeToString(byte[] content) {
        return Base64.getEncoder().encodeToString(content);
    }

    public static String encodeHexString(byte[] input) {
        return Hex.encodeHexString(input);
    }

    public static byte[] base64DecodeToByte(String content) {
        return Base64.getDecoder().decode(content);
    }

    public static byte[] decodeHex(String input) throws ServiceComException {
        byte[] result;
        try{
            result = Hex.decodeHex(input);
        } catch (DecoderException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "decode hex string error, can not decode.", e);
        }
        return result;
    }

    public static String decodeHex2String(String input) throws ServiceComException {
        return new String(decodeHex(input));
    }

    public static Cipher getCipherInstance(String cipherName) throws ServiceComException {
        try {
            return Cipher.getInstance(cipherName);
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "get cipher instance error, no such algorithm. ", e);
        } catch (NoSuchPaddingException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "get cipher instance error, no such padding. ", e);
        }
    }


    public static void main(String[] args) {
        SecretKey key = CipherUtils.generateKey(256, "AES");
        String keyString = encodeHexString(key.getEncoded());
        System.out.println("key byte : " + key.getEncoded());
        System.out.println("key byte string : " + new String (key.getEncoded()));
        System.out.println("key hex string : " + keyString);

        byte[] aa = getInitializationVector(128);
        System.out.println("iv byte : " + aa);
        System.out.println("iv byte string : " + new String(aa));
        String ivString = encodeHexString(aa);
        System.out.println("iv hex string : " + ivString);

        String originalString = "wanggang";

        try {

            Cipher cipher = Cipher.getInstance("AES/GCM/NOPADDING");
            AlgorithmParameterSpec spec = new GCMParameterSpec(128, aa);
            cipher.init(Cipher.ENCRYPT_MODE, key, spec);
            byte[] endata = cipher.doFinal(originalString.getBytes(StandardCharsets.UTF_8));
            System.out.println("encrypt data(direct string) : " + new String(endata));
            String encryptdata = base64EncodeToString(endata);
            System.out.println("encrypt data(base64) : " + base64EncodeToString(endata));
            System.out.println("encryptdata base64 decode to byte" + base64DecodeToByte(encryptdata));

            cipher.init(Cipher.DECRYPT_MODE, key, spec);
            byte[] dedata = cipher.doFinal(base64DecodeToByte(encryptdata));
            System.out.println("decrypt data : " + new String(dedata));

        } catch (Exception e) {

        }

    }
}
