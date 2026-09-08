/**
 * Copyright(C) 2026 Luvina
 * useAdm005.ts - Custom Hook for ADM005 (Employee Confirmation Page)
 * 05/09/2026 Pham Van Minh
 */

import { useState, useEffect, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import axios from 'axios';
import { APP_ROUTES, formatApiErrorMessage, VALIDATION_MESSAGES, COMPLETE_ACTION_TYPES } from '@/constants';
import { EmployeeFormData, EmployeeFormMode } from '@/types/employee';
import { getEmployeeFormData, getEditEmployeeId, clearEmployeeFormData } from '@/lib/storage/employeeFormState';
import { setEmployeeCompleteAction } from '@/lib/storage/employeeCompleteState';
import { addEmployee } from '@/lib/api/employees';

export interface UseAdm005Return {
  mode: EmployeeFormMode;
  formData: EmployeeFormData | null;
  loading: boolean;
  submitting: boolean;
  apiError: string | null;
  hasCertification: boolean;
  handleConfirmSubmit: () => Promise<void>;
  handleNavigateToEdit: () => void;
}

/**
 * Custom Hook quản lý dữ liệu hiển thị, gọi API lưu và điều hướng cho màn hình ADM005 Xác nhận thông tin nhân viên.
 */
export function useAdm005(): UseAdm005Return {
  const router = useRouter();

  const [mode, setMode] = useState<EmployeeFormMode>('ADD');
  const [formData, setFormData] = useState<EmployeeFormData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [submitting, setSubmitting] = useState<boolean>(false);
  const [apiError, setApiError] = useState<string | null>(null);

  useEffect(() => {
    const editId = getEditEmployeeId();
    setMode(editId ? 'EDIT' : 'ADD');

    const savedData = getEmployeeFormData();
    if (!savedData) {
      // Nếu không có dữ liệu form (ví dụ truy cập trực tiếp URL), điều hướng về màn hình edit/add
      router.push(APP_ROUTES.EMPLOYEE_EDIT);
      return;
    }

    setFormData(savedData);
    setLoading(false);
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  // Kiểm tra xem nhân viên có thông tin chứng chỉ tiếng Nhật hợp lệ không
  const hasCertification = Boolean(
    formData &&
      formData.certificationId !== '' &&
      formData.certificationId !== null &&
      formData.certificationId !== undefined &&
      Number(formData.certificationId) > 0
  );

  /**
   * Xử lý xác nhận lưu thông tin nhân viên (Nút OK)
   * Gọi API backend tương ứng (thêm mới hoặc cập nhật).
   */
  const handleConfirmSubmit = useCallback(async () => {
    if (!formData || submitting) {
      return;
    }

    try {
      setSubmitting(true);
      setApiError(null);

      if (mode === 'ADD') {
        const response = await addEmployee(formData);
        if (response.code === 200) {
          clearEmployeeFormData();
          setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
          router.push(APP_ROUTES.EMPLOYEE_COMPLETE);
        } else {
          const errCode = response.message?.code;
          const params = response.message?.params;
          setApiError(formatApiErrorMessage(errCode, params));
        }
      } else {
        // Mode EDIT (dự phòng cho chức năng update employee khi triển khai)
        setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.EDIT);
        router.push(APP_ROUTES.EMPLOYEE_COMPLETE);
      }
    } catch (error: unknown) {
      if (axios.isAxiosError(error) && error.response?.data?.message?.code) {
        const respData = error.response.data as { message?: { code?: string; params?: string[] } };
        setApiError(formatApiErrorMessage(respData.message?.code, respData.message?.params));
      } else {
        setApiError(VALIDATION_MESSAGES.ER015_SYSTEM_ERROR);
      }
    } finally {
      setSubmitting(false);
    }
  }, [formData, mode, submitting, router]);

  /**
   * Xử lý quay lại màn hình nhập liệu ADM004 (Nút 戻る)
   */
  const handleNavigateToEdit = useCallback(() => {
    // Quay về ADM004 (dữ liệu trong sessionStorage vẫn được giữ nguyên để ADM004 khôi phục)
    router.push(APP_ROUTES.EMPLOYEE_EDIT);
  }, [router]);

  return {
    mode,
    formData,
    loading,
    submitting,
    apiError,
    hasCertification,
    handleConfirmSubmit,
    handleNavigateToEdit,
  };
}

