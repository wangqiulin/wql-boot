package com.wql.boot.wqlboot.common.util.pwd;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * 
 * @author wangqiulin
 * @date 2018年5月12日
 */
public class PwdEncoderUtil {

	/**秘玥值*/ 
    private static final String SITE_WIDE_SECRET = "secret-key";  
   
    private static final PasswordEncoder ENCODER = new StandardPasswordEncoder(SITE_WIDE_SECRET);  
  
    /**
     * 密码加密（每次都不一样），加密后是80位的字符
     * @param rawPwd
     * @return
     */
    public static String encrypt(String rawPwd) {  
        return ENCODER.encode(rawPwd);  
    }  
   
    /**
     * 判断密码是否正确
     * @param rawPwd： 明文密码
     * @param encodePwd： 加密后的密文
     * @return
     */
    public static boolean match(String rawPwd, String encodePwd) {  
        return ENCODER.matches(rawPwd, encodePwd);  
    }  
	
   
}
