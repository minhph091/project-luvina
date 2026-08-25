package com.luvina.la.repository;

/**
 * Copyright(C) 2026 Luvina
 * EmployeeNativeRepository.java, 21/08/2026 Phạm Văn Minh
 */

import com.luvina.la.config.Constants;
import com.luvina.la.dto.EmployeeDTO;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;

/**
 * Repository thực hiện truy vấn native SQL cho nhân viên.
 * Dùng để lấy danh sách nhân viên với JOIN nhiều bảng và phân trang.
 * Mỗi nhân viên chỉ hiển thị duy nhất 1 chứng chỉ có level cao nhất (nếu có).
 *
 * @author Phạm Văn Minh
 */
@Repository
public class EmployeeNativeRepository {

    private final EntityManager entityManager;

    /**
     * Khởi tạo EmployeeNativeRepository.
     *
     * @param entityManager EntityManager dùng để thực thi native query.
     */
    public EmployeeNativeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Lấy danh sách nhân viên theo điều kiện với limit và offset.
     * Mỗi nhân viên chỉ lấy 1 chứng chỉ ưu tiên (level cao nhất).
     *
     * @param employeeName         Tên nhân viên để lọc (null nếu không lọc).
     * @param departmentId         ID phòng ban để lọc (null nếu không lọc).
     * @param limit                Số bản ghi tối đa cần lấy.
     * @param offset               Vị trí bắt đầu lấy bản ghi.
     * @param ordEmployeeName      Chiều sắp xếp theo tên (ASC/DESC).
     * @param ordCertificationName Chiều sắp xếp theo chứng chỉ (ASC/DESC).
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn (ASC/DESC).
     * @return Danh sách EmployeeDTO theo trang.
     */
    @SuppressWarnings("unchecked")
    public List<EmployeeDTO> findEmployees(
            String employeeName,
            Long departmentId,
            int limit,
            int offset,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate) {
        return findEmployees(employeeName, departmentId, limit, offset, ordEmployeeName, ordCertificationName,
                ordEndDate, null);
    }

    /**
     * Lấy danh sách nhân viên theo điều kiện với limit, offset và độ ưu tiên sắp
     * xếp cột.
     * Mỗi nhân viên chỉ lấy 1 chứng chỉ ưu tiên (level cao nhất).
     *
     * @param employeeName         Tên nhân viên để lọc (null nếu không lọc).
     * @param departmentId         ID phòng ban để lọc (null nếu không lọc).
     * @param limit                Số bản ghi tối đa cần lấy.
     * @param offset               Vị trí bắt đầu lấy bản ghi.
     * @param ordEmployeeName      Chiều sắp xếp theo tên (ASC/DESC).
     * @param ordCertificationName Chiều sắp xếp theo chứng chỉ (ASC/DESC).
     * @param ordEndDate           Chiều sắp xếp theo ngày hết hạn (ASC/DESC).
     * @param sortBy               Cột đang được ưu tiên sắp xếp hàng đầu.
     * @return Danh sách EmployeeDTO theo trang.
     */
    @SuppressWarnings("unchecked")
    public List<EmployeeDTO> findEmployees(
            String employeeName,
            Long departmentId,
            int limit,
            int offset,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String sortBy) {

        StringBuilder sql = buildSelectSql(employeeName, departmentId);
        appendOrderBy(sql, ordEmployeeName, ordCertificationName, ordEndDate, sortBy);

        Query query = entityManager.createNativeQuery(sql.toString());
        setFilterParams(query, employeeName, departmentId);

        query.setFirstResult(Math.max(offset, 0));
        query.setMaxResults(limit > 0 ? limit : Constants.DEFAULT_LIMIT);

        List<Object[]> rows = query.getResultList();
        return mapToEmployeeDTO(rows);
    }

    /**
     * Đếm tổng số nhân viên thỏa điều kiện lọc.
     *
     * @param employeeName Tên nhân viên để lọc (null nếu không lọc).
     * @param departmentId ID phòng ban để lọc (null nếu không lọc).
     * @return Tổng số bản ghi nhân viên thỏa điều kiện.
     */
    public Long countEmployees(String employeeName, Long departmentId) {

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(e.employee_id) FROM employees e ");
        sql.append("INNER JOIN departments d ON e.department_id = d.department_id ");
        sql.append("WHERE 1=1 ");

        appendFilterConditions(sql, employeeName, departmentId);

        Query query = entityManager.createNativeQuery(sql.toString());
        setFilterParams(query, employeeName, departmentId);

        Object result = query.getSingleResult();
        return ((Number) result).longValue();
    }

    /**
     * Xây dựng câu SQL SELECT với JOIN các bảng cần thiết.
     * Dùng window function ROW_NUMBER() để chỉ chọn 1 chứng chỉ tốt nhất (cấp độ
     * cao nhất) cho mỗi nhân viên.
     *
     * @param employeeName Tên nhân viên để lọc.
     * @param departmentId ID phòng ban để lọc.
     * @return StringBuilder chứa câu SQL SELECT.
     */
    private StringBuilder buildSelectSql(String employeeName, Long departmentId) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("  e.employee_id, ");
        sql.append("  e.employee_name, ");
        sql.append("  e.employee_birth_date, ");
        sql.append("  d.department_name, ");
        sql.append("  e.employee_email, ");
        sql.append("  e.employee_telephone, ");
        sql.append("  ec_top.certification_name, ");
        sql.append("  ec_top.end_date, ");
        sql.append("  ec_top.score ");
        sql.append("FROM employees e ");
        sql.append("INNER JOIN departments d ON e.department_id = d.department_id ");
        sql.append("LEFT JOIN ( ");
        sql.append("  SELECT ");
        sql.append("    ec.employee_id, ");
        sql.append("    ec.certification_id, ");
        sql.append("    ec.start_date, ");
        sql.append("    ec.end_date, ");
        sql.append("    ec.score, ");
        sql.append("    c.certification_name, ");
        sql.append("    c.certification_level, ");
        sql.append("    ROW_NUMBER() OVER ( ");
        sql.append("      PARTITION BY ec.employee_id ");
        sql.append("      ORDER BY c.certification_level ASC, ec.start_date DESC, ec.employee_certification_id DESC ");
        sql.append("    ) AS rn ");
        sql.append("  FROM employees_certifications ec ");
        sql.append("  INNER JOIN certifications c ON ec.certification_id = c.certification_id ");
        sql.append(") ec_top ON e.employee_id = ec_top.employee_id AND ec_top.rn = 1 ");
        sql.append("WHERE 1=1 ");

        appendFilterConditions(sql, employeeName, departmentId);
        return sql;
    }

    /**
     * Thêm điều kiện lọc vào câu SQL nếu có.
     * Mặc định loại trừ người dùng có role ADMIN.
     *
     * @param sql          StringBuilder đang xây dựng.
     * @param employeeName Tên nhân viên cần lọc.
     * @param departmentId ID phòng ban cần lọc.
     */
    private void appendFilterConditions(
            StringBuilder sql,
            String employeeName,
            Long departmentId) {

        sql.append("AND e.employee_role != '").append(Constants.ROLE_ADMIN).append("' ");

        if (employeeName != null && !employeeName.trim().isEmpty()) {
            sql.append("AND e.employee_name LIKE :employeeName ");
        }
        if (departmentId != null) {
            sql.append("AND e.department_id = :departmentId ");
        }
    }

    /**
     * Thêm mệnh đề ORDER BY vào câu SQL dựa trên các tham số sort và cột ưu tiên.
     * Nếu không có tham số order nào, sắp xếp mặc định theo employee_id ASC.
     *
     * @param sql                  StringBuilder đang xây dựng.
     * @param ordEmployeeName      Chiều sort tên nhân viên (ASC/DESC).
     * @param ordCertificationName Chiều sort tên chứng chỉ (ASC/DESC).
     * @param ordEndDate           Chiều sort ngày hết hạn (ASC/DESC).
     * @param sortBy               Cột đang được ưu tiên hàng đầu.
     */
    private void appendOrderBy(
            StringBuilder sql,
            String ordEmployeeName,
            String ordCertificationName,
            String ordEndDate,
            String sortBy) {

        List<String> orderClauses = new ArrayList<>();

        String nameOrder = isValidOrder(ordEmployeeName) ? ("e.employee_name " + ordEmployeeName.trim().toUpperCase())
                : null;
        String certOrder = isValidOrder(ordCertificationName)
                ? ("ec_top.certification_name " + ordCertificationName.trim().toUpperCase())
                : null;
        String endOrder = isValidOrder(ordEndDate) ? ("ec_top.end_date " + ordEndDate.trim().toUpperCase()) : null;

        String normalizedSortBy = sortBy != null ? sortBy.trim().toLowerCase() : "";

        if ((normalizedSortBy.contains("cert") || normalizedSortBy.contains("nihongo")) && certOrder != null) {
            orderClauses.add(certOrder);
            if (nameOrder != null)
                orderClauses.add(nameOrder);
            if (endOrder != null)
                orderClauses.add(endOrder);
        } else if ((normalizedSortBy.contains("end") || normalizedSortBy.contains("shikkou")) && endOrder != null) {
            orderClauses.add(endOrder);
            if (nameOrder != null)
                orderClauses.add(nameOrder);
            if (certOrder != null)
                orderClauses.add(certOrder);
        } else if (normalizedSortBy.contains("name") && nameOrder != null) {
            orderClauses.add(nameOrder);
            if (certOrder != null)
                orderClauses.add(certOrder);
            if (endOrder != null)
                orderClauses.add(endOrder);
        } else {
            if (nameOrder != null)
                orderClauses.add(nameOrder);
            if (certOrder != null)
                orderClauses.add(certOrder);
            if (endOrder != null)
                orderClauses.add(endOrder);
        }

        if (orderClauses.isEmpty()) {
            sql.append("ORDER BY e.employee_id ASC ");
        } else {
            sql.append("ORDER BY ");
            sql.append(String.join(", ", orderClauses));
            sql.append(", e.employee_id ASC ");
        }
    }

    /**
     * Kiểm tra giá trị sort có hợp lệ (ASC hoặc DESC).
     *
     * @param order Giá trị cần kiểm tra.
     * @return true nếu hợp lệ, false nếu không.
     */
    private boolean isValidOrder(String order) {
        if (order == null || order.trim().isEmpty()) {
            return false;
        }
        String trimmed = order.trim();
        return Constants.ORDER_ASC.equalsIgnoreCase(trimmed) || Constants.ORDER_DESC.equalsIgnoreCase(trimmed);
    }

    /**
     * Gán giá trị các tham số lọc vào native query.
     *
     * @param query        Query đang xây dựng.
     * @param employeeName Tên nhân viên (nếu có).
     * @param departmentId ID phòng ban (nếu có).
     */
    private void setFilterParams(Query query, String employeeName, Long departmentId) {
        if (employeeName != null && !employeeName.trim().isEmpty()) {
            query.setParameter("employeeName", "%" + escapeLikePattern(employeeName.trim()) + "%");
        }
        if (departmentId != null) {
            query.setParameter("departmentId", departmentId);
        }
    }

    /**
     * Escape ký tự đặc biệt trong LIKE query (%, _, \).
     *
     * @param input Chuỗi cần escape.
     * @return Chuỗi đã escape.
     */
    private String escapeLikePattern(String input) {
        if (input == null) {
            return null;
        }
        return input.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");
    }

    /**
     * Chuyển đổi danh sách Object[] từ native query sang danh sách
     * EmployeeDTO.
     *
     * @param rows Danh sách hàng kết quả từ native query.
     * @return Danh sách EmployeeDTO.
     */
    private List<EmployeeDTO> mapToEmployeeDTO(List<Object[]> rows) {
        List<EmployeeDTO> result = new ArrayList<>();
        for (Object[] row : rows) {
            Long employeeId = row[0] != null ? ((Number) row[0]).longValue() : null;
            String employeeName = (String) row[1];
            LocalDate employeeBirthDate = row[2] != null
                    ? ((java.sql.Date) row[2]).toLocalDate()
                    : null;
            String departmentName = (String) row[3];
            String employeeEmail = (String) row[4];
            String employeeTelephone = (String) row[5];
            String certificationName = (String) row[6];
            LocalDate endDate = row[7] != null
                    ? ((java.sql.Date) row[7]).toLocalDate()
                    : null;
            BigDecimal score = row[8] != null ? (BigDecimal) row[8] : null;

            result.add(EmployeeDTO.builder()
                    .employeeId(employeeId)
                    .employeeName(employeeName)
                    .employeeBirthDate(employeeBirthDate)
                    .departmentName(departmentName)
                    .employeeEmail(employeeEmail)
                    .employeeTelephone(employeeTelephone)
                    .certificationName(certificationName)
                    .endDate(endDate)
                    .score(score)
                    .build());
        }
        return result;
    }
}
