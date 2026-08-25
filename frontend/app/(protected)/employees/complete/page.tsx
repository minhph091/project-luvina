'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM006: Employee Action Complete Page
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { APP_ROUTES, BUTTON_LABELS, SYSTEM_MESSAGES } from '@/constants';

export default function EmployeeCompletePage() {
  useAuth();
  const router = useRouter();

  const handleNavigateToList = () => {
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="box-shadow">
      <div className="notification-box">
        <h1 className="msg-title">
          {`${SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE} or ${SYSTEM_MESSAGES.MSG002_USER_UPDATE_COMPLETE} or ${SYSTEM_MESSAGES.MSG003_USER_DELETE_COMPLETE}`}
        </h1>
        <div className="notification-box-btn">
          <button type="button" onClick={handleNavigateToList} className="btn btn-primary btn-sm">{BUTTON_LABELS.OK}</button>
        </div>
      </div>
    </div>
  );
}
