/**
 * Copyright(C) 2026 Luvina
 * department.ts - Type Definitions for Department
 * 21/08/2026 Pham Van Minh
 */

/**
 * Thông tin một phòng ban.
 */
export interface DepartmentItem {
  departmentId: number;
  departmentName: string;
}

/**
 * Phản hồi từ API lấy danh sách phòng ban.
 */
export interface GetDepartmentsApiResponse {
  code: number;
  departments: DepartmentItem[];
}
