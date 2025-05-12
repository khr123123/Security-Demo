package org.khr.securitydemo.common;

import lombok.Getter;

/**
 * 自定义错误码
 */
@Getter
public enum ErrorCode {

    SUCCESS(0, "成功"),
    PARAMS_ERROR(40000, "リクエストパラメータが無効です"),
    NOT_LOGIN_ERROR(40100, "ログインが必要です"),
    NO_AUTH_ERROR(40101, "権限がありません"),
    NOT_FOUND_ERROR(40400, "リクエストしたデータが見つかりません"),
    TOO_MANY_REQUEST(42900, "リクエストが多すぎます"),
    FORBIDDEN_ERROR(40300, "アクセスが禁止されています"),
    SYSTEM_ERROR(50000, "システム内部エラー"),
    OPERATION_ERROR(50001, "操作に失敗しました");


    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}