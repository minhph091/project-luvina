/**
 * Copyright(C) 2026 Luvina
 * employeeSearchState.ts - SessionStorage Management for Employee Search & Sort State
 * 25/08/2026 Pham Van Minh
 */

import { STORAGE_KEYS } from '@/constants';
import { SortState } from '@/types/employee';

export interface EmployeeSearchState {
  currentPage: number;
  searchName: string;
  searchDepartmentId: number | undefined;
  appliedName: string;
  appliedDepartmentId: number | undefined;
  sort: SortState;
  activeSortColumn: keyof SortState;
}

/**
 * Lưu trạng thái tìm kiếm, phân trang và sắp xếp của danh sách nhân viên vào sessionStorage.
 *
 * @param state Trạng thái tìm kiếm và sắp xếp hiện tại
 */
export function saveEmployeeSearchState(state: EmployeeSearchState): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.setItem(STORAGE_KEYS.EMPLOYEE_SEARCH_STATE, JSON.stringify(state));
  } catch {
    // Ignore storage quota or disabled storage errors
  }
}

/**
 * Lấy trạng thái tìm kiếm, phân trang và sắp xếp đã lưu từ sessionStorage.
 *
 * @returns Trạng thái đã lưu hoặc null nếu không tồn tại / không hợp lệ
 */
export function getEmployeeSearchState(): EmployeeSearchState | null {
  if (typeof window === 'undefined') return null;
  try {
    const raw = sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_SEARCH_STATE);
    if (!raw) return null;
    return JSON.parse(raw) as EmployeeSearchState;
  } catch {
    return null;
  }
}

/**
 * Xóa trạng thái tìm kiếm đã lưu trong sessionStorage.
 */
export function clearEmployeeSearchState(): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.removeItem(STORAGE_KEYS.EMPLOYEE_SEARCH_STATE);
  } catch {
    // Ignore error
  }
}
