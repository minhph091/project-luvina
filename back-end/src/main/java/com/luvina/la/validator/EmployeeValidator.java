package com.luvina.la.validator;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeValidator.java, 24/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.payload.response.MessageResponse;
import java.util.ArrayList;
import java.util.Collections;
import org.springframework.stereotype.Component;

/**
 * Validator kiểm tra tính hợp lệ của các tham số liên quan đến nhân viên.
 *
 * @author Phạm Văn Minh
 */
@Component
public class EmployeeValidator {

    /**
     * Validate toàn bộ các tham số đầu vào khi lấy danh sách nhân viên.
     *
     * @param ordEmployeeName      Chiều sắp xếp theo tên nhân viên.
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn chứng chỉ.
     * @param offsetStr            Vị trí bắt đầu lấy bản ghi dưới dạng chuỗi.
     * @param limitStr             Số bản ghi tối đa dưới dạng chuỗi.
     * @return MessageResponse chứa thông tin lỗi nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateGetEmployeesParams(
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offsetStr,
            String limitStr) {

        // 1.1 Validate ord_employee_name, ord_certification_name, ord_end_date
        MessageResponse orderError = validateOrderParams(ordEmployeeName, ordCertificationName, ordEndDate);
        if (orderError != null) {
            return orderError;
        }

        // 1.2 Validate offset
        MessageResponse offsetError = validateOffset(offsetStr);
        if (offsetError != null) {
            return offsetError;
        }

        // 1.3 Validate limit
        MessageResponse limitError = validateLimit(limitStr);
        if (limitError != null) {
            return limitError;
        }

        return null;
    }

    /**
     * Kiểm tra tính hợp lệ của các tham số sắp xếp.
     *
     * @param ordEmployeeName      Chiều sắp xếp theo tên nhân viên.
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ.
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn.
     * @return MessageResponse lỗi ER021 nếu có tham số không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateOrderParams(
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate) {
        if (!isValidOrderParam(ordEmployeeName)
                || !isValidOrderParam(ordCertificationName)
                || !isValidOrderParam(ordEndDate)) {
            return new MessageResponse(Constants.ERROR_CODE_ER021, new ArrayList<>());
        }
        return null;
    }

    /**
     * Kiểm tra tính hợp lệ của một tham số sắp xếp (chấp nhận rỗng, "ASC" hoặc "DESC").
     *
     * @param orderParam Tham số order cần kiểm tra.
     * @return true nếu hợp lệ, false nếu không hợp lệ.
     */
    public boolean isValidOrderParam(String orderParam) {
        if (orderParam == null || orderParam.trim().isEmpty()) {
            return true;
        }
        String trimmed = orderParam.trim();
        return Constants.ORDER_ASC.equalsIgnoreCase(trimmed) || Constants.ORDER_DESC.equalsIgnoreCase(trimmed);
    }

    /**
     * Validate tham số offset (phải là số nguyên không âm).
     *
     * @param offsetStr Giá trị offset dạng chuỗi.
     * @return MessageResponse lỗi ER018 nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateOffset(String offsetStr) {
        if (offsetStr != null && !offsetStr.trim().isEmpty()) {
            try {
                int parsedOffset = Integer.parseInt(offsetStr.trim());
                if (parsedOffset < 0) {
                    return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_OFFSET));
                }
            } catch (NumberFormatException ex) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_OFFSET));
            }
        }
        return null;
    }

    /**
     * Validate tham số limit (phải là số nguyên dương > 0).
     *
     * @param limitStr Giá trị limit dạng chuỗi.
     * @return MessageResponse lỗi ER018 nếu không hợp lệ, hoặc null nếu hợp lệ.
     */
    public MessageResponse validateLimit(String limitStr) {
        if (limitStr != null && !limitStr.trim().isEmpty()) {
            try {
                int parsedLimit = Integer.parseInt(limitStr.trim());
                if (parsedLimit <= 0) {
                    return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_LIMIT));
                }
            } catch (NumberFormatException ex) {
                return new MessageResponse(Constants.ERROR_CODE_ER018, Collections.singletonList(Constants.PARAM_LIMIT));
            }
        }
        return null;
    }
}
