package com.project.vue.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.util.AlternativeJdkIdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Utils {

	private static final AlternativeJdkIdGenerator IdGenerator = new AlternativeJdkIdGenerator();

	/** 2023/01/16~ 사용하지 않음 */
	// IP check : request.getRemoteAddr() 값을 받음
	// IPv4로 받고 싶을경우 VM argument 에서 -Djava.net.preferIPv4Stack=true 값 추가.
	public static String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");
	    log.info("> X-FORWARDED-FOR : " + ip);
	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
	        log.info("> Proxy-Client-IP : " + ip);
	        ip = request.getHeader("WL-Proxy-Client-IP");
	        log.info(">  WL-Proxy-Client-IP : " + ip);
	        ip = request.getHeader("HTTP_CLIENT_IP");
	        log.info("> HTTP_CLIENT_IP : " + ip);
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	        log.info("> HTTP_X_FORWARDED_FOR : " + ip);
	        ip = request.getRemoteAddr();
	        log.info("> getRemoteAddr : "+ ip);
	    }
	    log.info("> Result : IP Address : "+ip);

	    return ip;
	}

	public static String getUUID16() {
		return IdGenerator.generateId().toString()
		          .replace("-", "")
		          .substring(0, 16);
	}

	public static String getUUID32() {
		return IdGenerator.generateId().toString()
		          .replace("-", "");
	}
}
