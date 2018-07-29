package com.wql.boot.wqlboot.model.res.user;

import org.apache.poi.hssf.util.HSSFColor;

import com.xuxueli.poi.excel.annotation.ExcelField;
import com.xuxueli.poi.excel.annotation.ExcelSheet;

/**
 *
 * @author wangqiulin
 * @date 2018年7月29日
 */
@ExcelSheet(name = "用户列表", headColor = HSSFColor.HSSFColorPredefined.LIGHT_GREEN)
public class UserExcelResult {
    
    @ExcelField(name = "用户名")
    private String userName;
    
    @ExcelField(name = "密码")
    private String userPwd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    
}
