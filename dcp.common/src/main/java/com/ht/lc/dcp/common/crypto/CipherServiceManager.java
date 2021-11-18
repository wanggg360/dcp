package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.base.ResultCode;
import com.ht.lc.dcp.common.constants.CipherConstant;
import com.ht.lc.dcp.common.exception.ServiceComException;
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

public class CipherServiceManager {

    private static CipherServiceManager INSTANCE = new CipherServiceManager();

    private final Map<String, CipherService> cipherMap;

    private CipherServiceManager() {
        cipherMap = new ConcurrentHashMap<>(2);
        cipherMap.put(CipherConstant.AES_GCM_256, new AesCipherService(CipherAlgorithm.AES_GCM_NOPADDING_256));
    }

    public static CipherServiceManager getInstance() {
        return INSTANCE;
    }

    public String encrypt(String type, String src, String key, String iv) throws ServiceComException {
        if (CollectionUtils.isEmpty(cipherMap)) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "CipherServiceManager init failed, no algorithm instance. ");
        }
        return cipherMap.get(type).encrypt(src, key, iv);
    }

    public String decrypt(String type, String src, String key, String iv) throws ServiceComException {
        if (CollectionUtils.isEmpty(cipherMap)) {
            throw new ServiceComException(ResultCode.SYS_CIPHER_ERROR.getCode(),
                    "CipherServiceManager init failed, no algorithm instance. ");
        }
        return cipherMap.get(type).decrypt(src, key, iv);
    }
}
