import {
  saveEmployeeSearchState,
  getEmployeeSearchState,
  clearEmployeeSearchState,
  EmployeeSearchState,
} from '@/lib/storage/employeeSearchState';
import { STORAGE_KEYS, SORT_ORDERS } from '@/constants';

describe('Employee Search State Storage Utilities', () => {
  beforeEach(() => {
    sessionStorage.clear();
  });

  it('should save and retrieve search state correctly', () => {
    const mockState: EmployeeSearchState = {
      currentPage: 2,
      searchName: 'Yamada',
      searchDepartmentId: 1,
      appliedName: 'Yamada',
      appliedDepartmentId: 1,
      sort: {
        employeeNameOrder: SORT_ORDERS.DESC,
        certificationNameOrder: SORT_ORDERS.ASC,
        endDateOrder: SORT_ORDERS.ASC,
      },
      activeSortColumn: 'employeeNameOrder',
    };

    saveEmployeeSearchState(mockState);

    const retrieved = getEmployeeSearchState();
    expect(retrieved).toEqual(mockState);
  });

  it('should return null if no search state is stored', () => {
    const retrieved = getEmployeeSearchState();
    expect(retrieved).toBeNull();
  });

  it('should return null if stored JSON is corrupted', () => {
    sessionStorage.setItem(STORAGE_KEYS.EMPLOYEE_SEARCH_STATE, 'invalid-json{');
    const retrieved = getEmployeeSearchState();
    expect(retrieved).toBeNull();
  });

  it('should clear stored search state', () => {
    const mockState: EmployeeSearchState = {
      currentPage: 3,
      searchName: '',
      searchDepartmentId: undefined,
      appliedName: '',
      appliedDepartmentId: undefined,
      sort: {
        employeeNameOrder: SORT_ORDERS.ASC,
        certificationNameOrder: SORT_ORDERS.ASC,
        endDateOrder: SORT_ORDERS.ASC,
      },
      activeSortColumn: 'employeeNameOrder',
    };

    saveEmployeeSearchState(mockState);
    clearEmployeeSearchState();

    const retrieved = getEmployeeSearchState();
    expect(retrieved).toBeNull();
  });
});
