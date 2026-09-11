'use client';

/**
 * Copyright(C) 2026 Luvina
 * Adm003.tsx - Component hiển thị thông tin chi tiết nhân viên (ADM003)
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { EmployeeDetail } from '@/types/employee';
import { BUTTON_LABELS, COMMON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';

export interface Adm003Props {
  employee: EmployeeDetail | null;
  loading: boolean;
  deleting: boolean;
  apiError: string | null;
  hasCertification: boolean;
  handleNavigateToEdit: () => void;
  handleNavigateToList: () => void;
  handleDelete: () => Promise<void> | void;
}

export const Adm003: React.FC<Adm003Props> = ({
  employee,
  loading,
  deleting,
  apiError,
  hasCertification,
  handleNavigateToEdit,
  handleNavigateToList,
  handleDelete,
}) => {
  if (loading && !employee && !apiError) {
    return (
      <div className="row">
        <div style={{ padding: '24px', textAlign: 'center', color: '#888' }}>
          {COMMON_LABELS.LOADING}
        </div>
      </div>
    );
  }

  return (
    <div className="row">
      <form className="c-form box-shadow" onSubmit={(e) => e.preventDefault()}>
        <ul className="show-data">
          {/* Tiêu đề khối thông tin */}
          <li className="title">{PAGE_TITLES.INFO_CONFIRM}</li>

          {/* Vùng hiển thị thông báo lỗi từ server nếu có */}
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
            <div id="detail-account-name" className="col-sm col-sm-10">
              {employee?.employeeLoginId || ''}
            </div>
          </li>

          {/* Phòng ban */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.GROUP}</label>
            <div id="detail-group" className="col-sm col-sm-10">
              {employee?.departmentName || ''}
            </div>
          </li>

          {/* Họ và tên */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.NAME}</label>
            <div id="detail-name" className="col-sm col-sm-10">
              {employee?.employeeName || ''}
            </div>
          </li>

          {/* Họ và tên Katakana */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.KATAKANA_NAME}</label>
            <div id="detail-katakana-name" className="col-sm col-sm-10">
              {employee?.employeeNameKana || ''}
            </div>
          </li>

          {/* Ngày sinh */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.BIRTHDAY}</label>
            <div id="detail-birthday" className="col-sm col-sm-10">
              {employee?.employeeBirthDate || ''}
            </div>
          </li>

          {/* Email */}
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.EMAIL}</label>
            <div id="detail-email" className="col-sm col-sm-10">
              {employee?.employeeEmail || ''}
            </div>
          </li>

          {/* Số điện thoại */}
          <li className="form-group row d-flex bor-none">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.TEL}</label>
            <div id="detail-tel" className="col-sm col-sm-10">
              {employee?.employeeTelephone || ''}
            </div>
          </li>

          {/* Khối thông tin chứng chỉ tiếng Nhật (nếu có) */}
          {hasCertification && (
            <>
              <li className="title mt-12">
                <a href="#!">{FIELD_LABELS.JAPANESE_LEVEL}</a>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.CERTIFICATION}</label>
                <div id="detail-certification-name" className="col-sm col-sm-10">
                  {employee?.certificationName || ''}
                </div>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.START_DATE}</label>
                <div id="detail-start-date" className="col-sm col-sm-10">
                  {employee?.certificationStartDate || ''}
                </div>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.END_DATE}</label>
                <div id="detail-end-date" className="col-sm col-sm-10">
                  {employee?.certificationEndDate || ''}
                </div>
              </li>
              <li className="form-group row d-flex">
                <label className="col-form-label col-sm-2">{FIELD_LABELS.SCORE}</label>
                <div id="detail-score" className="col-sm col-sm-10">
                  {employee?.score !== null && employee?.score !== undefined ? String(employee.score) : ''}
                </div>
              </li>
            </>
          )}

          {/* Nhóm nút thao tác: 編集 (Edit), 削除 (Delete), 戻る (Back) */}
          <li className="form-group row d-flex">
            <div className="btn-group col-sm col-sm-10 ml">
              <button
                type="button"
                id="btn-detail-edit"
                onClick={handleNavigateToEdit}
                disabled={!employee}
                className="btn btn-primary btn-sm"
                tabIndex={1}
              >
                {BUTTON_LABELS.EDIT}
              </button>
              <button
                type="button"
                id="btn-detail-delete"
                onClick={handleDelete}
                disabled={!employee || deleting}
                className="btn btn-secondary btn-sm"
                tabIndex={2}
              >
                {BUTTON_LABELS.DELETE}
              </button>
              <button
                type="button"
                id="btn-detail-back"
                onClick={handleNavigateToList}
                className="btn btn-secondary btn-sm"
                tabIndex={3}
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

export default Adm003;
