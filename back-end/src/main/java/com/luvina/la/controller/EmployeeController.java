package com.luvina.la.controller;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeController.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeListDTO;
import com.luvina.la.exception.CustomValidationException;
import com.luvina.la.mapper.EmployeeMapper;
import com.luvina.la.payload.response.EmployeeResponse;
import com.luvina.la.payload.response.ListEmployeesResponse;
import com.luvina.la.payload.response.MessageResponse;
import com.luvina.la.service.EmployeeService;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý các request liên quan đến nhân viên.
 * Nhận DTO từ Service và chuyển đổi sang Response Payload trả về client.
 *
 * @author Phạm Văn Minh
 */
@RestController
public class EmployeeController {

    private static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    /**
     * Khởi tạo EmployeeController.
     *
     * @param employeeService Service xử lý nghiệp vụ nhân viên.
     * @param employeeMapper  Mapper chuyển đổi từ DTO sang Response Payload.
     */
    public EmployeeController(
            EmployeeService employeeService,
            EmployeeMapper employeeMapper) {
        this.employeeService = employeeService;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Lấy danh sách nhân viên có phân trang, tìm kiếm và sắp xếp theo tài liệu thiết kế API (GET /employee).
     *
     * @param employeeName         Tên nhân viên cần lọc (employee_name theo thiết kế).
     * @param departmentId         ID phòng ban cần lọc (department_id theo thiết kế).
     * @param ordEmployeeName      Chiều sắp xếp theo tên (ord_employee_name: ASC/DESC theo thiết kế).
     * @param ordCertificationName Chiều sắp xếp theo tên chứng chỉ (ord_certification_name: ASC/DESC theo thiết kế).
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn (ord_end_date: ASC/DESC theo thiết kế).
     * @param offset               Vị trí bắt đầu lấy bản ghi (offset theo thiết kế).
     * @param limit                Số bản ghi trên mỗi trang (limit theo thiết kế).
     * @param request              HttpServletRequest để xác định thứ tự ưu tiên cột sắp xếp từ query string.
     * @return Response chứa mã kết quả, tổng số bản ghi và danh sách nhân viên.
     */
    @GetMapping("/employee")
    public ListEmployeesResponse getEmployees(
            @RequestParam(required = false, name = "employee_name") String employeeName,
            @RequestParam(required = false, name = "department_id") String departmentId,
            @RequestParam(required = false, name = "ord_employee_name") String ordEmployeeName,
            @RequestParam(required = false, name = "ord_certification_name") String ordCertificationName,
            @RequestParam(required = false, name = "ord_end_date") String ordEndDate,
            @RequestParam(required = false, name = "offset") String offset,
            @RequestParam(required = false, name = "limit") String limit,
            HttpServletRequest request) {

        String sortBy = extractSortBy(request);

        try {
            EmployeeListDTO employeeListDTO = employeeService.getEmployees(
                    employeeName,
                    departmentId,
                    ordEmployeeName,
                    ordCertificationName,
                    ordEndDate,
                    offset,
                    limit,
                    sortBy);

            List<EmployeeResponse> employeeResponses = employeeMapper.toResponseList(employeeListDTO.getEmployees());

            return ListEmployeesResponse.builder()
                    .code(Constants.RESPONSE_CODE_SUCCESS)
                    .totalRecords(employeeListDTO.getTotalRecords())
                    .employees(employeeResponses)
                    .build();

        } catch (CustomValidationException ex) {
            log.warn("Validation error in getEmployees: {}", ex.getMessageResponse());
            return ListEmployeesResponse.builder()
                    .code(Constants.RESPONSE_CODE_ERROR)
                    .message(ex.getMessageResponse())
                    .build();
        } catch (Exception ex) {
            log.error("Error occurred while getting employee list: ", ex);
            return ListEmployeesResponse.builder()
                    .code(Constants.RESPONSE_CODE_ERROR)
                    .message(new MessageResponse(Constants.ERROR_CODE_ER015, new ArrayList<>()))
                    .build();
        }
    }

    /**
     * Overload getEmployees phục vụ gọi trực tiếp hoặc kiểm thử không cần request context.
     */
    public ListEmployeesResponse getEmployees(
            String employeeName,
            String departmentId,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String offset,
            String limit) {
        return getEmployees(employeeName, departmentId, ordEmployeeName, ordCertificationName, ordEndDate, offset, limit, (HttpServletRequest) null);
    }

    /**
     * Xác định cột ưu tiên sắp xếp hàng đầu từ thứ tự xuất hiện trong query string hoặc param sortBy.
     *
     * @param request HttpServletRequest
     * @return Tên cột sắp xếp ưu tiên (certificationNameOrder, endDateOrder, employeeNameOrder) hoặc null.
     */
    private String extractSortBy(HttpServletRequest request) {
        if (request == null) {
            return null;
        }

        String sortByParam = request.getParameter("sortBy");
        if (sortByParam != null && !sortByParam.trim().isEmpty()) {
            return sortByParam.trim();
        }

        String qs = request.getQueryString();
        if (qs == null || qs.trim().isEmpty()) {
            return null;
        }

        int idxCert = qs.indexOf("ord_certification_name");
        int idxEnd = qs.indexOf("ord_end_date");
        int idxName = qs.indexOf("ord_employee_name");

        int minIdx = Integer.MAX_VALUE;
        String firstSort = null;

        if (idxCert != -1 && idxCert < minIdx) {
            minIdx = idxCert;
            firstSort = "certificationNameOrder";
        }
        if (idxEnd != -1 && idxEnd < minIdx) {
            minIdx = idxEnd;
            firstSort = "endDateOrder";
        }
        if (idxName != -1 && idxName < minIdx) {
            minIdx = idxName;
            firstSort = "employeeNameOrder";
        }

        return firstSort;
    }
}
