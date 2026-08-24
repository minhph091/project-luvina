'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM006: Employee Action Complete Page
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useRouter } from 'next/navigation';
import { APP_ROUTES } from '@/constants';

export default function EmployeeCompletePage() {
  useAuth();
  const router = useRouter();

  const handleNavigateToList = () => {
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="box-shadow">
      <div className="notification-box">
        <h1 className="msg-title">ユーザの登録が完了しました。 or ユーザの更新が完了しました。 or ユーザの削除が完了しました。</h1>
        <div className="notification-box-btn">
          <button type="button" onClick={handleNavigateToList} className="btn btn-primary btn-sm">OK</button>
        </div>
      </div>
    </div>
  );
}
