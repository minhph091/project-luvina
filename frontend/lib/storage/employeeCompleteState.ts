/**
 * Copyright(C) 2026 Luvina
 * employeeCompleteState.ts - SessionStorage Management for Employee Action Complete (ADM006)
 * 07/09/2026 Pham Van Minh
 */

import { STORAGE_KEYS, COMPLETE_ACTION_TYPES, CompleteActionType } from '@/constants';

/**
 * Lưu loại hành động hoàn tất vào sessionStorage (khi di chuyển sang ADM006 Complete).
 *
 * @param action Loại hành động (ADD | EDIT | DELETE)
 */
export function setEmployeeCompleteAction(action: CompleteActionType): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.setItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION, action);
  } catch {
    // Ignore storage quota or disabled storage errors
  }
}

/**
 * Lấy loại hành động hoàn tất từ sessionStorage.
 *
 * @returns CompleteActionType hoặc null nếu không tồn tại / không hợp lệ
 */
export function getEmployeeCompleteAction(): CompleteActionType | null {
  if (typeof window === 'undefined') return null;
  try {
    const action = sessionStorage.getItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION);
    if (
      action === COMPLETE_ACTION_TYPES.ADD ||
      action === COMPLETE_ACTION_TYPES.EDIT ||
      action === COMPLETE_ACTION_TYPES.DELETE
    ) {
      return action;
    }
    return null;
  } catch {
    return null;
  }
}

/**
 * Xóa loại hành động hoàn tất trong sessionStorage.
 */
export function clearEmployeeCompleteAction(): void {
  if (typeof window === 'undefined') return;
  try {
    sessionStorage.removeItem(STORAGE_KEYS.EMPLOYEE_COMPLETE_ACTION);
  } catch {
    // Ignore error
  }
}
