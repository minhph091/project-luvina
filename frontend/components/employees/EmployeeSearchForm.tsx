'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeeSearchForm.tsx - Component form tìm kiếm danh sách nhân viên
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { DepartmentItem } from '@/types/department';

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
        会員名称で会員を検索します。検索条件無しの場合は全て表示されます。
      </h1>
      <form className="c-form" onSubmit={onSearchSubmit}>
        <ul className="d-flex">
          {/* Nhập tên nhân viên */}
          <li className="form-group row">
            <label className="col-form-label">
              氏名:
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
              グループ:
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
                <option value="">全て</option>
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
                検索
              </button>
              <button
                id="btn-add"
                type="button"
                onClick={onNavigateToAdd}
                className="btn btn-secondary btn-sm"
              >
                新規追加
              </button>
            </div>
          </li>
        </ul>
      </form>
    </div>
  );
};

export default EmployeeSearchForm;
