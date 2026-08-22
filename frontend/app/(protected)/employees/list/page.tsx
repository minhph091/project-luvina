'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM002: Màn hình danh sách nhân viên
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useRouter } from 'next/navigation';
import { useAuth } from '@/hooks/useAuth';
import { useDepartments } from '@/hooks/useDepartments';
import { useEmployees } from '@/hooks/useEmployees';
import { APP_ROUTES } from '@/constants';
import EmployeeSearchForm from '@/components/employees/EmployeeSearchForm';
import EmployeeTable from '@/components/employees/EmployeeTable';
import EmployeePagination from '@/components/employees/EmployeePagination';

/**
 * Component trang Danh sách nhân viên (ADM002).
 * Phối hợp các sub-components (SearchForm, Table, Pagination) và custom hooks (useAuth, useDepartments, useEmployees).
 */
export default function EmployeeListPage() {
  // Bảo vệ route: kiểm tra token xác thực
  useAuth();
  const router = useRouter();

  // Custom hook lấy danh sách phòng ban cho dropdown
  const { departments, departmentErrorMessage } = useDepartments();

  // Custom hook quản lý state, tìm kiếm, phân trang và sắp xếp danh sách nhân viên
  const {
    employees,
    totalPages,
    loading,
    errorMessage,
    currentPage,
    searchName,
    searchDepartmentId,
    setSearchName,
    setSearchDepartmentId,
    handleSearch,
    handlePageChange,
    handleSort,
    sortIcon,
    formatDate,
    getPageNumbers,
  } = useEmployees();

  /**
   * Xử lý submit form tìm kiếm nhân viên.
   *
   * @param event Form event
   */
  const handleSearchSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    handleSearch();
  };

  /**
   * Điều hướng sang màn hình thêm mới nhân viên (ADM004).
   */
  const handleNavigateToAddEmployee = () => {
    router.push(APP_ROUTES.EMPLOYEE_EDIT);
  };

  // Thông báo lỗi chung (ưu tiên lỗi nhân viên, sau đó đến lỗi phòng ban)
  const displayErrorMessage = errorMessage || departmentErrorMessage;

  return (
    <>
      {/* ── Khu vực Form Tìm kiếm ──────────────────────────────────── */}
      <EmployeeSearchForm
        searchName={searchName}
        searchDepartmentId={searchDepartmentId}
        departments={departments}
        loading={loading}
        onSearchNameChange={setSearchName}
        onSearchDepartmentIdChange={setSearchDepartmentId}
        onSearchSubmit={handleSearchSubmit}
        onNavigateToAdd={handleNavigateToAddEmployee}
      />

      {/* ── Vùng Thông báo lỗi (dưới header khi API trả về lỗi) ───── */}
      {displayErrorMessage && (
        <div className="box-err-content" style={{ marginBottom: 16 }}>
          {displayErrorMessage}
        </div>
      )}

      {/* ── Bảng danh sách nhân viên ──────────────────────────────── */}
      <EmployeeTable
        employees={employees}
        loading={loading}
        onSort={handleSort}
        getSortIcon={sortIcon}
        formatDate={formatDate}
      />

      {/* ── Thanh điều khiển phân trang ───────────────────────────── */}
      <EmployeePagination
        currentPage={currentPage}
        totalPages={totalPages}
        loading={loading}
        pageNumbers={getPageNumbers()}
        onPageChange={handlePageChange}
      />
    </>
  );
}
