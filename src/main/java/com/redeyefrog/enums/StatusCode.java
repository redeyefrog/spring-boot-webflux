package com.redeyefrog.enums;

public enum StatusCode {

    SUCCESS("0000", "Success"),

    CLIENT_OR_SERVER_ERROR("9000", "Client or Server Error({0})"),

    UNKNOWN("9999", "Unknown Error");

    private String code;

    private String desc;

    StatusCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

}
