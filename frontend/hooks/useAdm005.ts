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
import { getEmployeeFormData, getEditEmployeeId, clearEmployeeFormData, clearInitialCertData } from '@/lib/storage/employeeFormState';
import { setEmployeeCompleteAction } from '@/lib/storage/employeeCompleteState';
import { getEmployeeById, addEmployee, updateEmployee } from '@/lib/api/employees';

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

  /**
   * [Thời điểm kích hoạt useEffect / kiểm tra & khởi tạo dữ liệu xác nhận]:
   * - Kích hoạt 1 lần duy nhất khi màn hình xác nhận ADM005 mount lần đầu tiên (initial render).
   * - Luồng xử lý theo tài liệu thiết kế (Mục 6.1):
   *   + Xác định mode thao tác (`ADD` hoặc `EDIT`) từ storage (`getEditEmployeeId()`).
   *   + Lấy dữ liệu tạm `savedData` vừa nhập từ màn hình ADM004.
   *   + Nếu không có dữ liệu (truy cập URL trực tiếp trái phép): Tự động redirect người dùng quay về màn hình nhập liệu ADM004.
   *   + Nếu là mode `EDIT`: Gọi API `getEmployeeById(editId)` tương ứng. Nếu API trả về lỗi hoặc không tồn tại employee data thì chuyển sang MH System Error.
   *   + Binding data từ MH edit/add gửi sang lên màn hình xác nhận.
   */
  useEffect(() => {
    let isMounted = true;

    async function initConfirm() {
      const editId = getEditEmployeeId();
      const currentMode: EmployeeFormMode = editId ? 'EDIT' : 'ADD';
      if (isMounted) {
        setMode(currentMode);
      }

      const savedData = getEmployeeFormData();
      if (!savedData) {
        // Nếu không có dữ liệu form (ví dụ truy cập trực tiếp URL), điều hướng về màn hình edit/add
        router.push(APP_ROUTES.EMPLOYEE_EDIT);
        return;
      }

      // Nếu là mode EDIT: Gọi API get employee tương ứng với ID để xác thực tồn tại
      if (currentMode === 'EDIT' && editId) {
        try {
          const response = await getEmployeeById(editId);
          if (!response || Number(response.code) !== 200 || !response.employee) {
            if (isMounted) {
              router.push(APP_ROUTES.SYSTEM_ERROR);
            }
            return;
          }
        } catch {
          if (isMounted) {
            router.push(APP_ROUTES.SYSTEM_ERROR);
          }
          return;
        }
      }

      if (isMounted) {
        setFormData(savedData);
        setLoading(false);
      }
    }

    initConfirm();

    return () => {
      isMounted = false;
    };
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
          clearInitialCertData();
          setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.ADD);
          router.push(APP_ROUTES.EMPLOYEE_COMPLETE);
        } else {
          const errCode = response.message?.code;
          const params = response.message?.params;
          setApiError(formatApiErrorMessage(errCode, params));
        }
      } else {
        // Mode EDIT
        const response = await updateEmployee(formData);
        if (response.code === 200) {
          clearEmployeeFormData();
          clearInitialCertData();
          setEmployeeCompleteAction(COMPLETE_ACTION_TYPES.EDIT);
          router.push(APP_ROUTES.EMPLOYEE_COMPLETE);
        } else {
          const errCode = response.message?.code;
          const params = response.message?.params;
          setApiError(formatApiErrorMessage(errCode, params));
        }
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

