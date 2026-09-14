/**
 * Copyright(C) 2026 Luvina
 * useAdm003.ts - Custom Hook for ADM003 (Employee Detail Page)
 * 08/09/2026 Pham Van Minh
 */

import { useState, useEffect, useCallback } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import axios from 'axios';
import {
  API_RESPONSE_CODES,
  APP_ROUTES,
  BUTTON_LABELS,
  COMPLETE_ACTION_TYPES,
  ERROR_MESSAGES,
  SYSTEM_MESSAGES,
  VALIDATION_MESSAGES,
  formatApiErrorMessage,
} from '@/constants';
import { EmployeeDetail } from '@/types/employee';
import { getEmployeeById, deleteEmployee } from '@/lib/api/employees';
import { setEditEmployeeId, clearEmployeeFormData, getEditEmployeeId, clearEditEmployeeId } from '@/lib/storage/employeeFormState';
import { setEmployeeCompleteAction } from '@/lib/storage/employeeCompleteState';

export interface UseAdm003Return {
  employee: EmployeeDetail | null;
  loading: boolean;
  deleting: boolean;
  apiError: string | null;
  hasCertification: boolean;
  handleNavigateToEdit: () => void;
  handleNavigateToList: () => void;
  handleDelete: () => Promise<void>;
}

/**
 * Custom hook quản lý logic lấy dữ liệu chi tiết, chỉnh sửa, xóa và quay lại cho màn hình ADM003.
 */
export function useAdm003(): UseAdm003Return {
  const router = useRouter();
  const searchParams = useSearchParams();

  const [employee, setEmployee] = useState<EmployeeDetail | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [deleting, setDeleting] = useState<boolean>(false);
  const [apiError, setApiError] = useState<string | null>(null);

  // 1. Xác định ID nhân viên từ query parameter hoặc từ sessionStorage (khi quay lại từ ADM004)
  const getParamId = useCallback((): number | null => {
    const idStr = searchParams ? searchParams.get('id') : null;
    if (idStr && /^\d+$/.test(idStr)) {
      return Number(idStr);
    }
    const editId = getEditEmployeeId();
    if (editId && /^\d+$/.test(String(editId))) {
      return Number(editId);
    }
    return null;
  }, [searchParams]);

  /**
   * [Thời điểm kích hoạt useEffect / gọi hàm tải dữ liệu]:
   * 1. Khi component mount lần đầu tiên (initial render khi người dùng truy cập màn hình ADM003).
   * 2. Khi `getParamId` thay đổi tham chiếu (xảy ra khi `searchParams` / URL query thay đổi, ví dụ đổi `id` nhân viên).
   * 
   * [Điều kiện gọi API `fetchDetail`]:
   * - Hàm `fetchDetail()` chỉ được gọi khi xác định được `employeeId` hợp lệ từ URL.
   * - Nếu không có `employeeId`, effect dừng lại ngay và điều hướng sang màn hình System Error.
   */
  useEffect(() => {
    let isMounted = true;
    const employeeId = getParamId();

    if (!employeeId) {
      // Nếu không có ID hợp lệ trên router, chuyển sang màn hình System Error theo mục 4.1 thiết kế
      if (isMounted) {
        router.push(APP_ROUTES.SYSTEM_ERROR);
        setLoading(false);
      }
      return;
    }

    const fetchDetail = async () => {
      try {
        setLoading(true);
        setApiError(null);
        const response = await getEmployeeById(employeeId);

        if (!isMounted) return;

        if (response && response.employee) {
          const emp = response.employee;
          // Format date yyyy/MM/dd
          setEmployee({
            ...emp,
            employeeBirthDate: emp.employeeBirthDate ? emp.employeeBirthDate.replace(/-/g, '/') : '',
            certificationStartDate: emp.certificationStartDate ? emp.certificationStartDate.replace(/-/g, '/') : '',
            certificationEndDate: emp.certificationEndDate ? emp.certificationEndDate.replace(/-/g, '/') : '',
          });
        } else {
          // Nếu API trả về lỗi hoặc ko tồn tại employee data di chuyển sang MH system error
          router.push(APP_ROUTES.SYSTEM_ERROR);
        }
      } catch {
        if (!isMounted) return;
        router.push(APP_ROUTES.SYSTEM_ERROR);
      } finally {
        if (isMounted) {
          setLoading(false);
        }
      }
    };

    fetchDetail();

    return () => {
      isMounted = false;
    };
  }, [getParamId]);

  // Kiểm tra nhân viên có chứng chỉ tiếng Nhật để hiển thị
  const hasCertification = Boolean(
    employee &&
      (employee.certificationName ||
        (employee.certificationId !== null &&
          employee.certificationId !== undefined &&
          Number(employee.certificationId) > 0) ||
        employee.certificationStartDate ||
        (employee.score !== null && employee.score !== undefined))
  );

  /**
   * Action Edit: Lưu ID nhân viên vào sessionStorage và chuyển sang màn hình ADM004 (Mode EDIT).
   */
  const handleNavigateToEdit = useCallback(() => {
    if (!employee) return;
    setEditEmployeeId(employee.employeeId);
    clearEmployeeFormData();
    router.push(APP_ROUTES.EMPLOYEE_EDIT);
  }, [employee, router]);

  /**
   * Action Cancel / Back: Quay về màn hình danh sách ADM002.
   * ADM002 sẽ tự động khôi phục điều kiện Search, Sort và số trang từ sessionStorage.
   */
  const handleNavigateToList = useCallback(() => {
    clearEditEmployeeId();
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  }, [router]);

  /**
   * Action Delete: Xác nhận qua MSG004 -> Gọi API DELETE -> Chuyển sang ADM006.
   */
  const handleDelete = useCallback(async () => {
    if (!employee || deleting) return;

    // 1. Hiển thị confirm dialog theo đặc tả mục 4.4
    const isConfirmed = typeof window !== 'undefined'
      ? window.confirm(SYSTEM_MESSAGES.MSG004_DELETE_CONFIRM)
      : true;

    if (!isConfirmed) return;

    try {
      setDeleting(true);
      setApiError(null);

      const response = await deleteEmployee(employee.employeeId);
      if (response && response.code === API_RESPONSE_CODES.SUCCESS) {
        setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.DELETE);
        router.push(APP_ROUTES.EMPLOYEE_COMPLETE);
      } else {
        const errCode = response?.message?.code;
        const params = response?.message?.params;
        setApiError(formatApiErrorMessage(errCode, params));
      }
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error.response?.data?.message?.code) {
        const respData = error.response.data as { message?: { code?: string; params?: string[] } };
        setApiError(formatApiErrorMessage(respData.message?.code, respData.message?.params));
      } else {
        setApiError(VALIDATION_MESSAGES.ER015_SYSTEM_ERROR);
      }
    } finally {
      setDeleting(false);
    }
  }, [employee, deleting, router]);

  return {
    employee,
    loading,
    deleting,
    apiError,
    hasCertification,
    handleNavigateToEdit,
    handleNavigateToList,
    handleDelete,
  };
}
