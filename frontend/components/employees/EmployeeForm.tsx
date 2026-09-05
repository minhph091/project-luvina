'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeeForm.tsx - Main Form Component for ADM004 (Employee Create/Edit)
 * 04/09/2026 Pham Van Minh
 */

import React from 'react';
import { DepartmentItem } from '@/types/department';
import { CertificationItem } from '@/types/certification';
import { EmployeeFormData, EmployeeFormErrors, EmployeeFormMode } from '@/types/employee';
import { BUTTON_LABELS, PAGE_TITLES } from '@/constants';
import EmployeeBasicInfoForm from './EmployeeBasicInfoForm';
import EmployeeJapaneseForm from './EmployeeJapaneseForm';

interface EmployeeFormProps {
  mode: EmployeeFormMode;
  formData: EmployeeFormData;
  errors: EmployeeFormErrors;
  departments: DepartmentItem[];
  certifications: CertificationItem[];
  isCertSelected: boolean;
  birthDateObj: Date | null;
  startDateObj: Date | null;
  endDateObj: Date | null;
  apiError: string | null;
  loading: boolean;
  onFieldChange: (
    fieldOrUpdates: keyof EmployeeFormData | Partial<EmployeeFormData>,
    value?: any
  ) => void;
  onDateChange: (
    field: 'employeeBirthDate' | 'certificationStartDate' | 'certificationEndDate',
    date: Date | null
  ) => void;
  onBlur: (field: keyof EmployeeFormData) => void;
  onSubmit: (e?: React.FormEvent<HTMLFormElement>) => void;
  onBack: () => void;
}

/**
 * Component Form tổng thể màn hình ADM004 (Thêm mới / Chỉnh sửa nhân viên).
 */
export const EmployeeForm: React.FC<EmployeeFormProps> = ({
  mode,
  formData,
  errors,
  departments,
  certifications,
  isCertSelected,
  birthDateObj,
  startDateObj,
  endDateObj,
  apiError,
  loading,
  onFieldChange,
  onDateChange,
  onBlur,
  onSubmit,
  onBack,
}) => {
  const pageTitle =
    mode === 'ADD' ? PAGE_TITLES.ADD_EMPLOYEE : PAGE_TITLES.EDIT_EMPLOYEE;

  return (
    <div className="row">
      <form
        id="employee-form"
        className="c-form box-shadow"
        onSubmit={(e) => {
          e.preventDefault();
          onSubmit(e);
        }}
        noValidate
      >
        <ul>
          {/* Tiêu đề màn hình */}
          <li className="title">{pageTitle}</li>

          {/* Vùng hiển thị lỗi hệ thống / API nếu có */}
          {apiError && (
            <li className="box-err">
              <div id="api-error-box" className="box-err-content">
                {apiError}
              </div>
            </li>
          )}

          {/* Khối thông tin cơ bản */}
          <EmployeeBasicInfoForm
            formData={formData}
            errors={errors}
            mode={mode}
            departments={departments}
            birthDateObj={birthDateObj}
            onFieldChange={onFieldChange}
            onDateChange={onDateChange}
            onBlur={onBlur}
          />

          {/* Khối trình độ tiếng Nhật */}
          <EmployeeJapaneseForm
            formData={formData}
            errors={errors}
            certifications={certifications}
            isCertSelected={isCertSelected}
            startDateObj={startDateObj}
            endDateObj={endDateObj}
            onFieldChange={onFieldChange}
            onDateChange={onDateChange}
            onBlur={onBlur}
          />

          {/* Nhóm nút Xác nhận (確認) & Quay lại (戻る) */}
          <li className="form-group row d-flex">
            <div className="btn-group col-sm col-sm-10 ml">
              <button
                id="btn-confirm"
                type="submit"
                className="btn btn-primary btn-sm"
                disabled={loading}
              >
                {BUTTON_LABELS.CONFIRM}
              </button>
              <button
                id="btn-back"
                type="button"
                className="btn btn-secondary btn-sm"
                onClick={onBack}
              >
                {BUTTON_LABELS.BACK}
              </button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  );
};

export default EmployeeForm;
