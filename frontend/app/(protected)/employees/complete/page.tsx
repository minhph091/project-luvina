'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM006: Employee Action Complete Page
 * 21/08/2026 Pham Van Minh
 */

import React, { useEffect, useState } from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { APP_ROUTES, BUTTON_LABELS, COMPLETE_ACTION_TYPES, SYSTEM_MESSAGES } from '@/constants';
import { clearEmployeeSearchState } from '@/lib/storage/employeeSearchState';
import { getEmployeeCompleteAction, clearEmployeeCompleteAction } from '@/lib/storage/employeeCompleteState';

export default function EmployeeCompletePage() {
  useAuth();
  const router = useRouter();
  const [completeMessage, setCompleteMessage] = useState<string>(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE);

  useEffect(() => {
    const action = getEmployeeCompleteAction();
    if (action === COMPLETE_ACTION_TYPES.EDIT) {
      setCompleteMessage(SYSTEM_MESSAGES.MSG002_USER_UPDATE_COMPLETE);
    } else if (action === COMPLETE_ACTION_TYPES.DELETE) {
      setCompleteMessage(SYSTEM_MESSAGES.MSG003_USER_DELETE_COMPLETE);
    } else {
      setCompleteMessage(SYSTEM_MESSAGES.MSG001_USER_ADD_COMPLETE);
    }
  }, []);

  const handleNavigateToList = () => {
    clearEmployeeCompleteAction();
    clearEmployeeSearchState();
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="box-shadow">
      <div className="notification-box">
        <h1 className="msg-title">{completeMessage}</h1>
        <div className="notification-box-btn">
          <button type="button" onClick={handleNavigateToList} className="btn btn-primary btn-sm">
            {BUTTON_LABELS.OK}
          </button>
        </div>
      </div>
    </div>
  );
}

