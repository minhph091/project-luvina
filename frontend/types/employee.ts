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

/**
 * Chế độ hoạt động của Form ADM004: Thêm mới (ADD) hoặc Chỉnh sửa (EDIT)
 */
export type EmployeeFormMode = 'ADD' | 'EDIT';

/**
 * Chi tiết thông tin nhân viên trả về từ API GET /employee/:id (khớp thiết kế ADM003 & ADM004)
 */
export interface EmployeeDetail {
  employeeId: number;
  employeeLoginId: string;
  employeeName: string;
  employeeNameKana: string;
  employeeBirthDate: string;
  departmentId: number;
  departmentName?: string;
  employeeEmail: string;
  employeeTelephone: string;
  certificationId?: number | null;
  certificationName?: string | null;
  certificationStartDate?: string | null;
  certificationEndDate?: string | null;
  score?: number | null;
}

/**
 * Phản hồi từ API GET /employee/:id
 */
export interface GetEmployeeDetailApiResponse {
  code: number;
  employee?: EmployeeDetail;
  message?: {
    code?: string;
    params?: string[];
  };
}

/**
 * Dữ liệu form nhập liệu nhân viên (ADM004) và hiển thị xác nhận (ADM005)
 */
export interface EmployeeFormData {
  employeeId?: number;
  employeeLoginId: string;
  departmentId: number | '' | undefined;
  departmentName?: string;
  employeeName: string;
  employeeNameKana: string;
  employeeBirthDate: string;
  employeeEmail: string;
  employeeTelephone: string;
  employeeLoginPassword?: string;
  employeeLoginPasswordConfirm?: string;
  certificationId?: number | '' | null;
  certificationName?: string | null;
  certificationStartDate?: string | null;
  certificationEndDate?: string | null;
  score?: number | string | null;
}

/**
 * Chứa thông báo lỗi của từng trường form ADM004
 */
export type EmployeeFormErrors = Partial<Record<keyof EmployeeFormData, string>>;

