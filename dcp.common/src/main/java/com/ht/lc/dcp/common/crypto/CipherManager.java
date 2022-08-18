package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.constants.CipherConst;
import com.ht.lc.dcp.common.exception.ServiceException;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-11-16 15:36
 * @Version 1.0
 **/

public class CipherManager {

    private static CipherManager INSTANCE = new CipherManager();

    private final Map<String, CipherService> cipherMap;

    private CipherManager() {
        cipherMap = new ConcurrentHashMap<>(2);
        cipherMap.put(CipherConst.AES_GCM_256, new AesCipher(CipherAlgorithm.AES_GCM_NOPADDING_256));
    }

    public static CipherManager getInstance() {
        return INSTANCE;
    }

    public String encrypt(String type, String src, String key, String iv) throws ServiceException {
        if (CollectionUtils.isEmpty(cipherMap)) {
            throw new ServiceException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "CipherServiceManager init failed, no algorithm instance. ");
        }
        return cipherMap.get(type).encrypt(src, key, iv);
    }

    public String decrypt(String type, String src, String key, String iv) throws ServiceException {
        if (CollectionUtils.isEmpty(cipherMap)) {
            throw new ServiceException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "CipherServiceManager init failed, no algorithm instance. ");
        }
        return cipherMap.get(type).decrypt(src, key, iv);
    }
}
