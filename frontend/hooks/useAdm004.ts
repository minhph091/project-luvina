/**
 * Copyright(C) 2026 Luvina
 * useAdm004.ts - Custom Hook for ADM004 (Employee Create/Edit Form Management)
 * 04/09/2026 Pham Van Minh
 */

import { useState, useEffect, useCallback } from 'react';
import { useRouter } from 'next/navigation';
import { APP_ROUTES } from '@/constants';
import { EmployeeFormData, EmployeeFormErrors, EmployeeFormMode } from '@/types/employee';
import { getEmployeeById } from '@/lib/api/employees';
import {
  getEditEmployeeId,
  getEmployeeFormData,
  saveEmployeeFormData,
  clearEmployeeFormData,
} from '@/lib/storage/employeeFormState';
import { validateField, validateEmployeeForm } from '@/lib/validation/employeeForm';

const INITIAL_FORM_DATA: EmployeeFormData = {
  employeeLoginId: '',
  departmentId: '',
  employeeName: '',
  employeeNameKana: '',
  employeeBirthDate: '',
  employeeEmail: '',
  employeeTelephone: '',
  employeeLoginPassword: '',
  employeeLoginPasswordConfirm: '',
  certificationId: '',
  certificationStartDate: '',
  certificationEndDate: '',
  score: '',
};

/**
 * Format Date object to "yyyy/MM/dd" string
 */
export function formatDateToSlashString(date: Date | null | undefined): string {
  if (!date || isNaN(date.getTime())) return '';
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');
  return `${year}/${month}/${day}`;
}

/**
 * Parse string date "yyyy-MM-dd" or "yyyy/MM/dd" to Date object
 */
export function parseStringToDate(str: string | null | undefined): Date | null {
  if (!str || str.trim() === '') return null;
  const normalized = str.trim().replace(/-/g, '/');
  const parts = normalized.split('/').map(Number);
  if (parts.length !== 3) return null;
  const [year, month, day] = parts;
  if (isNaN(year) || isNaN(month) || isNaN(day)) return null;
  const date = new Date(year, month - 1, day);
  return isNaN(date.getTime()) ? null : date;
}

export interface UseAdm004Return {
  mode: EmployeeFormMode;
  formData: EmployeeFormData;
  errors: EmployeeFormErrors;
  touched: Record<string, boolean>;
  loading: boolean;
  apiError: string | null;
  isCertSelected: boolean;
  birthDateObj: Date | null;
  startDateObj: Date | null;
  endDateObj: Date | null;
  handleFieldChange: (field: keyof EmployeeFormData, value: any) => void;
  handleDateChange: (
    field: 'employeeBirthDate' | 'certificationStartDate' | 'certificationEndDate',
    date: Date | null
  ) => void;
  handleBlur: (field: keyof EmployeeFormData) => void;
  handleSubmit: (e?: React.FormEvent<HTMLFormElement>) => boolean;
  handleBack: () => void;
}

/**
 * Custom Hook quản lý State, Validation và Navigation cho màn hình ADM004.
 */
export function useAdm004(): UseAdm004Return {
  const router = useRouter();

  const [mode, setMode] = useState<EmployeeFormMode>('ADD');
  const [formData, setFormData] = useState<EmployeeFormData>(INITIAL_FORM_DATA);
  const [errors, setErrors] = useState<EmployeeFormErrors>({});
  const [touched, setTouched] = useState<Record<string, boolean>>({});
  const [loading, setLoading] = useState<boolean>(true);
  const [apiError, setApiError] = useState<string | null>(null);

  // Khởi tạo mode và form data khi load trang
  useEffect(() => {
    let isMounted = true;

    async function initForm() {
      setLoading(true);
      setApiError(null);

      const editId = getEditEmployeeId();
      const currentMode: EmployeeFormMode = editId ? 'EDIT' : 'ADD';
      if (isMounted) {
        setMode(currentMode);
      }

      // 1. Kiểm tra xem có dữ liệu tạm lưu từ ADM005 quay lại không
      const savedFormData = getEmployeeFormData();
      if (savedFormData) {
        if (isMounted) {
          setFormData(savedFormData);
          setLoading(false);
        }
        return;
      }

      // 2. Nếu là Mode EDIT và chưa có savedFormData, tải dữ liệu nhân viên từ API
      if (currentMode === 'EDIT' && editId) {
        try {
          const response = await getEmployeeById(editId);
          if (isMounted) {
            if (response && response.employee) {
              const emp = response.employee;
              setFormData({
                employeeId: emp.employeeId,
                employeeLoginId: emp.employeeLoginId || '',
                departmentId: emp.departmentId ?? '',
                employeeName: emp.employeeName || '',
                employeeNameKana: emp.employeeNameKana || '',
                employeeBirthDate: emp.employeeBirthDate ? emp.employeeBirthDate.replace(/-/g, '/') : '',
                employeeEmail: emp.employeeEmail || '',
                employeeTelephone: emp.employeeTelephone || '',
                employeeLoginPassword: '',
                employeeLoginPasswordConfirm: '',
                certificationId: emp.certificationId ?? '',
                certificationStartDate: emp.certificationStartDate
                  ? emp.certificationStartDate.replace(/-/g, '/')
                  : '',
                certificationEndDate: emp.certificationEndDate
                  ? emp.certificationEndDate.replace(/-/g, '/')
                  : '',
                score: emp.score !== null && emp.score !== undefined ? String(emp.score) : '',
              });
            } else {
              setApiError('従業員情報を取得できませんでした。');
            }
          }
        } catch {
          if (isMounted) {
            setApiError('従業員情報の取得に失敗しました。');
          }
        } finally {
          if (isMounted) {
            setLoading(false);
          }
        }
      } else {
        // Mode ADD: Form rỗng
        if (isMounted) {
          setFormData(INITIAL_FORM_DATA);
          setLoading(false);
        }
      }
    }

    initForm();

    return () => {
      isMounted = false;
    };
  }, []);

  // Kiểm tra xem chứng chỉ có đang được chọn không
  const isCertSelected = Boolean(
    formData.certificationId !== undefined &&
      formData.certificationId !== null &&
      formData.certificationId !== '' &&
      Number(formData.certificationId) > 0
  );

  // Date objects phục vụ DatePicker
  const birthDateObj = parseStringToDate(formData.employeeBirthDate);
  const startDateObj = parseStringToDate(formData.certificationStartDate);
  const endDateObj = parseStringToDate(formData.certificationEndDate);

  /**
   * Xử lý thay đổi giá trị một trường input / select
   */
  const handleFieldChange = useCallback(
    (field: keyof EmployeeFormData, value: any) => {
      setFormData((prev) => {
        const next = { ...prev, [field]: value };

        // Xử lý đặc biệt khi đổi certificationId
        if (field === 'certificationId') {
          const hasCert = value !== '' && value !== null && value !== undefined && Number(value) > 0;
          if (!hasCert) {
            // Disable & clear 3 trường tiếng Nhật
            next.certificationStartDate = '';
            next.certificationEndDate = '';
            next.score = '';
            // Clear lỗi của 3 trường tiếng Nhật
            setErrors((prevErr) => {
              const nextErr = { ...prevErr };
              delete nextErr.certificationStartDate;
              delete nextErr.certificationEndDate;
              delete nextErr.score;
              return nextErr;
            });
          }
        }

        return next;
      });

      // Nếu trường này đã từng có lỗi, validate lại để xóa lỗi ngay khi hợp lệ
      if (errors[field]) {
        setFormData((latest) => {
          const err = validateField(field, latest, mode);
          setErrors((prevErr) => ({
            ...prevErr,
            [field]: err || undefined,
          }));
          return latest;
        });
      }
    },
    [errors, mode]
  );

  /**
   * Xử lý chọn ngày từ DatePicker
   */
  const handleDateChange = useCallback(
    (
      field: 'employeeBirthDate' | 'certificationStartDate' | 'certificationEndDate',
      date: Date | null
    ) => {
      const dateStr = formatDateToSlashString(date);
      handleFieldChange(field, dateStr);

      // Re-validate date field
      setFormData((prev) => {
        const updated = { ...prev, [field]: dateStr };
        const err = validateField(field, updated, mode);
        setErrors((prevErr) => {
          const nextErr = { ...prevErr, [field]: err || undefined };
          // Nếu đổi startDate hoặc endDate thì re-validate endDate
          if (field === 'certificationStartDate' || field === 'certificationEndDate') {
            const endErr = validateField('certificationEndDate', updated, mode);
            nextErr.certificationEndDate = endErr || undefined;
          }
          return nextErr;
        });
        return updated;
      });
    },
    [handleFieldChange, mode]
  );

  /**
   * Xử lý sự kiện onBlur: Validate realtime từng trường
   */
  const handleBlur = useCallback(
    (field: keyof EmployeeFormData) => {
      setTouched((prev) => ({ ...prev, [field]: true }));

      const err = validateField(field, formData, mode);
      setErrors((prev) => {
        const next = { ...prev, [field]: err || undefined };

        // Khi blur password hoặc confirm password, validate lại cả 2 nếu confirm đã touched
        if (field === 'employeeLoginPassword' || field === 'employeeLoginPasswordConfirm') {
          const confirmErr = validateField('employeeLoginPasswordConfirm', formData, mode);
          next.employeeLoginPasswordConfirm = confirmErr || undefined;
        }

        // Khi blur startDate hoặc endDate, re-validate endDate để kiểm tra ER012
        if (field === 'certificationStartDate' || field === 'certificationEndDate') {
          const endErr = validateField('certificationEndDate', formData, mode);
          next.certificationEndDate = endErr || undefined;
        }

        return next;
      });
    },
    [formData, mode]
  );

  /**
   * Xử lý submit form (Xác nhận / 確認)
   */
  const handleSubmit = useCallback(
    (e?: React.FormEvent<HTMLFormElement>): boolean => {
      if (e) e.preventDefault();

      const { isValid, errors: validationErrors } = validateEmployeeForm(formData, mode);

      if (!isValid) {
        setErrors(validationErrors);
        // Mark all fields as touched to highlight errors
        const allTouched: Record<string, boolean> = {};
        for (const key of Object.keys(formData)) {
          allTouched[key] = true;
        }
        setTouched(allTouched);
        return false;
      }

      // Lưu formData vào sessionStorage cho ADM005
      saveEmployeeFormData(formData);

      // Chuyển hướng sang màn hình ADM005 Confirm
      router.push(APP_ROUTES.EMPLOYEE_CONFIRM);
      return true;
    },
    [formData, mode, router]
  );

  /**
   * Xử lý nút Quay lại (戻る)
   */
  const handleBack = useCallback(() => {
    // Xóa form data tạm
    clearEmployeeFormData();

    if (mode === 'EDIT') {
      router.push(APP_ROUTES.EMPLOYEE_DETAIL);
    } else {
      // Mode ADD: quay về ADM002 (trạng thái search/sort/page tự động được giữ trong sessionStorage)
      router.push(APP_ROUTES.EMPLOYEE_LIST);
    }
  }, [mode, router]);

  return {
    mode,
    formData,
    errors,
    touched,
    loading,
    apiError,
    isCertSelected,
    birthDateObj,
    startDateObj,
    endDateObj,
    handleFieldChange,
    handleDateChange,
    handleBlur,
    handleSubmit,
    handleBack,
  };
}
