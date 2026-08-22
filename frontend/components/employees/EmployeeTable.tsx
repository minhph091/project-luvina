'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeeTable.tsx - Component bảng danh sách nhân viên
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import Link from 'next/link';
import { EmployeeItem, SortState } from '@/types/employee';
import { APP_ROUTES, ERROR_MESSAGES } from '@/constants';

interface EmployeeTableProps {
  employees: EmployeeItem[];
  loading: boolean;
  onSort: (column: keyof SortState) => void;
  getSortIcon: (column: keyof SortState) => string;
  formatDate: (dateString: string | null) => string;
}

/**
 * Component hiển thị bảng danh sách nhân viên với header hỗ trợ sắp xếp.
 */
export const EmployeeTable: React.FC<EmployeeTableProps> = ({
  employees,
  loading,
  onSort,
  getSortIcon,
  formatDate,
}) => {
  return (
    <div className="row row-table">
      <div className="css-grid-table box-shadow">
        {/* Header bảng với các cột có thể sort */}
        <div className="css-grid-table-header">
          <div>ID</div>
          <div
            id="col-sort-name"
            style={{ cursor: 'pointer' }}
            onClick={() => onSort('employeeNameOrder')}
          >
            氏名{getSortIcon('employeeNameOrder')}
          </div>
          <div>生年月日</div>
          <div>グループ</div>
          <div>メールアドレス</div>
          <div>電話番号</div>
          <div
            id="col-sort-cert"
            style={{ cursor: 'pointer' }}
            onClick={() => onSort('certificationNameOrder')}
          >
            日本語能力{getSortIcon('certificationNameOrder')}
          </div>
          <div
            id="col-sort-enddate"
            style={{ cursor: 'pointer' }}
            onClick={() => onSort('endDateOrder')}
          >
            失効日{getSortIcon('endDateOrder')}
          </div>
          <div>点数</div>
        </div>

        {/* Body bảng */}
        <div className="css-grid-table-body">
          {loading ? (
            /* Trạng thái đang tải dữ liệu */
            <div
              style={{
                gridColumn: '1 / -1',
                padding: '24px',
                textAlign: 'center',
                color: '#888',
              }}
            >
              読み込み中...
            </div>
          ) : employees.length === 0 ? (
            /* Trạng thái không có bản ghi phù hợp */
            <div
              id="msg-no-data"
              style={{
                gridColumn: '1 / -1',
                padding: '24px',
                textAlign: 'center',
                color: '#888',
              }}
            >
              {ERROR_MESSAGES.NO_DATA_FOUND}
            </div>
          ) : (
            /* Danh sách nhân viên */
            employees.map((employee) => (
              <div key={`row-${employee.employeeId}`} style={{ display: 'contents' }}>
                <div key={`id-${employee.employeeId}`} className="bor-l-none text-center">
                  <Link
                    href={`${APP_ROUTES.EMPLOYEE_DETAIL}?id=${employee.employeeId}`}
                    id={`link-emp-${employee.employeeId}`}
                  >
                    {employee.employeeId}
                  </Link>
                </div>
                <div key={`name-${employee.employeeId}`}>{employee.employeeName}</div>
                <div key={`birth-${employee.employeeId}`}>
                  {formatDate(employee.employeeBirthDate)}
                </div>
                <div key={`dept-${employee.employeeId}`}>{employee.departmentName}</div>
                <div key={`email-${employee.employeeId}`}>{employee.employeeEmail}</div>
                <div key={`tel-${employee.employeeId}`}>{employee.employeeTelephone}</div>
                <div key={`cert-${employee.employeeId}`}>
                  {employee.certificationName ?? ''}
                </div>
                <div key={`end-${employee.employeeId}`}>
                  {formatDate(employee.endDate)}
                </div>
                <div key={`score-${employee.employeeId}`}>
                  {employee.score ?? ''}
                </div>
              </div>
            ))
          )}
        </div>
      </div>
    </div>
  );
};

export default EmployeeTable;
