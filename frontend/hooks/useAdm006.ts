/**
 * Copyright(C) 2026 Luvina
 * useAdm006.ts - Custom Hook for ADM006 (Employee Action Complete Page)
 * 09/09/2026 Pham Van Minh
 */

import { useCallback, useSyncExternalStore } from 'react';
import { useRouter } from 'next/navigation';
import { APP_ROUTES, COMPLETE_ACTION_TYPES, SYSTEM_MESSAGES } from '@/constants';
import { clearEmployeeSearchState } from '@/lib/storage/employeeSearchState';
import {
  getEmployeeCompleteAction,
  clearEmployeeCompleteAction,
} from '@/lib/storage/employeeCompleteState';

const subscribe = () => () => {};

function getSnapshot(): string {
  const action = getEmployeeCompleteAction();
  if (action === COMPLETE_ACTION_TYPES.EDIT) {
    return SYSTEM_MESSAGES.MSG002_USER_UPDATE_COMPLETE;
  }
  if (action === COMPLETE_ACTION_TYPES.DELETE) {
    return SYSTEM_MESSAGES.MSG003_USER_DELETE_COMPLETE;
  }
  return SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE;
}

function getServerSnapshot(): string {
  return SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE;
}

export interface UseAdm006Return {
  completeMessage: string;
  handleNavigateToList: () => void;
}

/**
 * Custom hook quản lý thông báo hoàn thành thao tác (thêm/sửa/xóa)
 * và điều hướng quay lại danh sách nhân viên (ADM002).
 */
export function useAdm006(): UseAdm006Return {
  const router = useRouter();
  const completeMessage = useSyncExternalStore(subscribe, getSnapshot, getServerSnapshot);

  /**
   * Dọn dẹp trạng thái hoàn tất, reset bộ lọc tìm kiếm và quay về trang ADM002.
   */
  const handleNavigateToList = useCallback(() => {
    clearEmployeeCompleteAction();
    clearEmployeeSearchState();
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  }, [router]);

  return {
    completeMessage,
    handleNavigateToList,
  };
}
