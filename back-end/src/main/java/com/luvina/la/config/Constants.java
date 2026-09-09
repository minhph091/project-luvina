package com.luvina.la.config;

/**
 * Copyright(C) 2026 Luvina
 * Constants.java, 21/08/2026 Phạm Văn Minh
 */

/**
 * Lớp khai báo các hằng số dùng chung trong toàn bộ hệ thống.
 *
 * @author Phạm Văn Minh
 */
public class Constants {

    private Constants() {
    }

    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final boolean IS_CROSS_ALLOW = true;

    public static final String JWT_SECRET = "Luvina-Academe";
    public static final long JWT_EXPIRATION = 160 * 60 * 60; // 7 day

    // config endpoints public
    public static final String[] ENDPOINTS_PUBLIC = new String[] {
            "/",
            "/login/**",
            "/register/**",
            "/create-account/**",
            "/error/**"
    };

    // config endpoints for USER role
    public static final String[] ENDPOINTS_WITH_ROLE = new String[] {
            "/user/**"
    };

    // user attributies put to token
    public static final String[] ATTRIBUTIES_TO_TOKEN = new String[] {
            "employeeId",
            "employeeName",
            "employeeLoginId",
            "employeeEmail"
    };

    // Roles
    public static final String ROLE_ADMIN = "ADMIN";

    // Response Code
    public static final int RESPONSE_CODE_SUCCESS = 200;
    public static final int RESPONSE_CODE_ERROR = 500;

    // Success Message Codes
    public static final String MESSAGE_CODE_MSG001 = "MSG001";
    public static final String MESSAGE_CODE_MSG003 = "MSG003";

    // Error Codes
    public static final String ERROR_CODE_ER001 = "ER001";
    public static final String ERROR_CODE_ER002 = "ER002";
    public static final String ERROR_CODE_ER003 = "ER003";
    public static final String ERROR_CODE_ER004 = "ER004";
    public static final String ERROR_CODE_ER005 = "ER005";
    public static final String ERROR_CODE_ER006 = "ER006";
    public static final String ERROR_CODE_ER007 = "ER007";
    public static final String ERROR_CODE_ER008 = "ER008";
    public static final String ERROR_CODE_ER009 = "ER009";
    public static final String ERROR_CODE_ER011 = "ER011";
    public static final String ERROR_CODE_ER012 = "ER012";
    public static final String ERROR_CODE_ER013 = "ER013";
    public static final String ERROR_CODE_ER014 = "ER014";
    public static final String ERROR_CODE_ER015 = "ER015";
    public static final String ERROR_CODE_ER018 = "ER018";
    public static final String ERROR_CODE_ER019 = "ER019";
    public static final String ERROR_CODE_ER021 = "ER021";
    public static final String ERROR_CODE_ER023 = "ER023";

    // Error Parameter Names
    public static final String PARAM_OFFSET = "オフセット";
    public static final String PARAM_LIMIT = "リミット";
    public static final String PARAM_ACCOUNT_NAME = "アカウント名";
    public static final String PARAM_NAME = "氏名";
    public static final String PARAM_KATAKANA_NAME = "カタカナ氏名";
    public static final String PARAM_BIRTHDAY = "生年月日";
    public static final String PARAM_EMAIL = "メールアドレス";
    public static final String PARAM_TEL = "電話番号";
    public static final String PARAM_PASSWORD = "パスワード";
    public static final String PARAM_GROUP = "グループ";
    public static final String PARAM_CERTIFICATION_START_DATE = "資格交付日";
    public static final String PARAM_CERTIFICATION_END_DATE = "失効日";
    public static final String PARAM_SCORE = "点数";
    public static final String PARAM_CERTIFICATION = "資格";
    public static final String PARAM_ID = "ＩＤ";

    // Date Format
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy/MM/dd";

    // Order Directions
    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";

    // Default Pagination
    public static final int DEFAULT_LIMIT = 5;
    public static final int DEFAULT_OFFSET = 0;
}
