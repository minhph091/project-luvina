/**
 * Copyright(C) 2026 Luvina
 * Adm006.test.tsx - Unit Tests for Adm006 Component
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import Adm006 from '@/components/employees/Adm006';
import { BUTTON_LABELS, SYSTEM_MESSAGES } from '@/constants';

describe('Adm006 Component', () => {
  const mockHandleNavigateToList = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
  });

  test('renders complete message properly', () => {
    render(
      <Adm006
        completeMessage={SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE}
        handleNavigateToList={mockHandleNavigateToList}
      />
    );

    expect(screen.getByText(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE)).toBeInTheDocument();
  });

  test('calls handleNavigateToList on OK button click', () => {
    render(
      <Adm006
        completeMessage={SYSTEM_MESSAGES.MSG002_USER_UPDATE_COMPLETE}
        handleNavigateToList={mockHandleNavigateToList}
      />
    );

    const okBtn = screen.getByRole('button', { name: BUTTON_LABELS.OK });
    fireEvent.click(okBtn);
    expect(mockHandleNavigateToList).toHaveBeenCalledTimes(1);
  });
});
