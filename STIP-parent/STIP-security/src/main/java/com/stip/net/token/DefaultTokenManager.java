package com.stip.net.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.stip.net.utils.GrnerateUUID;
import com.stip.net.utils.StringUtil;

  
/**        
 * Title: TokenManager的默认实现    
 * Description: 管理 Token
 * @author chenjunan       
 * @created 2017年7月4日 下午4:41:32    
 */      
public class DefaultTokenManager implements TokenManager {

	/** 将token存储到JVM内存(ConcurrentHashMap)中  */      
	private static Map<String, String> tokenMap = new ConcurrentHashMap<String, String>();

	/** 
	 * @description 利用UUID创建Token(用户登录时，创建Token)
	 * @created 2017年7月4日 下午4:46:46      
	 * @param username
	 * @return     
	 */  
	public String createToken(String username) {
		String token = GrnerateUUID.getUUID();
		tokenMap.put(token, username);
		return token;
	}

	  
	/** 
	 * @description Token验证(用户登录验证)
	 * @created 2017年7月4日 下午4:46:50      
	 * @param token
	 * @return     
	 */  
	public boolean checkToken(String token) {
		return !StringUtil.isEmpty(token) && tokenMap.containsKey(token);
	}

	  
	/** 
	 * @description Token删除(用户登出时，删除Token)
	 * @created 2017年7月4日 下午4:46:54      
	 * @param token     
	 */  
	@Override
	public void deleteToken(String token) {
		// TODO Auto-generated method stub
		tokenMap.remove(token);
	}
}
