'use client';

/**
 * Copyright(C) 2026 Luvina
 * EmployeePagination.tsx - Component điều khiển phân trang danh sách nhân viên
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';

interface EmployeePaginationProps {
  currentPage: number;
  totalPages: number;
  loading: boolean;
  pageNumbers: (number | '...')[];
  onPageChange: (page: number) => void;
}

/**
 * Component hiển thị thanh phân trang theo thiết kế:
 * - Luôn hiển thị button trang đầu và trang cuối.
 * - Hiển thị trang hiện tại, trang trước và sau trang hiện tại (dấu ... khi cần).
 * - Nút < bị disable ở trang 1, nút > bị disable ở trang cuối.
 */
export const EmployeePagination: React.FC<EmployeePaginationProps> = ({
  currentPage,
  totalPages,
  loading,
  pageNumbers,
  onPageChange,
}) => {
  if (loading || totalPages <= 1) {
    return null;
  }

  return (
    <div className="pagin">
      {/* Nút Prev (<) */}
      <button
        id="btn-prev"
        className={`btn btn-sm btn-pre btn-falcon-default${currentPage === 1 ? ' btn-disabled' : ''}`}
        onClick={() => onPageChange(currentPage - 1)}
        disabled={currentPage === 1}
        aria-label="Previous page"
      >
        <svg
          className="svg-inline--fa fa-chevron-left fa-w-10"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 320 512"
        >
          <path
            fill="currentColor"
            d="M34.52 239.03L228.87 44.69c9.37-9.37 24.57-9.37 33.94 0l22.67 22.67c9.36 9.36 9.37 24.52.04 33.9L131.49 256l154.02 154.75c9.34 9.38 9.32 24.54-.04 33.9l-22.67 22.67c-9.37 9.37-24.57 9.37-33.94 0L34.52 272.97c-9.37-9.37-9.37-24.57 0-33.94z"
          />
        </svg>
      </button>

      {/* Danh sách số trang */}
      {pageNumbers.map((pageItem, index) =>
        pageItem === '...' ? (
          <span
            key={`ellipsis-${index}`}
            className="btn btn-sm btn-falcon-default"
            style={{ cursor: 'default' }}
          >
            <svg
              className="svg-inline--fa fa-ellipsis-h fa-w-16"
              aria-hidden="true"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 512 512"
            >
              <path
                fill="currentColor"
                d="M328 256c0 39.8-32.2 72-72 72s-72-32.2-72-72 32.2-72 72-72 72 32.2 72 72zm104-72c-39.8 0-72 32.2-72 72s32.2 72 72 72 72-32.2 72-72-32.2-72-72-72zm-352 0c-39.8 0-72 32.2-72 72s32.2 72 72 72 72-32.2 72-72-32.2-72-72-72z"
              />
            </svg>
          </span>
        ) : (
          <button
            key={`page-${pageItem}`}
            id={`btn-page-${pageItem}`}
            className={`btn btn-sm btn-falcon-default${pageItem === currentPage ? ' text-primary' : ''}`}
            onClick={() => onPageChange(pageItem as number)}
            style={pageItem === currentPage ? { fontWeight: 'bold' } : {}}
          >
            {pageItem}
          </button>
        )
      )}

      {/* Nút Next (>) */}
      <button
        id="btn-next"
        className={`btn btn-sm btn-next btn-falcon-default${currentPage === totalPages ? ' btn-disabled' : ''}`}
        onClick={() => onPageChange(currentPage + 1)}
        disabled={currentPage === totalPages}
        aria-label="Next page"
      >
        <svg
          className="svg-inline--fa fa-chevron-right fa-w-10"
          aria-hidden="true"
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 320 512"
        >
          <path
            fill="currentColor"
            d="M285.476 272.971L91.132 467.314c-9.373 9.373-24.569 9.373-33.941 0l-22.667-22.667c-9.357-9.357-9.375-24.522-.04-33.901L188.505 256 34.484 101.255c-9.335-9.379-9.317-24.544.04-33.901l22.667-22.667c9.373-9.373 24.569-9.373 33.941 0L285.475 239.03c9.373 9.372 9.373 24.568.001 33.941z"
          />
        </svg>
      </button>
    </div>
  );
};

export default EmployeePagination;
