package com.wql.boot.wqlboot.common.support.encrypt;

/**
 * 
 * @author wangqiulin
 *
 */
public interface DatabaseEncryptService {

    /**
     * 数据库字段加密
     *
     * @param content
     * @return
     */
    String encrypt(String content);

    /**
     * 数据库字段解密
     *
     * @param content
     * @return
     */
    String decrypt(String content);

    /**
     * 获取密钥key
     * @return
     */
    String getEncryptKey();
}
