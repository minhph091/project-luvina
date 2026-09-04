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

    // Error Codes
    public static final String ERROR_CODE_ER015 = "ER015";
    public static final String ERROR_CODE_ER018 = "ER018";
    public static final String ERROR_CODE_ER021 = "ER021";
    public static final String ERROR_CODE_ER023 = "ER023";

    // Error Parameter Names
    public static final String PARAM_OFFSET = "オフセット";
    public static final String PARAM_LIMIT = "リミット";

    // Order Directions
    public static final String ORDER_ASC = "ASC";
    public static final String ORDER_DESC = "DESC";

    // Default Pagination
    public static final int DEFAULT_LIMIT = 5;
    public static final int DEFAULT_OFFSET = 0;
}
