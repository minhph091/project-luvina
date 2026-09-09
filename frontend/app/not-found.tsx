'use client';

/**
 * Copyright(C) 2026 Luvina
 * not-found.tsx - Custom Not Found Page (ER022)
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { useRouter } from 'next/navigation';
import { APP_ROUTES, BUTTON_LABELS, SYSTEM_ERROR_MESSAGES } from '@/constants';

export default function NotFound() {
  const router = useRouter();

  const handleOk = () => {
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="notification-box">
      <h1 className="title note-err">{SYSTEM_ERROR_MESSAGES.PAGE_NOT_FOUND}</h1>
      <div className="notification-box-btn">
        <button
          type="button"
          className="btn btn-primary btn-sm"
          onClick={handleOk}
        >
          {BUTTON_LABELS.OK}
        </button>
      </div>
    </div>
  );
}
