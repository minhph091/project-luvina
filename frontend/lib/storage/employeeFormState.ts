/**
 * Copyright(C) 2026 Luvina
 * employeeFormState.ts - SessionStorage Management for Employee Form Data & Edit Mode
 * 04/09/2026 Pham Van Minh
 */

import { STORAGE_KEYS } from '@/constants';
import { EmployeeFormData } from '@/types/employee';

/**
 * Lưu dữ liệu form nhập liệu nhân viên vào sessionStorage (khi di chuyển sang ADM005 Confirm).
 *
 * @param data Dữ liệu form nhập liệu
 */
export function saveEmployeeFormData(data: EmployeeFormData): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.setItem(STORAGE_KEYS.EMPLOYEE_FORM_DATA, JSON.stringify(data));
  } catch {
    // Ignore storage quota or disabled storage errors
  }
}

/**
 * Lấy dữ liệu form nhập liệu nhân viên đã lưu từ sessionStorage.
 *
 * @returns Dữ liệu form hoặc null nếu không tồn tại / không hợp lệ
 */
export function getEmployeeFormData(): EmployeeFormData | null {
  if (typeof window === 'undefined') return null;
  try {
    const raw = sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_FORM_DATA);
    if (!raw) return null;
    const parsed = JSON.parse(raw) as EmployeeFormData;
    if (!parsed || typeof parsed !== 'object') return null;
    return parsed;
  } catch {
    return null;
  }
}

/**
 * Xóa dữ liệu form nhân viên trong sessionStorage.
 */
export function clearEmployeeFormData(): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.removeItem(STORAGE_KEYS.EMPLOYEE_FORM_DATA);
  } catch {
    // Ignore error
  }
}

/**
 * Lưu ID nhân viên đang chỉnh sửa vào sessionStorage (Mode EDIT).
 *
 * @param id ID của nhân viên cần sửa
 */
export function setEditEmployeeId(id: number | string): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.setItem(STORAGE_KEYS.EDIT_EMPLOYEE_ID, String(id));
  } catch {
    // Ignore error
  }
}

/**
 * Lấy ID nhân viên đang chỉnh sửa từ sessionStorage.
 *
 * @returns ID nhân viên dạng số hoặc null nếu ở Mode ADD (không có ID)
 */
export function getEditEmployeeId(): number | null {
  if (typeof window === 'undefined') return null;
  try {
    const raw = sessionStorage.getItem(STORAGE_KEYS.EDIT_EMPLOYEE_ID);
    if (!raw || raw.trim() === '') return null;
    const parsed = Number(raw);
    return isNaN(parsed) ? null : parsed;
  } catch {
    return null;
  }
}

/**
 * Xóa ID nhân viên chỉnh sửa trong sessionStorage (khi chuyển về Mode ADD).
 */
export function clearEditEmployeeId(): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.removeItem(STORAGE_KEYS.EDIT_EMPLOYEE_ID);
  } catch {
    // Ignore error
  }
}
