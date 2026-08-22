/**
 * Copyright(C) 2026 Luvina
 * useEmployees.ts - Custom Hook for Employee List Logic and State Management
 * 21/08/2026 Phạm Văn Minh
 */

import { useCallback, useEffect, useState } from 'react';
import { getEmployees } from '@/lib/api/employees';
import { EmployeeItem, SortState } from '@/types/employee';
import { DEFAULT_PAGE_SIZE, ERROR_MESSAGES, SORT_ORDERS } from '@/constants';

interface UseEmployeesReturn {
  employees: EmployeeItem[];
  totalRecords: number;
  totalPages: number;
  loading: boolean;
  errorMessage: string | null;
  currentPage: number;
  sort: SortState;
  searchName: string;
  searchDepartmentId: number | undefined;
  setSearchName: (name: string) => void;
  setSearchDepartmentId: (departmentId: number | undefined) => void;
  handleSearch: () => void;
  handlePageChange: (page: number) => void;
  handleSort: (column: keyof SortState) => void;
  sortIcon: (column: keyof SortState) => string;
  formatDate: (dateString: string | null) => string;
  getPageNumbers: () => (number | '...')[];
  fetchEmployees: (
    page: number,
    name: string,
    departmentId: number | undefined,
    sortState: SortState
  ) => Promise<void>;
}

/**
 * Custom hook quản lý toàn bộ state, tìm kiếm, phân trang, sắp xếp và gọi API danh sách nhân viên.
 *
 * @returns Object chứa dữ liệu và các handler xử lý logic cho màn hình danh sách nhân viên.
 */
export function useEmployees(): UseEmployeesReturn {
  // ── Trạng thái dữ liệu ──────────────────────────────────────────
  const [employees, setEmployees] = useState<EmployeeItem[]>([]);
  const [totalRecords, setTotalRecords] = useState<number>(0);
  const [loading, setLoading] = useState<boolean>(false);
  const [errorMessage, setErrorMessage] = useState<string | null>(null);

  // ── Trạng thái phân trang ────────────────────────────────────────
  const [currentPage, setCurrentPage] = useState<number>(1);

  // ── Trạng thái sắp xếp ──────────────────────────────────────────
  const [sort, setSort] = useState<SortState>({
    employeeNameOrder: SORT_ORDERS.ASC,
    certificationNameOrder: SORT_ORDERS.ASC,
    endDateOrder: SORT_ORDERS.ASC,
  });
  const [activeSortColumn, setActiveSortColumn] = useState<keyof SortState>('employeeNameOrder');

  // ── Trạng thái tìm kiếm (dữ liệu đang nhập trong form) ───────────
  const [searchName, setSearchName] = useState<string>('');
  const [searchDepartmentId, setSearchDepartmentId] = useState<number | undefined>(undefined);

  // ── Giá trị tìm kiếm đang được áp dụng ───────────────────────────
  const [appliedName, setAppliedName] = useState<string>('');
  const [appliedDepartmentId, setAppliedDepartmentId] = useState<number | undefined>(undefined);

  // ── Tính tổng số trang ───────────────────────────────────────────
  const totalPages = Math.ceil(totalRecords / DEFAULT_PAGE_SIZE);

  /**
   * Gọi API GET /employees để lấy danh sách nhân viên.
   */
  const fetchEmployees = useCallback(
    async (
      page: number,
      name: string,
      departmentId: number | undefined,
      sortState: SortState,
      sortByColumn?: keyof SortState
    ) => {
      setLoading(true);
      setErrorMessage(null);
      try {
        const responseData = await getEmployees({
          limit: DEFAULT_PAGE_SIZE,
          offset: (page - 1) * DEFAULT_PAGE_SIZE,
          pageNo: page,
          pageSize: DEFAULT_PAGE_SIZE,
          employeeName: name || undefined,
          departmentId: departmentId,
          employeeNameOrder: sortState.employeeNameOrder,
          certificationNameOrder: sortState.certificationNameOrder,
          endDateOrder: sortState.endDateOrder,
          sortBy: sortByColumn || activeSortColumn,
        });
        setEmployees(responseData.employees || []);
        setTotalRecords(responseData.totalRecords ?? responseData.totalRecord ?? 0);
      } catch {
        setErrorMessage(ERROR_MESSAGES.FETCH_EMPLOYEES_FAILED);
        setEmployees([]);
        setTotalRecords(0);
      } finally {
        setLoading(false);
      }
    },
    [activeSortColumn]
  );

  // Tải danh sách nhân viên lần đầu khi hook được mount
  useEffect(() => {
    fetchEmployees(1, '', undefined, sort, 'employeeNameOrder');
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  /**
   * Xử lý khi nhấn nút Tìm kiếm (Search).
   */
  const handleSearch = () => {
    setAppliedName(searchName);
    setAppliedDepartmentId(searchDepartmentId);
    setCurrentPage(1);
    fetchEmployees(1, searchName, searchDepartmentId, sort, activeSortColumn);
  };

  /**
   * Xử lý khi chuyển trang.
   *
   * @param page Số trang đích
   */
  const handlePageChange = (page: number) => {
    if (page < 1 || page > totalPages) return;
    setCurrentPage(page);
    fetchEmployees(page, appliedName, appliedDepartmentId, sort, activeSortColumn);
  };

  /**
   * Xử lý đảo chiều sắp xếp cột.
   *
   * @param column Tên cột sắp xếp
   */
  const handleSort = (column: keyof SortState) => {
    setActiveSortColumn(column);
    const newSortState: SortState = {
      ...sort,
      [column]: sort[column] === SORT_ORDERS.ASC ? SORT_ORDERS.DESC : SORT_ORDERS.ASC,
    };
    setSort(newSortState);
    setCurrentPage(1);
    fetchEmployees(1, appliedName, appliedDepartmentId, newSortState, column);
  };

  /**
   * Hiển thị ký hiệu mũi tên sắp xếp.
   *
   * @param column Tên cột
   * @returns Chuỗi ký hiệu mũi tên
   */
  const sortIcon = (column: keyof SortState): string => {
    return sort[column] === SORT_ORDERS.ASC ? ' ▲ ▽' : ' ▼ △';
  };

  /**
   * Format định dạng ngày từ "yyyy-MM-dd" sang "yyyy/MM/dd".
   *
   * @param dateString Chuỗi ngày tháng ISO
   * @returns Chuỗi ngày định dạng yyyy/MM/dd
   */
  const formatDate = (dateString: string | null): string => {
    if (!dateString) return '';
    return dateString.replace(/-/g, '/');
  };

  /**
   * Tạo danh sách số trang hiển thị (bao gồm dấu "...").
   *
   * @returns Mảng các số trang hoặc ký tự "..."
   */
  const getPageNumbers = (): (number | '...')[] => {
    if (totalPages <= 5) {
      return Array.from({ length: totalPages }, (_, index) => index + 1);
    }
    const pages: (number | '...')[] = [1];
    if (currentPage > 3) {
      pages.push('...');
    }
    for (
      let pageNumber = Math.max(2, currentPage - 1);
      pageNumber <= Math.min(totalPages - 1, currentPage + 1);
      pageNumber++
    ) {
      pages.push(pageNumber);
    }
    if (currentPage < totalPages - 2) {
      pages.push('...');
    }
    pages.push(totalPages);
    return pages;
  };

  return {
    employees,
    totalRecords,
    totalPages,
    loading,
    errorMessage,
    currentPage,
    sort,
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
    fetchEmployees,
  };
}
