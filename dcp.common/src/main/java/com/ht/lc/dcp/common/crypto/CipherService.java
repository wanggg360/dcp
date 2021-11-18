package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.exception.ServiceComException;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-10-20 11:06
 * @Version 1.0
 **/
public interface CipherService {

    byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws ServiceComException;

    byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws ServiceComException;

    String encrypt(String src, String hexKey, String hexIV) throws ServiceComException;

    String decrypt(String src, String hexKey, String hexIV) throws ServiceComException;
}
