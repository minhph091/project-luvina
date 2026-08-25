'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM004: Employee Edit/Create Page
 * 21/08/2026 Pham Van Minh
 */

import React, { useState, useRef } from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { APP_ROUTES, BUTTON_LABELS, COMMON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';

export default function EmployeeEditPage() {
  useAuth();
  const router = useRouter();
  const [birthDate, setBirthDate] = useState<Date | null>(null);
  const [certificationStartDate, setCertificationStartDate] = useState<Date | null>(null);
  const [certificationEndDate, setCertificationEndDate] = useState<Date | null>(null);

  const birthDateRef = useRef<DatePicker>(null);
  const certificationStartDateRef = useRef<DatePicker>(null);
  const certificationEndDateRef = useRef<DatePicker>(null);

  const handleNavigateToConfirm = () => {
    router.push(APP_ROUTES.EMPLOYEE_CONFIRM);
  };

  const handleNavigateToList = () => {
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="row">
      <form className="c-form box-shadow was-validated">
        <ul>
          <li className="title">{PAGE_TITLES.EDIT_EMPLOYEE}</li>
          <li className="box-err">
            <div className="box-err-content">{COMMON_LABELS.EMPTY_ERROR_BOX}</div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.ACCOUNT_NAME_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.GROUP_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10">
              <select className="form-control">
                <option>{COMMON_LABELS.SELECT_DEFAULT}</option>
                <option>Nhóm 1</option>
                <option>Nhóm 2</option>
              </select>
            </div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.NAME_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.KATAKANA_NAME_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10">
              <input type="text" className="form-control" required />
              <div className="invalid-feedback">Example invalid form file feedback</div>
            </div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.BIRTHDAY_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10 d-flex">
              <div className="datepicker-wrapper">
                <DatePicker 
                  ref={birthDateRef}
                  placeholderText={COMMON_LABELS.DATE_PLACEHOLDER}
                  selected={birthDate} 
                  onChange={(date: Date | null) => setBirthDate(date ?? null)} 
                  dateFormat={COMMON_LABELS.DATE_PLACEHOLDER}
                />
                <span className="glyphicon glyphicon-calendar" onClick={() => birthDateRef.current?.setFocus()}></span>
              </div>
            </div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.EMAIL_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.TEL_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.PASSWORD_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.PASSWORD_CONFIRM_COLON}</i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="title mt-12"><a href="#!">{FIELD_LABELS.JAPANESE_LEVEL}</a></li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.CERTIFICATION_COLON}</i></label>
            <div className="col-sm col-sm-10">
              <select className="form-control">
                <option>{COMMON_LABELS.SELECT_DEFAULT}</option>
                <option>N 1</option>
                <option>N 2</option>
              </select>
            </div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.START_DATE_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10 d-flex">
              <div className="datepicker-wrapper">
                <DatePicker 
                  ref={certificationStartDateRef}
                  placeholderText={COMMON_LABELS.DATE_PLACEHOLDER}
                  selected={certificationStartDate} 
                  onChange={(date: Date | null) => setCertificationStartDate(date ?? null)} 
                  dateFormat={COMMON_LABELS.DATE_PLACEHOLDER}
                />
                <span className="glyphicon glyphicon-calendar" onClick={() => certificationStartDateRef.current?.setFocus()}></span>
              </div>
            </div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.END_DATE_COLON}<span className="note-red">*</span></i></label>
            <div className="col-sm col-sm-10 d-flex">
              <div className="datepicker-wrapper">
                <DatePicker 
                  ref={certificationEndDateRef}
                  placeholderText={COMMON_LABELS.DATE_PLACEHOLDER}
                  selected={certificationEndDate} 
                  onChange={(date: Date | null) => setCertificationEndDate(date ?? null)} 
                  dateFormat={COMMON_LABELS.DATE_PLACEHOLDER}
                />
                <span className="glyphicon glyphicon-calendar" onClick={() => certificationEndDateRef.current?.setFocus()}></span>
              </div>
            </div>
          </li>
          <li className="form-group row d-flex">
            <label className="col-form-label col-sm-2"><i className="relative">{FIELD_LABELS.SCORE_COLON}</i></label>
            <div className="col-sm col-sm-10"><input type="text" className="form-control" /></div>
          </li>
          <li className="form-group row d-flex">
            <div className="btn-group col-sm col-sm-10 ml">
              <button type="button" onClick={handleNavigateToConfirm} className="btn btn-primary btn-sm">{BUTTON_LABELS.CONFIRM}</button>
              <button type="button" onClick={handleNavigateToList} className="btn btn-secondary btn-sm">{BUTTON_LABELS.BACK}</button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  );
}
