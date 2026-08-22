/**
 * Copyright(C) 2026 Luvina
 * useDepartments.ts - Custom Hook for Department Data
 * 21/08/2026 Pham Van Minh
 */

import { useEffect, useState } from 'react';
import { getDepartments } from '@/lib/api/departments';
import { DepartmentItem } from '@/types/department';
import { ERROR_MESSAGES } from '@/constants';

interface UseDepartmentsReturn {
  departments: DepartmentItem[];
  loadingDepartments: boolean;
  departmentErrorMessage: string | null;
}

/**
 * Custom Hook quản lý việc tải danh sách phòng ban từ API.
 *
 * @returns Object chứa danh sách departments, trạng thái loading và error message.
 */
export function useDepartments(): UseDepartmentsReturn {
  const [departments, setDepartments] = useState<DepartmentItem[]>([]);
  const [loadingDepartments, setLoadingDepartments] = useState(false);
  const [departmentErrorMessage, setDepartmentErrorMessage] = useState<string | null>(null);

  useEffect(() => {
    let isMounted = true;

    async function fetchDepartmentList() {
      setLoadingDepartments(true);
      setDepartmentErrorMessage(null);
      try {
        const response = await getDepartments();
        if (isMounted) {
          setDepartments(response.departments || []);
        }
      } catch {
        if (isMounted) {
          setDepartmentErrorMessage(ERROR_MESSAGES.FETCH_DEPARTMENTS_FAILED);
        }
      } finally {
        if (isMounted) {
          setLoadingDepartments(false);
        }
      }
    }

    fetchDepartmentList();

    return () => {
      isMounted = false;
    };
  }, []);

  return {
    departments,
    loadingDepartments,
    departmentErrorMessage,
  };
}
