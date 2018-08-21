package com.wql.boot.wqlboot.common.dbEncrypt;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;

/**
 * MySqlAesUtil的实现,默认数据库加密实现
 * 
 * @author wangqiulin
 *
 */
@Component
@ConditionalOnExpression("'${database.encrypt.type:MySqlAes}'.equalsIgnoreCase('MySqlAes')")
public class MySqlAesEncryptServiceImpl implements DatabaseEncryptService {

    private static final Logger logger = LoggerFactory.getLogger(MySqlAesEncryptServiceImpl.class);

    @Value("${database.encrypt.key:}")
    private String databaseEncryptKey;

    private Cipher encCipher;

    private Cipher decCipher;

    @PostConstruct
    public void initCipher(){
        logger.info("开始初始化MySqlAesUtil加密解密器");
        if(StringUtils.isEmpty(databaseEncryptKey)){
            logger.warn("数据库加密使用默认key请通过database.encrypt.key设置");
            databaseEncryptKey = "defaultKey";
        }
        if(databaseEncryptKey.length() > 16){
            logger.warn("mysqlAes密钥设置过长,系统默认截取前16位");
            databaseEncryptKey = databaseEncryptKey.substring(0,16);
        }
        encCipher = MySqlAesUtil.getEncryptCipher(databaseEncryptKey);
        decCipher = MySqlAesUtil.getDecryptCipher(databaseEncryptKey);
        logger.info("初始化MySqlAesUtil加密解密器完成");
    }

    @Override
    public String encrypt(String content) {
        return MySqlAesUtil.encrypt(content,encCipher);
    }

    @Override
    public String decrypt(String content) {
        return MySqlAesUtil.decrypt(content,decCipher);
    }

    @Override
    public String getEncryptKey() {
        return this.databaseEncryptKey;
    }


}

