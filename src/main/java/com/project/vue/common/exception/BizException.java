package com.project.vue.common.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class BizException extends RuntimeException {

	private static final long serialVersionUID = 8290261778633064678L;
	private ErrorCode errorCode;

	public BizException(String message, ErrorCode errorCode) {
		super(message);
	    this.errorCode = errorCode;
	}

	public BizException(String message, Throwable cause, ErrorCode errorCode) {
		super(message, cause);
	    this.errorCode = errorCode;
	}

    /**
     * Error Code Enum
     * @author KoeyNim-이민혁
     */
    @Getter
    @JsonFormat(shape = Shape.OBJECT)
    @RequiredArgsConstructor
    public enum ErrorCode {

    	BAD_REQUEST(400, "Bad Request"),
    	UNAUTHORIZED(401, "Unauthorized"),
    	NOT_FOUND(404, "Not Found"),
    	INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    	SERVICE_UNAVAILABLE(503, "Service Unavailable");

    	private final int status;
    	private final String error;
    	
    }
}
