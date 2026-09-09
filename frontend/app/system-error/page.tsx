'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - System Error Page
 * 09/09/2026 Pham Van Minh
 */

import React, { Suspense } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { APP_ROUTES, BUTTON_LABELS, SYSTEM_ERROR_MESSAGES } from '@/constants';

/**
 * Component hiển thị nội dung lỗi và nút OK xác nhận.
 */
function SystemErrorContent() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const errorMessage = searchParams?.get('message') || SYSTEM_ERROR_MESSAGES.SYSTEM_ERROR;

  const handleOk = () => {
    router.push(APP_ROUTES.EMPLOYEE_LIST);
  };

  return (
    <div className="notification-box">
      <h1 className="title note-err">{errorMessage}</h1>
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

/**
 * Trang System Error chuẩn theo tài liệu thiết kế và HTML mock.
 */
export default function SystemErrorPage() {
  return (
    <Suspense fallback={<div className="notification-box"><h1 className="title note-err">{SYSTEM_ERROR_MESSAGES.SYSTEM_ERROR}</h1></div>}>
      <SystemErrorContent />
    </Suspense>
  );
}
