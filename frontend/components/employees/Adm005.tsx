'use client';

/**
 * Copyright(C) 2026 Luvina
 * Adm005.tsx - Component xác nhận thông tin nhân viên (ADM005)
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { EmployeeFormData } from '@/types/employee';
import { BUTTON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';

export interface Adm005Props {
  formData: EmployeeFormData | null;
  loading: boolean;
  submitting: boolean;
  apiError: string | null;
  hasCertification: boolean;
  handleConfirmSubmit: () => Promise<void> | void;
  handleNavigateToEdit: () => void;
}

export const Adm005: React.FC<Adm005Props> = ({
  formData,
  loading,
  submitting,
  apiError,
  hasCertification,
  handleConfirmSubmit,
  handleNavigateToEdit,
}) => {
  if (loading || !formData) {
    return null;
  }

  return (
    <div className="row">
      <form className="c-form box-shadow" onSubmit={(e) => e.preventDefault()}>
        <ul className="show-data">
          {/* Tiêu đề màn hình xác nhận */}
          <li className="title">
            <p>{PAGE_TITLES.INFO_CONFIRM}</p>
            <p>{PAGE_TITLES.CONFIRM_DESCRIPTION}</p>
          </li>

          {/* Vùng hiển thị thông báo lỗi từ API nếu có */}
          {apiError && (
            <li className="box-err">
              <div id="api-error-box" className="box-err-content">
                {apiError}
              </div>
            </li>
          )}

          {/* Tài khoản */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.ACCOUNT_NAME}</label>
            <div className="col-sm col-sm-10">{formData.employeeLoginId}</div>
          </li>

          {/* Nhóm / Phòng ban */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.GROUP}</label>
            <div className="col-sm col-sm-10">{formData.departmentName || ''}</div>
          </li>

          {/* Họ và tên */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.NAME}</label>
            <div className="col-sm col-sm-10">{formData.employeeName}</div>
          </li>

          {/* Họ và tên Katakana */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.KATAKANA_NAME}</label>
            <div className="col-sm col-sm-10">{formData.employeeNameKana}</div>
          </li>

          {/* Ngày sinh */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.BIRTHDAY}</label>
            <div className="col-sm col-sm-10">{formData.employeeBirthDate}</div>
          </li>

          {/* Email */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.EMAIL}</label>
            <div className="col-sm col-sm-10">{formData.employeeEmail}</div>
          </li>

          {/* Số điện thoại */}
          <li className="form-group row d-flex bor-none">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.TEL}</label>
            <div className="col-sm col-sm-10">{formData.employeeTelephone}</div>
          </li>

          {/* Khối trình độ tiếng Nhật (chỉ hiển thị khi có chứng chỉ được chọn) */}
          {hasCertification && (
            <>
              <li className="title mt-12">
                <a href="#!">{FIELD_LABELS.JAPANESE_LEVEL}</a>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.CERTIFICATION}</label>
                <div className="col-sm col-sm-10">{formData.certificationName || ''}</div>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.START_DATE}</label>
                <div className="col-sm col-sm-10">{formData.certificationStartDate || ''}</div>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.END_DATE}</label>
                <div className="col-sm col-sm-10">{formData.certificationEndDate || ''}</div>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.SCORE}</label>
                <div className="col-sm col-sm-10">{formData.score || ''}</div>
              </li>
            </>
          )}

          {/* Nhóm nút thao tác (OK / 戻る) */}
          <li className="form-group row d-flex">
            <div className="btn-group col-sm col-sm-10 ml">
              <button
                type="button"
                id="btn-confirm-submit"
                onClick={handleConfirmSubmit}
                disabled={submitting}
                className="btn btn-primary btn-sm"
              >
                {BUTTON_LABELS.OK}
              </button>
              <button
                type="button"
                id="btn-confirm-back"
                onClick={handleNavigateToEdit}
                className="btn btn-secondary btn-sm"
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

export default Adm005;
