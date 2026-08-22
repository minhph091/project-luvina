import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import EmployeeSearchForm from '@/components/employees/EmployeeSearchForm';
import EmployeeTable from '@/components/employees/EmployeeTable';
import EmployeePagination from '@/components/employees/EmployeePagination';
import { EmployeeItem } from '@/types/employee';

describe('Employee Sub-Components Tests', () => {
  describe('EmployeeSearchForm', () => {
    const mockDepartments = [
      { departmentId: 1, departmentName: 'Phòng Phát triển 1' },
      { departmentId: 2, departmentName: 'Phòng QA' },
    ];

    it('renders input, select, and buttons properly', () => {
      const handleNameChange = jest.fn();
      const handleDeptChange = jest.fn();
      const handleSubmit = jest.fn((e) => e.preventDefault());
      const handleAdd = jest.fn();

      render(
        <EmployeeSearchForm
          searchName="Nguyễn"
          searchDepartmentId={1}
          departments={mockDepartments}
          loading={false}
          onSearchNameChange={handleNameChange}
          onSearchDepartmentIdChange={handleDeptChange}
          onSearchSubmit={handleSubmit}
          onNavigateToAdd={handleAdd}
        />
      );

      const nameInput = screen.getByRole('textbox') as HTMLInputElement;
      expect(nameInput.value).toBe('Nguyễn');

      fireEvent.change(nameInput, { target: { value: 'Trần' } });
      expect(handleNameChange).toHaveBeenCalledWith('Trần');

      const select = screen.getByRole('combobox') as HTMLSelectElement;
      expect(select.value).toBe('1');

      fireEvent.change(select, { target: { value: '2' } });
      expect(handleDeptChange).toHaveBeenCalledWith(2);

      const addBtn = screen.getByRole('button', { name: '新規追加' });
      fireEvent.click(addBtn);
      expect(handleAdd).toHaveBeenCalled();
    });
  });

  describe('EmployeeTable', () => {
    const mockEmployees: EmployeeItem[] = [
      {
        employeeId: 101,
        employeeName: 'Nguyễn Văn B',
        employeeBirthDate: '1995-05-15',
        departmentName: 'Phòng Phát triển 1',
        employeeEmail: 'b@luvina.net',
        employeeTelephone: '0987654321',
        certificationName: 'N2',
        endDate: '2027-10-20',
        score: 140,
      },
    ];

    it('renders employee rows and handles sorting', () => {
      const handleSort = jest.fn();
      const getSortIcon = jest.fn(() => ' ▲ ▽');
      const formatDate = jest.fn((d) => (d ? d.replace(/-/g, '/') : ''));

      render(
        <EmployeeTable
          employees={mockEmployees}
          loading={false}
          onSort={handleSort}
          getSortIcon={getSortIcon}
          formatDate={formatDate}
        />
      );

      expect(screen.getByText('Nguyễn Văn B')).toBeInTheDocument();
      expect(screen.getByText('1995/05/15')).toBeInTheDocument();
      expect(screen.getByText('101')).toBeInTheDocument();

      const sortNameHeader = screen.getByText(/氏名/i);
      fireEvent.click(sortNameHeader);
      expect(handleSort).toHaveBeenCalledWith('employeeNameOrder');
    });

    it('renders empty message when no employees found', () => {
      render(
        <EmployeeTable
          employees={[]}
          loading={false}
          onSort={jest.fn()}
          getSortIcon={jest.fn()}
          formatDate={jest.fn()}
        />
      );

      expect(screen.getByText('検索条件に該当するユーザが見つかりません。')).toBeInTheDocument();
    });

    it('renders loading indicator when loading is true', () => {
      render(
        <EmployeeTable
          employees={[]}
          loading={true}
          onSort={jest.fn()}
          getSortIcon={jest.fn()}
          formatDate={jest.fn()}
        />
      );

      expect(screen.getByText('読み込み中...')).toBeInTheDocument();
    });
  });

  describe('EmployeePagination', () => {
    it('does not render if totalPages is 1 or less', () => {
      const { container } = render(
        <EmployeePagination
          currentPage={1}
          totalPages={1}
          loading={false}
          pageNumbers={[1]}
          onPageChange={jest.fn()}
        />
      );

      expect(container.firstChild).toBeNull();
    });

    it('renders pagination buttons and handles page navigation', () => {
      const handlePageChange = jest.fn();

      render(
        <EmployeePagination
          currentPage={2}
          totalPages={5}
          loading={false}
          pageNumbers={[1, 2, 3, 4, 5]}
          onPageChange={handlePageChange}
        />
      );

      const page3Btn = screen.getByRole('button', { name: '3' });
      fireEvent.click(page3Btn);
      expect(handlePageChange).toHaveBeenCalledWith(3);

      const prevBtn = screen.getByRole('button', { name: /previous page/i });
      fireEvent.click(prevBtn);
      expect(handlePageChange).toHaveBeenCalledWith(1);

      const nextBtn = screen.getByRole('button', { name: /next page/i });
      fireEvent.click(nextBtn);
      expect(handlePageChange).toHaveBeenCalledWith(3);
    });
  });
});
