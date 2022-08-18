package com.ht.lc.dcp.common.crypto;

import com.ht.lc.dcp.common.exception.ServiceException;

/**
 * @program: dcp
 * @description:
 * @author: wanggang
 * @create: 2021-10-20 11:06
 * @Version 1.0
 **/
public interface CipherService {

    byte[] encrypt(byte[] data, byte[] key, byte[] iv) throws ServiceException;

    byte[] decrypt(byte[] data, byte[] key, byte[] iv) throws ServiceException;

    String encrypt(String src, String hexKey, String hexIV) throws ServiceException;

    String decrypt(String src, String hexKey, String hexIV) throws ServiceException;
}
