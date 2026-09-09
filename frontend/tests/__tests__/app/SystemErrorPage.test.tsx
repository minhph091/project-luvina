/**
 * Copyright(C) 2026 Luvina
 * SystemErrorPage.test.tsx - Unit Tests for System Error and Not Found Pages
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { render, screen, fireEvent } from '@testing-library/react';
import { useRouter, useSearchParams } from 'next/navigation';
import SystemErrorPage from '@/app/system-error/page';
import NotFoundPage from '@/app/not-found';
import { APP_ROUTES, BUTTON_LABELS, SYSTEM_ERROR_MESSAGES } from '@/constants';

jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
  useSearchParams: jest.fn(),
}));

describe('SystemErrorPage', () => {
  const mockPush = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    (useRouter as jest.Mock).mockReturnValue({ push: mockPush });
    (useSearchParams as jest.Mock).mockReturnValue({
      get: jest.fn().mockReturnValue(null),
    });
  });

  test('renders default error message "System Error" when no query param is provided', () => {
    render(<SystemErrorPage />);
    expect(screen.getByText(SYSTEM_ERROR_MESSAGES.SYSTEM_ERROR)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.OK })).toBeInTheDocument();
  });

  test('renders custom error message provided in search params', () => {
    (useSearchParams as jest.Mock).mockReturnValue({
      get: jest.fn().mockImplementation((key: string) => {
        if (key === 'message') return 'Custom Error Message';
        return null;
      }),
    });

    render(<SystemErrorPage />);
    expect(screen.getByText('Custom Error Message')).toBeInTheDocument();
  });

  test('navigates to ADM002 employee list when OK button is clicked', () => {
    render(<SystemErrorPage />);
    const okButton = screen.getByRole('button', { name: BUTTON_LABELS.OK });
    fireEvent.click(okButton);
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_LIST);
  });
});

describe('NotFoundPage', () => {
  const mockPush = jest.fn();

  beforeEach(() => {
    jest.clearAllMocks();
    (useRouter as jest.Mock).mockReturnValue({ push: mockPush });
  });

  test('renders "Page not found" message and OK button', () => {
    render(<NotFoundPage />);
    expect(screen.getByText(SYSTEM_ERROR_MESSAGES.PAGE_NOT_FOUND)).toBeInTheDocument();
    expect(screen.getByRole('button', { name: BUTTON_LABELS.OK })).toBeInTheDocument();
  });

  test('navigates to ADM002 when OK button is clicked', () => {
    render(<NotFoundPage />);
    const okButton = screen.getByRole('button', { name: BUTTON_LABELS.OK });
    fireEvent.click(okButton);
    expect(mockPush).toHaveBeenCalledWith(APP_ROUTES.EMPLOYEE_LIST);
  });
});
