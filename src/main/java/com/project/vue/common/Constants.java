package com.project.vue.common;

public class Constants {
	/** 주로 서버를 2개 이상 운용할 때 버전관리 용으로 사용 */
    public static final String API_VERSION = "/v1";
    public static final String REQUEST_MAPPING_PREFIX = "/api" + API_VERSION;

    /** JWT 관련 상수 */
    public static final String AUTH_HEADER = "Authorization";
    public static final String TOKEN_TYPE = "Bearer";
}
