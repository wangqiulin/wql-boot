package com.wql.boot.wqlboot.config.mybatis;

import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MappedTypes(AESEncrypt.class)
public class AESTypeHandler extends BaseTypeHandler<String> {

	private static final Logger logger = LoggerFactory.getLogger(AESTypeHandler.class);
	
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	private static final String KEY = "default-key";
	
	
	/**
     * 用于在Mybatis获取数据结果集时如何把数据库类型转换为对应的Java类型
     *
     * @param rs         当前的结果集
     * @param columnName 当前的字段名称
     * @return 转换后的Java对象
     * @throws SQLException
     */
	@Override
	public String getNullableResult(ResultSet arg0, String arg1) throws SQLException {
		logger.debug("getNullableResult({}, {})", arg0, arg1);
		String rs = arg0.getString(arg1);
		return StringUtils.isBlank(rs) ? null : AESDecrypt(rs, KEY);
	}

	/**
     * 用于在Mybatis通过字段位置获取字段数据时把数据库类型转换为对应的Java类型
     *
     * @param rs          当前的结果集
     * @param columnIndex 当前字段的位置
     * @return 转换后的Java对象
     * @throws SQLException
     */
	@Override
	public String getNullableResult(ResultSet arg0, int arg1) throws SQLException {
		logger.debug("getNullableResult({}, {})", arg0, arg1);
		String rs = arg0.getString(arg1);
		return StringUtils.isBlank(rs) ? null : AESDecrypt(rs, KEY);
	}

	/**
     * 用于Mybatis在调用存储过程后把数据库类型的数据转换为对应的Java类型
     *
     * @param cs          当前的CallableStatement执行后的CallableStatement
     * @param columnIndex 当前输出参数的位置
     * @return
     * @throws SQLException
     */
	@Override
	public String getNullableResult(CallableStatement arg0, int arg1) throws SQLException {
		logger.debug("getNullableResult({}, {})", arg0, arg1);
		String rs = arg0.getString(arg1);
		return StringUtils.isBlank(rs) ? null : AESDecrypt(rs, KEY);
	}

	
	/**
     * 用于定义在Mybatis设置参数时该如何把Java类型的参数转换为对应的数据库类型
     *
     * @param ps        当前的PreparedStatement对象
     * @param i         当前参数的位置
     * @param parameter 当前参数的Java对象
     * @param jdbcType  当前参数的数据库类型
     * @throws SQLException
     */
	@Override
	public void setNonNullParameter(PreparedStatement arg0, int arg1, String arg2, JdbcType arg3) throws SQLException {
		logger.debug("setNonNullParameter({}, {})", arg1, arg2);
		arg0.setString(arg1, AESEncrypt(arg2, KEY));
	}
	
	/**
     * 使用aes加密
     * 功能和mysql的hex(AES_ENCRYPT(content,'key'))一样，使用utf-8编码
     *
     * @param content
     * @param key
     * @return
     */
    public static String AESEncrypt(String content, String key) {
        try {
            final Cipher encryptCipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = generateMySQLAESKey(key, DEFAULT_CHARSET);
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

            byte valBytes[] = encryptCipher.doFinal(content.getBytes(DEFAULT_CHARSET));
            return new String(Hex.encodeHex(valBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用aes解密，
     * 功能和mysql的AES_DECRYPT(unhex(content),'key')一样，使用utf-8编码
     *
     * @param content
     * @param key
     * @return
     */
    public static String AESDecrypt(String content, String key) {
        try {
            final Cipher decryptCipher = Cipher.getInstance("AES");
            SecretKeySpec secretKeySpec = generateMySQLAESKey(key, DEFAULT_CHARSET);
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte valBytes[] = decryptCipher.doFinal(Hex.decodeHex(content.toCharArray()));
            return new String(valBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param key
     * @param encoding
     * @return
     */
    public static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for (byte b : key.getBytes(encoding))
                finalKey[i++ % 16] ^= b;
            return new SecretKeySpec(finalKey, "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

}
