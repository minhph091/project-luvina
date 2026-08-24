package com.luvina.la.validator;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeValidatorTest.java, 24/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.payload.response.MessageResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test cho EmployeeValidator.
 *
 * @author Phạm Văn Minh
 */
public class EmployeeValidatorTest {

    private EmployeeValidator employeeValidator;

    @BeforeEach
    void setUp() {
        employeeValidator = new EmployeeValidator();
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams thành công khi tất cả tham số hợp lệ")
    void testValidateGetEmployeesParamsValid() {
        MessageResponse error = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "10");
        assertNull(error);

        // Với tham số null hoặc rỗng (mặc định)
        MessageResponse errorNull = employeeValidator.validateGetEmployeesParams(null, null, null, null, null);
        assertNull(errorNull);
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams lỗi khi tham số order không hợp lệ (ER021)")
    void testValidateGetEmployeesParamsInvalidOrder() {
        MessageResponse error = employeeValidator.validateGetEmployeesParams("INVALID", "ASC", "DESC", "0", "10");
        assertNotNull(error);
        assertEquals(Constants.ERROR_CODE_ER021, error.getCode());
        assertTrue(error.getParams().isEmpty());

        MessageResponse errorCert = employeeValidator.validateGetEmployeesParams("ASC", "WRONG", "DESC", "0", "10");
        assertNotNull(errorCert);
        assertEquals(Constants.ERROR_CODE_ER021, errorCert.getCode());

        MessageResponse errorEnd = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "WRONG", "0", "10");
        assertNotNull(errorEnd);
        assertEquals(Constants.ERROR_CODE_ER021, errorEnd.getCode());
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams lỗi khi offset âm hoặc sai định dạng (ER018)")
    void testValidateGetEmployeesParamsInvalidOffset() {
        MessageResponse errorNegative = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "-1", "10");
        assertNotNull(errorNegative);
        assertEquals(Constants.ERROR_CODE_ER018, errorNegative.getCode());
        assertEquals(List.of(Constants.PARAM_OFFSET), errorNegative.getParams());

        MessageResponse errorFormat = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "abc", "10");
        assertNotNull(errorFormat);
        assertEquals(Constants.ERROR_CODE_ER018, errorFormat.getCode());
        assertEquals(List.of(Constants.PARAM_OFFSET), errorFormat.getParams());
    }

    @Test
    @DisplayName("Test validateGetEmployeesParams lỗi khi limit <= 0 hoặc sai định dạng (ER018)")
    void testValidateGetEmployeesParamsInvalidLimit() {
        MessageResponse errorZero = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "0");
        assertNotNull(errorZero);
        assertEquals(Constants.ERROR_CODE_ER018, errorZero.getCode());
        assertEquals(List.of(Constants.PARAM_LIMIT), errorZero.getParams());

        MessageResponse errorNegative = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "-5");
        assertNotNull(errorNegative);
        assertEquals(Constants.ERROR_CODE_ER018, errorNegative.getCode());
        assertEquals(List.of(Constants.PARAM_LIMIT), errorNegative.getParams());

        MessageResponse errorFormat = employeeValidator.validateGetEmployeesParams("ASC", "DESC", "ASC", "0", "xyz");
        assertNotNull(errorFormat);
        assertEquals(Constants.ERROR_CODE_ER018, errorFormat.getCode());
        assertEquals(List.of(Constants.PARAM_LIMIT), errorFormat.getParams());
    }

    @Test
    @DisplayName("Test isValidOrderParam chấp nhận null, chuỗi rỗng, ASC, DESC bất kể hoa thường")
    void testIsValidOrderParam() {
        assertTrue(employeeValidator.isValidOrderParam(null));
        assertTrue(employeeValidator.isValidOrderParam(""));
        assertTrue(employeeValidator.isValidOrderParam("   "));
        assertTrue(employeeValidator.isValidOrderParam("ASC"));
        assertTrue(employeeValidator.isValidOrderParam("asc"));
        assertTrue(employeeValidator.isValidOrderParam("DESC"));
        assertTrue(employeeValidator.isValidOrderParam("desc"));
        assertFalse(employeeValidator.isValidOrderParam("ascending"));
        assertFalse(employeeValidator.isValidOrderParam("123"));
    }
}
