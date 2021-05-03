package cn.repeatlink.framework.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import cn.repeatlink.framework.bean.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtils {
	public static final String SPLIT_STR = "&&";
	public static final String TOKEN_HEADER = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer ";

	public static final String SUBJECT = "rl_token";

	public static final long EXPIRITION = 1000 * 24 * 60 * 60 * 7;

	public static final String APPSECRET_KEY = "rl_secret";

	public static String generateJsonWebToken(LoginUser user) {

		if (user == null || StringUtils.isEmpty(user.getUsername())) {
			return null;
		}

		Map<String, Object> map = new HashMap<>();
		String key = user.getUserType() + SPLIT_STR + user.getUsername() + SPLIT_STR + System.currentTimeMillis();
		JwtStore.put(key, user);
		String token = Jwts.builder().setSubject(SUBJECT).setClaims(map).claim("key", key)
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRITION))
				.signWith(SignatureAlgorithm.HS256, APPSECRET_KEY).compact();
		return TOKEN_PREFIX + token;
	}
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	public static LoginUser getLoginUser(HttpServletRequest request) {
		String   token=getToken(request);
		if(!StringUtils.isEmpty(token)) {
			Claims claim=parseToken(token);
			String key=(String) claim.get("key");
			LoginUser user=JwtStore.get(key);
			return user;
		}
		return null;
	}
	/**
	 * 获取用户信息
	 * @param request
	 * @return
	 */
	public static void deleteToken(HttpServletRequest request) {
		String   token=getToken(request);
		if(!StringUtils.isEmpty(token)) {
			Claims claim=parseToken(token);
			String key=(String) claim.get("key");
			JwtStore.delete(key);
			
		}
	}

    /**
     * 从令牌中获取数据声明
     *
     * @param token 令牌
     * @return 数据声明
     */
    private static  Claims parseToken(String token)
    {
        return Jwts.parser()
                .setSigningKey(APPSECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
	private static String getToken(HttpServletRequest request) {
		String token = request.getHeader(TOKEN_HEADER);
		if (token!=null && token.startsWith(TOKEN_PREFIX)) {
			token = token.replace(TOKEN_PREFIX, "");
		}
		return token;
	}

	public static void main(String args[]) {
		LoginUser user=new LoginUser("tangliqiang","111");
		String token=generateJsonWebToken(user);
		System.out.println("token:"+token);
		token = token.replace(TOKEN_PREFIX, "");
		Claims claim=parseToken(token);
		String key=(String) claim.get("key");
		LoginUser result=JwtStore.get(key);
		System.out.println(result.getUsername());
		
		
	}
}
