'use client';

/**
 * Copyright(C) 2026 Luvina
 * ModernDatePickerHeader.tsx - Custom header for modern DatePicker navigation
 * 14/09/2026 Pham Van Minh
 */

import React, { useMemo } from 'react';

export interface ModernDatePickerHeaderProps {
  date: Date;
  changeYear: (year: number) => void;
  changeMonth: (month: number) => void;
  decreaseMonth: () => void;
  increaseMonth: () => void;
  prevMonthButtonDisabled: boolean;
  nextMonthButtonDisabled: boolean;
  minYear?: number;
  maxYear?: number;
}

const JAPANESE_MONTHS = [
  '1月',
  '2月',
  '3月',
  '4月',
  '5月',
  '6月',
  '7月',
  '8月',
  '9月',
  '10月',
  '11月',
  '12月',
];

/**
 * Header tùy biến hiện đại cho DatePicker, tích hợp:
 * - Nút lùi/tiến tháng bằng icon SVG mượt mà.
 * - Dropdown chọn nhanh Năm và Tháng.
 */
export const ModernDatePickerHeader: React.FC<ModernDatePickerHeaderProps> = ({
  date,
  changeYear,
  changeMonth,
  decreaseMonth,
  increaseMonth,
  prevMonthButtonDisabled,
  nextMonthButtonDisabled,
  minYear = 1940,
  maxYear = new Date().getFullYear() + 10,
}) => {
  const currentYear = date.getFullYear();

  const years = useMemo(() => {
    const startYear = Math.min(minYear, currentYear);
    const endYear = Math.max(maxYear, currentYear);
    const list: number[] = [];
    for (let y = startYear; y <= endYear; y++) {
      list.push(y);
    }
    return list;
  }, [minYear, maxYear, currentYear]);

  return (
    <div className="modern-datepicker-header">
      <button
        type="button"
        onClick={decreaseMonth}
        disabled={prevMonthButtonDisabled}
        className="datepicker-nav-btn"
        aria-label="Previous Month"
      >
        <svg
          width="14"
          height="14"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <polyline points="15 18 9 12 15 6" />
        </svg>
      </button>

      <div className="datepicker-header-selectors">
        <select
          value={currentYear}
          onChange={({ target: { value } }) => changeYear(Number(value))}
          className="datepicker-select datepicker-year-select"
          aria-label="Select Year"
        >
          {years.map((y) => (
            <option key={y} value={y}>
              {y}年
            </option>
          ))}
        </select>

        <select
          value={date.getMonth()}
          onChange={({ target: { value } }) => changeMonth(Number(value))}
          className="datepicker-select datepicker-month-select"
          aria-label="Select Month"
        >
          {JAPANESE_MONTHS.map((m, idx) => (
            <option key={m} value={idx}>
              {m}
            </option>
          ))}
        </select>
      </div>

      <button
        type="button"
        onClick={increaseMonth}
        disabled={nextMonthButtonDisabled}
        className="datepicker-nav-btn"
        aria-label="Next Month"
      >
        <svg
          width="14"
          height="14"
          viewBox="0 0 24 24"
          fill="none"
          stroke="currentColor"
          strokeWidth="2.5"
          strokeLinecap="round"
          strokeLinejoin="round"
        >
          <polyline points="9 18 15 12 9 6" />
        </svg>
      </button>
    </div>
  );
};

export default ModernDatePickerHeader;
