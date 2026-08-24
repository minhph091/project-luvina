import React from 'react';
import { render, screen, waitFor, fireEvent } from '@testing-library/react';
import EmployeeListPage from '@/app/(protected)/employees/adm002/page';
import { getEmployees } from '@/lib/api/employees';
import { getDepartments } from '@/lib/api/departments';

// Mock dependencies
jest.mock('@/hooks/useAuth', () => ({
  useAuth: jest.fn(),
}));

const mockPush = jest.fn();
jest.mock('next/navigation', () => ({
  useRouter: () => ({
    push: mockPush,
  }),
}));

jest.mock('@/lib/api/employees', () => ({
  getEmployees: jest.fn(),
}));

jest.mock('@/lib/api/departments', () => ({
  getDepartments: jest.fn(),
}));

describe('EmployeeListPage Integration Tests', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('loads departments and employees on mount and displays them', async () => {
    (getDepartments as jest.Mock).mockResolvedValue({
      code: 200,
      departments: [
        { departmentId: 1, departmentName: 'Phòng Phát triển 1' },
        { departmentId: 2, departmentName: 'Phòng Quản lý chất lượng' },
      ],
    });

    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 1,
      employees: [
        {
          employeeId: 1,
          employeeName: 'Nguyễn Văn A',
          employeeBirthDate: '1990-01-01',
          departmentName: 'Phòng Phát triển 1',
          employeeEmail: 'nguyenvana@luvina.net',
          employeeTelephone: '0123456789',
          certificationName: 'N1',
          endDate: '2025-12-31',
          score: 160,
        },
      ],
    });

    render(<EmployeeListPage />);

    await waitFor(() => {
      expect(screen.getByText('Nguyễn Văn A')).toBeInTheDocument();
      expect(screen.getAllByText('Phòng Phát triển 1').length).toBeGreaterThan(0);
      expect(screen.getByText('nguyenvana@luvina.net')).toBeInTheDocument();
    });

    expect(screen.getByText('Phòng Quản lý chất lượng')).toBeInTheDocument();
  });

  it('triggers search with name and department filter', async () => {
    (getDepartments as jest.Mock).mockResolvedValue({
      code: 200,
      departments: [{ departmentId: 1, departmentName: 'DEV1' }],
    });

    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 0,
      employees: [],
    });

    render(<EmployeeListPage />);

    await waitFor(() => {
      expect(getEmployees).toHaveBeenCalledTimes(1);
    });

    const nameInput = screen.getByRole('textbox');
    fireEvent.change(nameInput, { target: { value: 'Nguyen' } });

    const searchBtn = screen.getByRole('button', { name: /検索/i });
    fireEvent.click(searchBtn);

    await waitFor(() => {
      expect(getEmployees).toHaveBeenCalledWith(
        expect.objectContaining({
          employeeName: 'Nguyen',
        })
      );
      expect(screen.getByText('検索条件に該当するユーザが見つかりません。')).toBeInTheDocument();
    });
  });

  it('toggles sort order when clicking header columns', async () => {
    (getDepartments as jest.Mock).mockResolvedValue({
      code: 200,
      departments: [],
    });

    (getEmployees as jest.Mock).mockResolvedValue({
      code: 200,
      totalRecords: 0,
      employees: [],
    });

    const { container } = render(<EmployeeListPage />);

    await waitFor(() => {
      expect(getEmployees).toHaveBeenCalledTimes(1);
    });

    const colName = container.querySelector('#col-sort-name');
    expect(colName).toBeInTheDocument();
    if (colName) fireEvent.click(colName);

    await waitFor(() => {
      expect(getEmployees).toHaveBeenCalledWith(
        expect.objectContaining({
          employeeNameOrder: 'DESC',
        })
      );
    });
  });
});
