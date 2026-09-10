/**
 * Copyright(C) 2026 Luvina
 * employees.ts - API Service for Employee Domain
 * 21/08/2026 Pham Van Minh
 */

import { apiClient } from './client';
import {
  EmployeeItem,
  GetEmployeesApiResponse,
  GetEmployeesParams,
  EmployeeDetail,
  GetEmployeeDetailApiResponse,
  DeleteEmployeeApiResponse,
  EmployeeCertificationDetail,
  EmployeeFormData,
  EmployeeFormErrors,
  EmployeeFormMode,
  AddEmployeeApiResponse,
  AddEmployeeApiRequest,
  UpdateEmployeeApiResponse,
  UpdateEmployeeApiRequest,
} from '@/types/employee';

/**
 * Gọi API GET /employee để lấy danh sách nhân viên theo tiêu chí tìm kiếm, phân trang và sắp xếp.
 *
 * @param params Tham số tìm kiếm, phân trang và sắp xếp.
 * @returns Promise chứa dữ liệu danh sách nhân viên và tổng số bản ghi từ backend.
 */
export async function getEmployees(
  params: GetEmployeesParams = {}
): Promise<GetEmployeesApiResponse> {
  const {
    employeeName,
    departmentId,
    limit,
    offset,
    pageNo,
    pageSize,
    employeeNameOrder = 'ASC',
    certificationNameOrder = 'ASC',
    endDateOrder = 'ASC',
    sortBy = 'employeeNameOrder',
  } = params;

  // Xử lý limit / pageSize
  let finalLimit: number | undefined = undefined;
  if (limit !== undefined && limit !== null) {
    finalLimit = limit;
  } else if (pageSize !== undefined && pageSize !== null) {
    finalLimit = pageSize;
  }

  // Xử lý offset / pageNo
  let finalOffset: number | undefined = undefined;
  if (offset !== undefined && offset !== null) {
    finalOffset = offset;
  } else if (pageNo !== undefined && pageNo !== null) {
    const calcLimit = finalLimit ?? 5;
    finalOffset = (pageNo - 1) * calcLimit;
  }

  // Mặc định cả 3 trường sắp xếp theo đúng tài liệu thiết kế (design-doc & api-design-doc)
  const sortParams: Record<string, string> = {
    ord_employee_name: employeeNameOrder || 'ASC',
    ord_certification_name: certificationNameOrder || 'ASC',
    ord_end_date: endDateOrder || 'ASC',
  };

  const queryParams: Record<string, string | number> = {};

  // Đưa trường đang được ưu tiên sort lên đầu danh sách tham số để backend nhận diện thứ tự
  if (sortBy === 'certificationNameOrder' || sortBy === 'ord_certification_name') {
    queryParams.ord_certification_name = sortParams.ord_certification_name;
    queryParams.ord_employee_name = sortParams.ord_employee_name;
    queryParams.ord_end_date = sortParams.ord_end_date;
  } else if (sortBy === 'endDateOrder' || sortBy === 'ord_end_date') {
    queryParams.ord_end_date = sortParams.ord_end_date;
    queryParams.ord_employee_name = sortParams.ord_employee_name;
    queryParams.ord_certification_name = sortParams.ord_certification_name;
  } else {
    queryParams.ord_employee_name = sortParams.ord_employee_name;
    queryParams.ord_certification_name = sortParams.ord_certification_name;
    queryParams.ord_end_date = sortParams.ord_end_date;
  }

  if (finalOffset !== undefined) {
    queryParams.offset = finalOffset;
  }

  if (finalLimit !== undefined) {
    queryParams.limit = finalLimit;
  }

  if (employeeName && employeeName.trim() !== '') {
    queryParams.employee_name = employeeName.trim();
  }

  if (departmentId !== undefined && departmentId !== null && String(departmentId).trim() !== '') {
    queryParams.department_id = String(departmentId);
  }

  const response = await apiClient.get<GetEmployeesApiResponse>('/employee', {
    params: queryParams,
  });

  return response.data;
}

/**
 * Gọi API GET /employee/:id để lấy thông tin chi tiết một nhân viên.
 *
 * @param id ID của nhân viên cần lấy thông tin.
 * @returns Promise chứa dữ liệu chi tiết nhân viên từ backend.
 */
export async function getEmployeeById(id: number | string): Promise<GetEmployeeDetailApiResponse> {
  const response = await apiClient.get<GetEmployeeDetailApiResponse>(`/employee/${id}`);
  const data = response.data;
  if (data && data.employeeId && !data.employee) {
    const firstCert = data.certifications && data.certifications.length > 0 ? data.certifications[0] : null;
    data.employee = {
      employeeId: data.employeeId,
      employeeLoginId: data.employeeLoginId || '',
      employeeName: data.employeeName || '',
      employeeNameKana: data.employeeNameKana || '',
      employeeBirthDate: data.employeeBirthDate || '',
      departmentId: Number(data.departmentId) || 0,
      departmentName: data.departmentName || '',
      employeeEmail: data.employeeEmail || '',
      employeeTelephone: data.employeeTelephone || '',
      certificationId: firstCert ? Number(firstCert.certificationId) : null,
      certificationName: firstCert ? firstCert.certificationName : null,
      certificationStartDate: firstCert ? firstCert.startDate : null,
      certificationEndDate: firstCert ? firstCert.endDate : null,
      score: firstCert && firstCert.score !== null && firstCert.score !== undefined ? Number(firstCert.score) : null,
    };
  }
  return data;
}

/**
 * Gọi API DELETE /employee/:id để xóa một nhân viên theo ID.
 *
 * @param id ID của nhân viên cần xóa.
 * @returns Promise chứa phản hồi từ server (DeleteEmployeeApiResponse).
 */
export async function deleteEmployee(id: number | string): Promise<DeleteEmployeeApiResponse> {
  const response = await apiClient.delete<DeleteEmployeeApiResponse>(`/employee/${id}`);
  return response.data;
}

/**
 * Gọi API POST /employee để tạo mới nhân viên theo tài liệu thiết kế API.
 *
 * @param formData Dữ liệu form nhập liệu nhân viên từ ADM004/ADM005.
 * @returns Promise chứa phản hồi từ server (AddEmployeeApiResponse).
 */
export async function addEmployee(formData: EmployeeFormData): Promise<AddEmployeeApiResponse> {
  const hasCert =
    formData.certificationId !== '' &&
    formData.certificationId !== null &&
    formData.certificationId !== undefined &&
    Number(formData.certificationId) > 0;

  const certifications = hasCert
    ? [
        {
          certificationId: String(formData.certificationId),
          startDate: formData.certificationStartDate || '',
          endDate: formData.certificationEndDate || '',
          score: String(formData.score || ''),
        },
      ]
    : undefined;

  const requestBody = {
    employeeLoginId: formData.employeeLoginId,
    employeeLoginPassword: formData.employeeLoginPassword,
    employeeName: formData.employeeName,
    employeeNameKana: formData.employeeNameKana,
    employeeBirthDate: formData.employeeBirthDate,
    employeeEmail: formData.employeeEmail,
    employeeTelephone: formData.employeeTelephone,
    departmentId: String(formData.departmentId),
    certifications,
  };

  const response = await apiClient.post<AddEmployeeApiResponse>('/employee', requestBody);
  return response.data;
}

/**
 * Gọi API PUT /employee để cập nhật nhân viên theo tài liệu thiết kế API.
 *
 * @param formData Dữ liệu form chỉnh sửa nhân viên từ ADM004/ADM005.
 * @returns Promise chứa phản hồi từ server (UpdateEmployeeApiResponse).
 */
export async function updateEmployee(
  formData: EmployeeFormData
): Promise<UpdateEmployeeApiResponse> {
  const hasCert =
    formData.certificationId !== '' &&
    formData.certificationId !== null &&
    formData.certificationId !== undefined &&
    Number(formData.certificationId) > 0;

  const certifications = hasCert
    ? {
        certificationId: String(formData.certificationId),
        startDate: formData.certificationStartDate || '',
        endDate: formData.certificationEndDate || '',
        score: String(formData.score || ''),
      }
    : undefined;

  const requestBody: Record<string, unknown> = {
    employeeId: formData.employeeId,
    employeeLoginId: formData.employeeLoginId,
    employeeName: formData.employeeName,
    employeeNameKana: formData.employeeNameKana,
    employeeBirthDate: formData.employeeBirthDate,
    employeeEmail: formData.employeeEmail,
    employeeTelephone: formData.employeeTelephone,
    departmentId: String(formData.departmentId),
  };

  if (formData.employeeLoginPassword && formData.employeeLoginPassword.trim() !== '') {
    requestBody.employeeLoginPassword = formData.employeeLoginPassword.trim();
  }

  if (certifications) {
    requestBody.certifications = certifications;
  }

  const response = await apiClient.put<UpdateEmployeeApiResponse>('/employee', requestBody);
  return response.data;
}

export type {
  EmployeeItem,
  GetEmployeesApiResponse,
  GetEmployeesParams,
  EmployeeDetail,
  GetEmployeeDetailApiResponse,
  DeleteEmployeeApiResponse,
  EmployeeCertificationDetail,
  EmployeeFormData,
  EmployeeFormErrors,
  EmployeeFormMode,
};
export type {
  AddEmployeeApiRequest,
  AddEmployeeApiResponse,
  UpdateEmployeeApiRequest,
  UpdateEmployeeApiResponse,
} from '@/types/employee';


