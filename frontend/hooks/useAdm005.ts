/**
 * Copyright(C) 2026 Luvina
 * useAdm005.ts - Custom Hook for ADM005 (Employee Confirmation Page)
 * 05/09/2026 Pham Van Minh
 */

import { useState, useEffect, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { APP_ROUTES } from '@/constants';
import { EmployeeFormData, EmployeeFormMode } from '@/types/employee';
import { getEmployeeFormData, getEditEmployeeId } from '@/lib/storage/employeeFormState';

export interface UseAdm005Return {
  mode: EmployeeFormMode;
  formData: EmployeeFormData | null;
  loading: boolean;
  hasCertification: boolean;
  handleConfirmSubmit: () => void;
  handleNavigateToEdit: () => void;
}

/**
 * Custom Hook quản lý dữ liệu hiển thị và điều hướng cho màn hình ADM005 Xác nhận thông tin nhân viên.
 */
export function useAdm005(): UseAdm005Return {
  const router = useRouter();

  const [mode, setMode] = useState<EmployeeFormMode>('ADD');
  const [formData, setFormData] = useState<EmployeeFormData | null>(null);
  const [loading, setLoading] = useState<boolean>(true);

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
  }, [router]);

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
   */
  const handleConfirmSubmit = useCallback(() => {
    // Điều hướng sang màn hình hoàn tất ADM006
    router.push(APP_ROUTES.EMPLOYEE_COMPLETE);
  }, [router]);

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
    hasCertification,
    handleConfirmSubmit,
    handleNavigateToEdit,
  };
}
