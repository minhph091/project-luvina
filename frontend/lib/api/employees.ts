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
    sortBy,
  } = params;

  const queryParams: Record<string, string | number> = {
    employeeNameOrder,
    certificationNameOrder,
    endDateOrder,
  };

  if (sortBy) {
    queryParams.sortBy = sortBy;
    queryParams.sort_by = sortBy;
  }

  if (limit !== undefined && limit !== null) {
    queryParams.limit = limit;
  } else if (pageSize !== undefined && pageSize !== null) {
    queryParams.pageSize = pageSize;
  }

  if (offset !== undefined && offset !== null) {
    queryParams.offset = offset;
  } else if (pageNo !== undefined && pageNo !== null) {
    queryParams.pageNo = pageNo;
  }

  if (employeeName && employeeName.trim() !== '') {
    queryParams.employeeName = employeeName.trim();
  }

  if (departmentId !== undefined && departmentId !== null) {
    queryParams.departmentId = departmentId;
  }

  const response = await apiClient.get<GetEmployeesApiResponse>('/employee', {
    params: queryParams,
  });

  return response.data;
}

export type { EmployeeItem, GetEmployeesApiResponse, GetEmployeesParams };
