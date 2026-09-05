'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeeJapaneseForm.tsx - Sub-component for Japanese Level & Certification Form (ADM004)
 * 04/09/2026 Pham Van Minh
 */

import React, { useRef } from 'react';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { CertificationItem } from '@/types/certification';
import { EmployeeFormData, EmployeeFormErrors } from '@/types/employee';
import { COMMON_LABELS, FIELD_LABELS } from '@/constants';

interface EmployeeJapaneseFormProps {
  formData: EmployeeFormData;
  errors: EmployeeFormErrors;
  certifications: CertificationItem[];
  isCertSelected: boolean;
  startDateObj: Date | null;
  endDateObj: Date | null;
  onFieldChange: (
    fieldOrUpdates: keyof EmployeeFormData | Partial<EmployeeFormData>,
    value?: any
  ) => void;
  onDateChange: (
    field: 'certificationStartDate' | 'certificationEndDate',
    date: Date | null
  ) => void;
  onBlur: (field: keyof EmployeeFormData) => void;
}

/**
 * Component hiển thị và chỉnh sửa khối Trình độ tiếng Nhật (Chứng chỉ, Ngày cấp, Ngày hết hạn, Điểm số).
 */
export const EmployeeJapaneseForm: React.FC<EmployeeJapaneseFormProps> = ({
  formData,
  errors,
  certifications,
  isCertSelected,
  startDateObj,
  endDateObj,
  onFieldChange,
  onDateChange,
  onBlur,
}) => {
  const startDateRef = useRef<DatePicker>(null);
  const endDateRef = useRef<DatePicker>(null);

  return (
    <>
      {/* Tiêu đề khối tiếng Nhật */}
      <li className="title mt-12">
        <a href="#!">{FIELD_LABELS.JAPANESE_LEVEL}</a>
      </li>

      {/* 10. Tên chứng chỉ (Certification) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-certification-id">
          <i className="relative">{FIELD_LABELS.CERTIFICATION_COLON}</i>
        </label>
        <div className="col-sm col-sm-10">
          <select
            id="employee-certification-id"
            name="certificationId"
            className="form-control"
            value={formData.certificationId ?? ''}
            onChange={(e) => {
              const rawVal = e.target.value;
              const certId = rawVal === '' ? '' : Number(rawVal);
              const selectedCert = certifications.find((c) => c.certificationId === certId);
              onFieldChange({
                certificationId: certId,
                certificationName: selectedCert?.certificationName || '',
              });
            }}
            onBlur={() => onBlur('certificationId')}
          >
            <option value="">{COMMON_LABELS.SELECT_DEFAULT}</option>
            {certifications.map((cert) => (
              <option key={cert.certificationId} value={cert.certificationId}>
                {cert.certificationName}
              </option>
            ))}
          </select>
        </div>
      </li>

      {/* 11. Ngày cấp chứng chỉ (Start Date) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-certification-start-date">
          <i className="relative">
            {FIELD_LABELS.START_DATE_COLON}
            {isCertSelected && <span className="note-red">*</span>}
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <div className="datepicker-wrapper">
            <DatePicker
              id="employee-certification-start-date"
              ref={startDateRef}
              disabled={!isCertSelected}
              placeholderText={COMMON_LABELS.DATE_PLACEHOLDER}
              selected={startDateObj}
              onChange={(date: Date | null) => onDateChange('certificationStartDate', date)}
              onBlur={() => onBlur('certificationStartDate')}
              dateFormat={COMMON_LABELS.DATE_PLACEHOLDER}
              className={`form-control ${errors.certificationStartDate ? 'is-invalid' : ''}`}
              wrapperClassName="w-100"
            />
            <span
              className="glyphicon glyphicon-calendar"
              onClick={() => {
                if (isCertSelected) startDateRef.current?.setFocus();
              }}
              style={!isCertSelected ? { cursor: 'not-allowed', opacity: 0.5 } : undefined}
            ></span>
          </div>
          {errors.certificationStartDate && (
            <div
              id="error-employee-certification-start-date"
              className="invalid-feedback"
              style={{ display: 'block' }}
            >
              {errors.certificationStartDate}
            </div>
          )}
        </div>
      </li>

      {/* 12. Ngày hết hạn chứng chỉ (End Date) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-certification-end-date">
          <i className="relative">
            {FIELD_LABELS.END_DATE_COLON}
            {isCertSelected && <span className="note-red">*</span>}
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <div className="datepicker-wrapper">
            <DatePicker
              id="employee-certification-end-date"
              ref={endDateRef}
              disabled={!isCertSelected}
              placeholderText={COMMON_LABELS.DATE_PLACEHOLDER}
              selected={endDateObj}
              onChange={(date: Date | null) => onDateChange('certificationEndDate', date)}
              onBlur={() => onBlur('certificationEndDate')}
              dateFormat={COMMON_LABELS.DATE_PLACEHOLDER}
              className={`form-control ${errors.certificationEndDate ? 'is-invalid' : ''}`}
              wrapperClassName="w-100"
            />
            <span
              className="glyphicon glyphicon-calendar"
              onClick={() => {
                if (isCertSelected) endDateRef.current?.setFocus();
              }}
              style={!isCertSelected ? { cursor: 'not-allowed', opacity: 0.5 } : undefined}
            ></span>
          </div>
          {errors.certificationEndDate && (
            <div
              id="error-employee-certification-end-date"
              className="invalid-feedback"
              style={{ display: 'block' }}
            >
              {errors.certificationEndDate}
            </div>
          )}
        </div>
      </li>

      {/* 13. Điểm số (Score) */}
      <li className="form-group row d-flex">
        <label className="col-form-label col-sm-2" htmlFor="employee-score">
          <i className="relative">
            {FIELD_LABELS.SCORE_COLON}
            {isCertSelected && <span className="note-red">*</span>}
          </i>
        </label>
        <div className="col-sm col-sm-10">
          <input
            id="employee-score"
            name="score"
            type="text"
            disabled={!isCertSelected}
            className={`form-control ${errors.score ? 'is-invalid' : ''}`}
            style={errors.score ? { borderColor: '#c00' } : undefined}
            value={formData.score !== null && formData.score !== undefined ? formData.score : ''}
            onChange={(e) => onFieldChange('score', e.target.value)}
            onBlur={() => onBlur('score')}
          />
          {errors.score && (
            <div id="error-employee-score" className="invalid-feedback" style={{ display: 'block' }}>
              {errors.score}
            </div>
          )}
        </div>
      </li>
    </>
  );
};

export default EmployeeJapaneseForm;
