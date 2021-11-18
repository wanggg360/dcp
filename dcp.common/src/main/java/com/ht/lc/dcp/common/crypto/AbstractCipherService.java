package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.constants.CipherConstant;
import com.ht.lc.dcp.common.exception.ServiceComException;
import com.ht.lc.dcp.common.utils.CipherUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-13 19:34
 * @Version 1.0
 **/
public abstract class AbstractCipherService implements CipherService {

    private CipherAlgorithm cipherAlgorithm;

    protected AbstractCipherService(CipherAlgorithm cipherAlgorithm) {
        this.cipherAlgorithm = cipherAlgorithm;
    }

    public byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws ServiceComException {
        Cipher cipher = initCipher(Cipher.ENCRYPT_MODE, key, iv);
        try {
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "encrypt error, illegal block size. ", e);
        } catch (BadPaddingException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "encrypt error, bad padding. ", e);
        }
    }

    public byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws ServiceComException {
        Cipher cipher = initCipher(Cipher.DECRYPT_MODE, key, iv);
        try {
            return cipher.doFinal(data);
        } catch (IllegalBlockSizeException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "encrypt error, illegal block size. ", e);
        } catch (BadPaddingException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "encrypt error, bad padding. ", e);
        }
    }

    private Cipher initCipher(int encryptMode, byte[] key, byte[] iv) throws ServiceComException {
        Cipher cipher = CipherUtils.getCipherInstance(cipherAlgorithm.getTransformationString());
        Key keySpec = new SecretKeySpec(key, cipherAlgorithm.getAlgName());
        try {
            if (iv == null || iv.length == 0) {
                cipher.init(encryptMode, keySpec);
            } else {
                AlgorithmParameterSpec algSpec;
                switch (cipherAlgorithm.getAlgMode()) {
                    case CipherConstant.ALG_MODE_GCM:
                        algSpec = new GCMParameterSpec(cipherAlgorithm.getIvSize(), iv);
                        break;
                    default:
                        algSpec = new IvParameterSpec(iv);
                }
                cipher.init(encryptMode, keySpec, algSpec);
            }
        } catch (InvalidKeyException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "init cipher error, invalid key. ", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "init cipher error, invalid algorithm parameter. ", e);
        }
        return cipher;
    }
}
