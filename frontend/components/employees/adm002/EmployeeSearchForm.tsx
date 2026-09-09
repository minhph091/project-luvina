'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeeSearchForm.tsx - Component form tìm kiếm danh sách nhân viên
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { DepartmentItem } from '@/types/department';
import { BUTTON_LABELS, COMMON_LABELS, FIELD_LABELS, PAGE_TITLES } from '@/constants';

interface EmployeeSearchFormProps {
  searchName: string;
  searchDepartmentId: number | undefined;
  departments: DepartmentItem[];
  loading: boolean;
  onSearchNameChange: (name: string) => void;
  onSearchDepartmentIdChange: (departmentId: number | undefined) => void;
  onSearchSubmit: (event: React.FormEvent<HTMLFormElement>) => void;
  onNavigateToAdd: () => void;
}

/**
 * Component hiển thị khu vực tìm kiếm nhân viên theo tên và phòng ban.
 */
export const EmployeeSearchForm: React.FC<EmployeeSearchFormProps> = ({
  searchName,
  searchDepartmentId,
  departments,
  loading,
  onSearchNameChange,
  onSearchDepartmentIdChange,
  onSearchSubmit,
  onNavigateToAdd,
}) => {
  return (
    <div className="search-memb">
      <h1 className="title">
        {PAGE_TITLES.SEARCH_MEMBERS_DESCRIPTION}
      </h1>
      <form className="c-form" onSubmit={onSearchSubmit}>
        <ul className="d-flex">
          {/* Nhập tên nhân viên */}
          <li className="form-group row">
            <label className="col-form-label">
              {FIELD_LABELS.NAME_COLON}
            </label>
            <div className="col-sm">
              <input
                id="search-name"
                type="text"
                value={searchName}
                onChange={(event) => onSearchNameChange(event.target.value)}
                maxLength={125}
              />
            </div>
          </li>

          {/* Chọn phòng ban (Group) */}
          <li className="form-group row">
            <label className="col-form-label">
              {FIELD_LABELS.GROUP_COLON}
            </label>
            <div className="col-sm">
              <select
                id="search-department"
                value={searchDepartmentId ?? ''}
                onChange={(event) =>
                  onSearchDepartmentIdChange(
                    event.target.value === '' ? undefined : Number(event.target.value)
                  )
                }
              >
                <option value="">{COMMON_LABELS.ALL}</option>
                {departments.map((department) => (
                  <option key={department.departmentId} value={department.departmentId}>
                    {department.departmentName}
                  </option>
                ))}
              </select>
            </div>
          </li>

          {/* Nút Tìm kiếm & Thêm mới */}
          <li className="form-group row">
            <div className="btn-group">
              <button
                id="btn-search"
                type="submit"
                className="btn btn-primary btn-sm"
                disabled={loading}
              >
                {BUTTON_LABELS.SEARCH}
              </button>
              <button
                id="btn-add"
                type="button"
                onClick={onNavigateToAdd}
                className="btn btn-secondary btn-sm"
              >
                {BUTTON_LABELS.ADD_NEW}
              </button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  );
};

export default EmployeeSearchForm;
