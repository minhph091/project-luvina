'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM006: Employee Action Complete Page
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useAdm006 } from '@/hooks/useAdm006';
import { BUTTON_LABELS } from '@/constants';

export default function EmployeeCompletePage() {
  useAuth();
  const { completeMessage, handleNavigateToList } = useAdm006();

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

