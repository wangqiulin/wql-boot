package com.wql.boot.wqlboot.common.util.emoji;

import com.xxl.emoji.EmojiTool;
import com.xxl.emoji.encode.EmojiEncode;

/**
 * Emoji表情编解码库 
 *
 * @author wangqiulin
 * @date 2018年7月29日
 */
public class EmojiUtil {

    /**
     * emoji表情编码
     * @param input
     * @param encode
     * @return
     */
    public static String encode(String input, EmojiEncode encode) {
        return EmojiTool.encodeUnicode(input, encode);
    }
    
    /**
     * emoji表情解码
     * @param encode
     * @return
     */
    public static String decode(String encode) {
        return EmojiTool.decodeToUnicode(encode);
    }
    
}
