package com.spring.common.model;

public enum ErrorCode {
	INTERNAL_SERVER_ERROR(500, "C_001", "서버 에러"), 
	METHOD_NOT_ALLOWED(405, "C_002", "Api는 열려있으나 메소드는 사용 불가합니다."),
	INVALID_INPUT_VALUE(400, "C_003", "적절하지 않은 요청 값입니다."),
	INVALID_TYPE_VALUE(400, "C_004", "요청 값의 타입이 잘못되었습니다."),
	DUPLICATED_APPLY(400, "C_005", "이미 신청한 COURSE 입니다."),
	EXCEED_APPLY(400, "C_006", "신청인원이 초과하여 마감되었습니다."),
	
	AUTH_ERROR(400, "AU_001", "인증 관련 오류가 발생했습니다."),
	DUPLICATED_EMAIL(400, "AU_002", "이미 존재하는 E-mail입니다."),
	NOTFOUND_EMAIL(400, "AU_003", "존재하지 않는 E-mail입니다."),
	UNAUTHORIZED_REDIRECT_URI(400, "AU_004", "인증되지 않은 REDIRECT_URI입니다."),
	BAD_LOGIN(400, "AU_005", "잘못된 아이디 또는 패스워드입니다.");
	
	private final int status;
	private final String code;
	private final String message;

	ErrorCode(int status, String code, String message) {
		this.status = status;
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCode() {
		return code;
	}

	public int getStatus() {
		return status;
	}
}
