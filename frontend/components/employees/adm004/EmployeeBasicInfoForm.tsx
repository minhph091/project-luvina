'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeeBasicInfoForm.tsx - Sub-component for Basic Employee Information Form (ADM004)
 * 04/09/2026 Pham Van Minh
 */

import React, { useEffect, useRef } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { DepartmentItem } from '@/types/department';
import { EmployeeFormData, EmployeeFormErrors, EmployeeFormMode } from '@/types/employee';
import { COMMON_LABELS, FIELD_LABELS } from '@/constants';

interface EmployeeBasicInfoFormProps {
  formData: EmployeeFormData;
  errors: EmployeeFormErrors;
  mode: EmployeeFormMode;
  departments: DepartmentItem[];
  birthDateObj: Date | null;
  onFieldChange: (
    fieldOrUpdates: keyof EmployeeFormData | Partial<EmployeeFormData>,
    value?: EmployeeFormData[keyof EmployeeFormData]
  ) => void;
  onDateChange: (field: 'employeeBirthDate', date: Date | null) => void;
  onBlur: (field: keyof EmployeeFormData) => void;
}

/**
 * Component hiển thị và chỉnh sửa phần Thông tin cơ bản của nhân viên.
 */
export const EmployeeBasicInfoForm: React.FC<EmployeeBasicInfoFormProps> = ({
  formData,
  errors,
  mode,
  departments,
  birthDateObj,
  onFieldChange,
  onDateChange,
  onBlur,
}) => {
  const birthDateRef = useRef<DatePicker>(null);
  const firstInputRef = useRef<HTMLInputElement>(null);
  const firstSelectRef = useRef<HTMLSelectElement>(null);

  useEffect(() => {
    if (mode === 'ADD') {
      firstInputRef.current?.focus();
    } else {
      firstSelectRef.current?.focus();
    }
  }, [mode]);

  const toggleCalendar = (ref: React.RefObject<DatePicker | null>) => {
    if (!ref.current) return;
    const dp = ref.current as any;
    if (typeof dp.toggleCalendar === 'function') {
      dp.toggleCalendar();
    } else if (typeof dp.setOpen === 'function') {
      dp.setOpen(true);
    } else {
      ref.current.setFocus();
    }
  };

  return (
    <>
      {/* 1. Tài khoản (Account Name) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-login-id">
          <i className="relative">
            {FIELD_LABELS.ACCOUNT_NAME_COLON}
            {mode === 'ADD' && <span className="note-red">*</span>}
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            ref={firstInputRef}
            id="employee-login-id"
            name="employeeLoginId"
            type="text"
            tabIndex={mode === 'EDIT' ? -1 : 1}
            className={`form-control ${errors.employeeLoginId ? 'is-invalid' : ''}`}
            style={{
              ...(errors.employeeLoginId ? { borderColor: '#c00' } : {}),
              ...(mode === 'EDIT' ? { backgroundColor: '#e9ecef', cursor: 'not-allowed' } : {}),
            }}
            value={formData.employeeLoginId}
            disabled={mode === 'EDIT'}
            readOnly={mode === 'EDIT'}
            onChange={(e) => onFieldChange('employeeLoginId', e.target.value)}
            onBlur={() => onBlur('employeeLoginId')}
          />
          {errors.employeeLoginId && (
            <div id="error-employee-login-id" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeLoginId}
            </div>
          )}
        </div>
      </li>

      {/* 2. Nhóm / Phòng ban (Group) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-department-id">
          <i className="relative">
            {FIELD_LABELS.GROUP_COLON}
            <span className="note-red">*</span>
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <select
            ref={firstSelectRef}
            id="employee-department-id"
            name="departmentId"
            tabIndex={2}
            className={`form-control ${errors.departmentId ? 'is-invalid' : ''}`}
            style={errors.departmentId ? { borderColor: '#c00' } : undefined}
            value={formData.departmentId ?? ''}
            onChange={(e) => {
              const rawVal = e.target.value;
              const deptId = rawVal === '' ? '' : Number(rawVal);
              const selectedDept = departments.find((d) => d.departmentId === deptId);
              onFieldChange({
                departmentId: deptId,
                departmentName: selectedDept?.departmentName || '',
              });
            }}
            onBlur={() => onBlur('departmentId')}
          >
            <option value="">{COMMON_LABELS.SELECT_DEFAULT}</option>
            {departments.map((dept) => (
              <option key={dept.departmentId} value={dept.departmentId}>
                {dept.departmentName}
              </option>
            ))}
          </select>
          {errors.departmentId && (
            <div id="error-employee-department-id" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.departmentId}
            </div>
          )}
        </div>
      </li>

      {/* 3. Họ và tên (Name) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-name">
          <i className="relative">
            {FIELD_LABELS.NAME_COLON}
            <span className="note-red">*</span>
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-name"
            name="employeeName"
            type="text"
            tabIndex={3}
            className={`form-control ${errors.employeeName ? 'is-invalid' : ''}`}
            style={errors.employeeName ? { borderColor: '#c00' } : undefined}
            value={formData.employeeName}
            onChange={(e) => onFieldChange('employeeName', e.target.value)}
            onBlur={() => onBlur('employeeName')}
          />
          {errors.employeeName && (
            <div id="error-employee-name" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeName}
            </div>
          )}
        </div>
      </li>

      {/* 4. Tên Katakana (Katakana Name) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-name-kana">
          <i className="relative">
            {FIELD_LABELS.KATAKANA_NAME_COLON}
            <span className="note-red">*</span>
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-name-kana"
            name="employeeNameKana"
            type="text"
            tabIndex={4}
            className={`form-control ${errors.employeeNameKana ? 'is-invalid' : ''}`}
            style={errors.employeeNameKana ? { borderColor: '#c00' } : undefined}
            value={formData.employeeNameKana}
            onChange={(e) => onFieldChange('employeeNameKana', e.target.value)}
            onBlur={() => onBlur('employeeNameKana')}
          />
          {errors.employeeNameKana && (
            <div id="error-employee-name-kana" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeNameKana}
            </div>
          )}
        </div>
      </li>

      {/* 5. Ngày sinh (Birthday) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-birth-date">
          <i className="relative">
            {FIELD_LABELS.BIRTHDAY_COLON}
            <span className="note-red">*</span>
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <div className="datepicker-wrapper">
            <DatePicker
              id="employee-birth-date"
              ref={birthDateRef}
              tabIndex={5}
              placeholderText={COMMON_LABELS.DATE_PLACEHOLDER}
              selected={birthDateObj}
              onChange={(date: Date | null) => onDateChange('employeeBirthDate', date)}
              onBlur={() => onBlur('employeeBirthDate')}
              dateFormat={COMMON_LABELS.DATE_PLACEHOLDER}
              className={`form-control ${errors.employeeBirthDate ? 'is-invalid' : ''}`}
              wrapperClassName="w-100"
              preventOpenOnFocus
              onKeyDown={(e) => {
                if (e.key !== 'Tab') {
                  e.preventDefault();
                }
              }}
              onChangeRaw={(e) => {
                e?.preventDefault();
              }}
            />
            <span
              className="glyphicon glyphicon-calendar"
              onClick={() => toggleCalendar(birthDateRef)}
            ></span>
          </div>
          {errors.employeeBirthDate && (
            <div id="error-employee-birth-date" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeBirthDate}
            </div>
          )}
        </div>
      </li>

      {/* 6. Email */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-email">
          <i className="relative">
            {FIELD_LABELS.EMAIL_COLON}
            <span className="note-red">*</span>
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-email"
            name="employeeEmail"
            type="text"
            tabIndex={6}
            className={`form-control ${errors.employeeEmail ? 'is-invalid' : ''}`}
            style={errors.employeeEmail ? { borderColor: '#c00' } : undefined}
            value={formData.employeeEmail}
            onChange={(e) => onFieldChange('employeeEmail', e.target.value)}
            onBlur={() => onBlur('employeeEmail')}
          />
          {errors.employeeEmail && (
            <div id="error-employee-email" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeEmail}
            </div>
          )}
        </div>
      </li>

      {/* 7. Số điện thoại (Telephone) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-telephone">
          <i className="relative">
            {FIELD_LABELS.TEL_COLON}
            <span className="note-red">*</span>
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-telephone"
            name="employeeTelephone"
            type="text"
            tabIndex={7}
            className={`form-control ${errors.employeeTelephone ? 'is-invalid' : ''}`}
            style={errors.employeeTelephone ? { borderColor: '#c00' } : undefined}
            value={formData.employeeTelephone}
            onChange={(e) => onFieldChange('employeeTelephone', e.target.value)}
            onBlur={() => onBlur('employeeTelephone')}
          />
          {errors.employeeTelephone && (
            <div id="error-employee-telephone" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeTelephone}
            </div>
          )}
        </div>
      </li>

      {/* 8. Mật khẩu (Password) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-login-password">
          <i className="relative">
            {FIELD_LABELS.PASSWORD_COLON}
            {mode === 'ADD' && <span className="note-red">*</span>}
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-login-password"
            name="employeeLoginPassword"
            type="password"
            tabIndex={8}
            className={`form-control ${errors.employeeLoginPassword ? 'is-invalid' : ''}`}
            style={errors.employeeLoginPassword ? { borderColor: '#c00' } : undefined}
            value={formData.employeeLoginPassword ?? ''}
            onChange={(e) => onFieldChange('employeeLoginPassword', e.target.value)}
            onBlur={() => onBlur('employeeLoginPassword')}
          />
          {errors.employeeLoginPassword && (
            <div id="error-employee-login-password" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeLoginPassword}
            </div>
          )}
        </div>
      </li>

      {/* 9. Xác nhận mật khẩu (Password Confirm) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-login-password-confirm">
          <i className="relative">
            {FIELD_LABELS.PASSWORD_CONFIRM_COLON}
            {mode === 'ADD' && <span className="note-red">*</span>}
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-login-password-confirm"
            name="employeeLoginPasswordConfirm"
            type="password"
            tabIndex={9}
            className={`form-control ${errors.employeeLoginPasswordConfirm ? 'is-invalid' : ''}`}
            style={errors.employeeLoginPasswordConfirm ? { borderColor: '#c00' } : undefined}
            value={formData.employeeLoginPasswordConfirm ?? ''}
            onChange={(e) => onFieldChange('employeeLoginPasswordConfirm', e.target.value)}
            onBlur={() => onBlur('employeeLoginPasswordConfirm')}
          />
          {errors.employeeLoginPasswordConfirm && (
            <div id="error-employee-login-password-confirm" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.employeeLoginPasswordConfirm}
            </div>
          )}
        </div>
      </li>
    </>
  );
};

export default EmployeeBasicInfoForm;
