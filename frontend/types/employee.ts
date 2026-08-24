/**
 * Copyright(C) 2026 Luvina
 * employee.ts - Type Definitions for Employee Domain
 * 21/08/2026 Pham Van Minh
 */

import { DepartmentItem } from './department';

export type SortOrder = 'ASC' | 'DESC';

/**
 * Trạng thái sắp xếp các cột trong bảng danh sách nhân viên.
 */
export interface SortState {
  employeeNameOrder: SortOrder;
  certificationNameOrder: SortOrder;
  endDateOrder: SortOrder;
}

/**
 * Thông tin một nhân viên trả về từ API danh sách (khớp với EmployeeResponse.java).
 */
export interface EmployeeItem {
  employeeId: number;
  employeeName: string;
  employeeBirthDate: string | null; // "yyyy-MM-dd"
  departmentName: string;
  employeeEmail: string;
  employeeTelephone: string;
  certificationName: string | null;
  endDate: string | null; // "yyyy-MM-dd"
  score: number | null;
}

/**
 * Phản hồi từ API GET /employee.
 */
export interface GetEmployeesApiResponse {
  code: number;
  totalRecords?: number;
  employees: EmployeeItem[];
}

/**
 * Tham số truyền vào API GET /employee.
 */
export interface GetEmployeesParams {
  employeeName?: string;
  departmentId?: number;
  limit?: number;
  offset?: number;
  pageNo?: number;
  pageSize?: number;
  employeeNameOrder?: SortOrder;
  certificationNameOrder?: SortOrder;
  endDateOrder?: SortOrder;
  sortBy?: string;
}

// Re-export DepartmentItem for convenience
export type { DepartmentItem };
