import { renderHook, act, waitFor } from '@testing-library/react';
import { useEmployees } from '@/hooks/useEmployees';
import { getEmployees } from '@/lib/api/employees';
import {
  saveEmployeeSearchState,
  getEmployeeSearchState,
} from '@/lib/storage/employeeSearchState';
import { SORT_ORDERS } from '@/constants';

jest.mock('@/lib/api/employees', () => ({
  getEmployees: jest.fn(),
}));

describe('useEmployees Hook', () => {
  beforeEach(() => {
    jest.clearAllMocks();
    sessionStorage.clear();
  });

  it('fetches employees on mount with default parameters', async () => {
    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 1,
      employees: [
        {
          employeeId: 1,
          employeeName: 'Nguyễn Văn A',
          employeeBirthDate: '1990-01-01',
          departmentName: 'DEV1',
          employeeEmail: 'a@luvina.net',
          employeeTelephone: '0123456789',
          certificationName: 'N1',
          endDate: '2025-12-31',
          score: 160,
        },
      ],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.loading).toBe(false);
      expect(hookResult.current.employees).toHaveLength(1);
      expect(hookResult.current.totalRecords).toBe(1);
    });

    expect(getEmployees).toHaveBeenCalledWith(
      expect.objectContaining({
        pageNo: 1,
        pageSize: 20,
        employeeNameOrder: 'ASC',
        certificationNameOrder: 'ASC',
        endDateOrder: 'ASC',
      })
    );
  });

  it('restores search, sort, and page state from sessionStorage on mount', async () => {
    saveEmployeeSearchState({
      currentPage: 3,
      searchName: 'Tanaka',
      searchDepartmentId: 2,
      appliedName: 'Tanaka',
      appliedDepartmentId: 2,
      sort: {
        employeeNameOrder: SORT_ORDERS.DESC,
        certificationNameOrder: SORT_ORDERS.ASC,
        endDateOrder: SORT_ORDERS.ASC,
      },
      activeSortColumn: 'employeeNameOrder',
    });

    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 60,
      employees: [],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.loading).toBe(false);
      expect(hookResult.current.currentPage).toBe(3);
      expect(hookResult.current.searchName).toBe('Tanaka');
      expect(hookResult.current.searchDepartmentId).toBe(2);
      expect(hookResult.current.sort.employeeNameOrder).toBe(SORT_ORDERS.DESC);
    });

    expect(getEmployees).toHaveBeenCalledWith(
      expect.objectContaining({
        pageNo: 3,
        pageSize: 20,
        employeeName: 'Tanaka',
        departmentId: 2,
        employeeNameOrder: SORT_ORDERS.DESC,
        sortBy: 'employeeNameOrder',
      })
    );
  });

  it('handles search correctly and saves state to sessionStorage', async () => {
    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 0,
      employees: [],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.loading).toBe(false);
    });

    act(() => {
      hookResult.current.setSearchName('Yamada');
      hookResult.current.setSearchDepartmentId(2);
    });

    await act(async () => {
      hookResult.current.handleSearch();
    });

    expect(getEmployees).toHaveBeenCalledWith(
      expect.objectContaining({
        employeeName: 'Yamada',
        departmentId: 2,
        pageNo: 1,
      })
    );

    const saved = getEmployeeSearchState();
    expect(saved?.appliedName).toBe('Yamada');
    expect(saved?.appliedDepartmentId).toBe(2);
    expect(saved?.currentPage).toBe(1);
  });

  it('handles page change correctly and saves state to sessionStorage', async () => {
    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 60,
      employees: [],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.totalPages).toBe(3);
    });

    await act(async () => {
      hookResult.current.handlePageChange(2);
    });

    expect(hookResult.current.currentPage).toBe(2);
    expect(getEmployees).toHaveBeenCalledWith(
      expect.objectContaining({
        pageNo: 2,
      })
    );

    const saved = getEmployeeSearchState();
    expect(saved?.currentPage).toBe(2);
  });

  it('handles sorting toggle correctly and saves state to sessionStorage', async () => {
    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 0,
      employees: [],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.loading).toBe(false);
    });

    await act(async () => {
      hookResult.current.handleSort('employeeNameOrder');
    });

    expect(hookResult.current.sort.employeeNameOrder).toBe('DESC');
    expect(hookResult.current.sortIcon('employeeNameOrder')).toBe(' ▼ △');

    const saved = getEmployeeSearchState();
    expect(saved?.sort.employeeNameOrder).toBe('DESC');
    expect(saved?.activeSortColumn).toBe('employeeNameOrder');

    await act(async () => {
      hookResult.current.handleSort('certificationNameOrder');
    });

    expect(hookResult.current.sort.certificationNameOrder).toBe('DESC');
    expect(hookResult.current.sortIcon('certificationNameOrder')).toBe(' ▼ △');

    await act(async () => {
      hookResult.current.handleSort('endDateOrder');
    });

    expect(hookResult.current.sort.endDateOrder).toBe('DESC');
    expect(hookResult.current.sortIcon('endDateOrder')).toBe(' ▼ △');
  });

  it('handles error when fetch fails', async () => {
    (getEmployees as jest.Mock).mockRejectedValue(new Error('Network error'));

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.loading).toBe(false);
      expect(hookResult.current.errorMessage).toBe('従業員を取得できません');
      expect(hookResult.current.employees).toEqual([]);
    });
  });

  it('formats date properly', async () => {
    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 0,
      employees: [],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    expect(hookResult.current.formatDate('2026-08-21')).toBe('2026/08/21');
    expect(hookResult.current.formatDate(null)).toBe('');
  });

  it('generates page numbers with pagination ellipsis', async () => {
    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 200, // 10 pages (200 / 20 = 10)
      employees: [],
    });

    let hookResult: { current: ReturnType<typeof useEmployees> };
    await act(async () => {
      hookResult = renderHook(() => useEmployees()).result;
    });

    await waitFor(() => {
      expect(hookResult.current.totalPages).toBe(10);
    });

    expect(hookResult.current.getPageNumbers()).toEqual([1, 2, '...', 10]);

    await act(async () => {
      hookResult.current.handlePageChange(5);
    });

    expect(hookResult.current.getPageNumbers()).toEqual([1, '...', 4, 5, 6, '...', 10]);
  });
});
