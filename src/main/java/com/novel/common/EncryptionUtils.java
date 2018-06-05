package com.novel.common;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtils {
	/**
	 * base64加密
	 * @param data
	 * @return
	 */
    public static String base64Encode(String data){  
        
        return Base64.encodeBase64String(data.getBytes());  
    }  
	/**
	 * base64解密
	 * @param data
	 * @return
	 */
    public static String base64Decode(String data){  
    	byte[] enArr = Base64.decodeBase64(data.getBytes());     	
        return  enArr.toString();
    }  
      
    public static String md5(String data) {  
          
        return DigestUtils.md5Hex(data);  
    }  
      
    public static String sha1(String data) {  
          
        return DigestUtils.shaHex(data);  
    }  
      
    public static String sha256Hex(String data) {  
          
        return DigestUtils.sha256Hex(data);  
    }  
}
