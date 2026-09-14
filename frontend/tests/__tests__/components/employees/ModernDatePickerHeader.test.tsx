/**
 * Copyright(C) 2026 Luvina
 * ModernDatePickerHeader.test.tsx - Unit tests for ModernDatePickerHeader component
 * 14/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import ModernDatePickerHeader from '@/components/employees/adm004/ModernDatePickerHeader';

describe('ModernDatePickerHeader Component', () => {
  const defaultProps = {
    date: new Date(2026, 8, 14), // September 14, 2026
    changeYear: jest.fn(),
    changeMonth: jest.fn(),
    decreaseMonth: jest.fn(),
    increaseMonth: jest.fn(),
    prevMonthButtonDisabled: false,
    nextMonthButtonDisabled: false,
    minYear: 1990,
    maxYear: 2030,
  };

  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders navigation buttons and selects with correct current values', () => {
    render(<ModernDatePickerHeader {...defaultProps} />);

    const prevBtn = screen.getByRole('button', { name: 'Previous Month' });
    const nextBtn = screen.getByRole('button', { name: 'Next Month' });
    const yearSelect = screen.getByRole('combobox', { name: 'Select Year' });
    const monthSelect = screen.getByRole('combobox', { name: 'Select Month' });

    expect(prevBtn).toBeInTheDocument();
    expect(nextBtn).toBeInTheDocument();
    expect(yearSelect).toHaveValue('2026');
    expect(monthSelect).toHaveValue('8'); // September is month index 8
  });

  test('calls decreaseMonth when previous button is clicked', () => {
    render(<ModernDatePickerHeader {...defaultProps} />);

    const prevBtn = screen.getByRole('button', { name: 'Previous Month' });
    fireEvent.click(prevBtn);

    expect(defaultProps.decreaseMonth).toHaveBeenCalledTimes(1);
  });

  test('calls increaseMonth when next button is clicked', () => {
    render(<ModernDatePickerHeader {...defaultProps} />);

    const nextBtn = screen.getByRole('button', { name: 'Next Month' });
    fireEvent.click(nextBtn);

    expect(defaultProps.increaseMonth).toHaveBeenCalledTimes(1);
  });

  test('calls changeYear when year select is changed', () => {
    render(<ModernDatePickerHeader {...defaultProps} />);

    const yearSelect = screen.getByRole('combobox', { name: 'Select Year' });
    fireEvent.change(yearSelect, { target: { value: '2020' } });

    expect(defaultProps.changeYear).toHaveBeenCalledWith(2020);
  });

  test('calls changeMonth when month select is changed', () => {
    render(<ModernDatePickerHeader {...defaultProps} />);

    const monthSelect = screen.getByRole('combobox', { name: 'Select Month' });
    fireEvent.change(monthSelect, { target: { value: '0' } }); // January

    expect(defaultProps.changeMonth).toHaveBeenCalledWith(0);
  });

  test('disables prev/next buttons when disabled props are true', () => {
    render(
      <ModernDatePickerHeader
        {...defaultProps}
        prevMonthButtonDisabled={true}
        nextMonthButtonDisabled={true}
      />
    );

    const prevBtn = screen.getByRole('button', { name: 'Previous Month' });
    const nextBtn = screen.getByRole('button', { name: 'Next Month' });

    expect(prevBtn).toBeDisabled();
    expect(nextBtn).toBeDisabled();
  });
});
