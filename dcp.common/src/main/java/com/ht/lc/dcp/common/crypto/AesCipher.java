package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.exception.ServiceException;
import com.ht.lc.dcp.common.utils.CipherUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-09-13 20:01
 * @Version 1.0
 **/
public class AesCipher extends AbstractCipher {

    AesCipher(CipherAlgorithm cipherAlgorithm) {
        super(cipherAlgorithm);
    }

    public static void main(String[] args) {
        AesCipher aes = new AesCipher(CipherAlgorithm.AES_GCM_NOPADDING_256);
        String aa = aes.encrypt("root", "f4fd5f31498ac8db4f9262e061200cd2a9a52ad4fd1f37ffe8c59675d3b0207e",
                "37f82343accd6ee494f792601c46e686");
        String org = aes.decrypt(aa, "f4fd5f31498ac8db4f9262e061200cd2a9a52ad4fd1f37ffe8c59675d3b0207e",
                "37f82343accd6ee494f792601c46e686");
        System.out.println("===== encrypt : " + aa);
        System.out.println("===== decrypt : " + org);
    }

    public String encrypt(String src, String hexKey, String hexIV) throws ServiceException {
        if (!StringUtils.hasText(hexKey) || !StringUtils.hasText(hexIV) || !StringUtils.hasText(src)) {
            throw new ServiceException("aes encrypt error, srcdata or key or iv string is blank. ");
        }
        byte[] encryptData =
                encrypt(src.getBytes(StandardCharsets.UTF_8), CipherUtils.decodeHex(hexKey), CipherUtils.decodeHex(hexIV));
        return CipherUtils.encodeHexString(encryptData);

    }

    public String decrypt(String src, String hexKey, String hexIV) throws ServiceException {
        if (!StringUtils.hasText(hexKey) || !StringUtils.hasText(hexIV) || !StringUtils.hasText(src)) {
            throw new ServiceException("aes decrypt error, srcdata or key or iv string is blank. ");
        }
        return new String(
                decrypt(CipherUtils.decodeHex(src), CipherUtils.decodeHex(hexKey), CipherUtils.decodeHex(hexIV)));
    }

}
