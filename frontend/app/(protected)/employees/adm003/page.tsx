'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM003: Employee Detail Page
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { APP_ROUTES, BUTTON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';
import { setEditEmployeeId, clearEmployeeFormData } from '@/lib/storage/employeeFormState';

export default function EmployeeDetailPage() {
  useAuth();
  const router = useRouter();

  const handleNavigateToEdit = () => {
    // Lưu ID nhân viên cần chỉnh sửa vào sessionStorage và xóa dữ liệu form cũ
    setEditEmployeeId(1); // placeholder ID hoặc ID từ employee detail
    clearEmployeeFormData();
    router.push(APP_ROUTES.EMPLOYEE_EDIT);
  };

  const handleNavigateToList = () => {
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="row">
      <form className="c-form box-shadow">
        <ul className="show-data">
          <li className="title">{PAGE_TITLES.INFO_CONFIRM}</li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.ACCOUNT_NAME}</label>
            <div className="col-sm col-sm-10">ntmhuong</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.GROUP}</label>
            <div className="col-sm col-sm-10">Nhóm 1</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.NAME}</label>
            <div className="col-sm col-sm-10">Nguyễn Thị Mai Hương</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.KATAKANA_NAME}</label>
            <div className="col-sm col-sm-10">名カナ</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.BIRTHDAY}</label>
            <div className="col-sm col-sm-10">1983/07/08</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.EMAIL}</label>
            <div className="col-sm col-sm-10">ntmhuong@luvina.net</div>
          </li>
          <li className="form-group row d-flex  bor-none">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.TEL}</label>
            <div className="col-sm col-sm-10">0914326386</div>
          </li>
          <li className="title mt-12"><a href="#!">{FIELD_LABELS.JAPANESE_LEVEL}</a></li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.CERTIFICATION}</label>
            <div className="col-sm col-sm-10">Trình độ tiếng nhật cấp 1</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.START_DATE}</label>
            <div className="col-sm col-sm-10">2010/07/08</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.END_DATE}</label>
            <div className="col-sm col-sm-10">2010/07/08</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2">{FIELD_LABELS.SCORE}</label>
            <div className="col-sm col-sm-10">290</div>
          </li>
          <li className="form-group row d-flex">
            <div className="btn-group col-sm col-sm-10 ml">
              <button type="button" onClick={handleNavigateToEdit} className="btn btn-primary btn-sm">{BUTTON_LABELS.EDIT}</button>
              <button type="button" className="btn btn-secondary btn-sm">{BUTTON_LABELS.DELETE}</button>
              <button type="button" onClick={handleNavigateToList} className="btn btn-secondary btn-sm">{BUTTON_LABELS.BACK}</button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  );
}
