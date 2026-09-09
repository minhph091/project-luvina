'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM004: Màn hình Thêm mới / Chỉnh sửa nhân viên (会員情報追加 / 会員情報編集)
 * 04/09/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useDepartments } from '@/hooks/useDepartments';
import { useCertifications } from '@/hooks/useCertifications';
import { useAdm004 } from '@/hooks/useAdm004';
import EmployeeForm from '@/components/employees/EmployeeForm';

/**
 * Page Component của ADM004.
 * Kết nối Hook xác thực, master data (departments, certifications) và logic form của useAdm004.
 */
export default function EmployeeEditPage() {
  // Bảo vệ route: kiểm tra token xác thực
  useAuth();

  // Master data: danh sách phòng ban & chứng chỉ
  const { departments, departmentErrorMessage } = useDepartments();
  const { certifications, certificationErrorMessage } = useCertifications();

  // Custom hook quản lý form ADM004
  const {
    mode,
    formData,
    errors,
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
  } = useAdm004();

  // Tổng hợp lỗi API / Master data nếu có
  const displayApiError = apiError || departmentErrorMessage || certificationErrorMessage;

  return (
    <EmployeeForm
      mode={mode}
      formData={formData}
      errors={errors}
      departments={departments}
      certifications={certifications}
      isCertSelected={isCertSelected}
      birthDateObj={birthDateObj}
      startDateObj={startDateObj}
      endDateObj={endDateObj}
      apiError={displayApiError}
      loading={loading}
      onFieldChange={handleFieldChange}
      onDateChange={handleDateChange}
      onBlur={handleBlur}
      onSubmit={handleSubmit}
      onBack={handleBack}
    />
  );
}
