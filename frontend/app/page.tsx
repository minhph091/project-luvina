'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - Root Entry Page (ADM001 / ADM002 Redirect Handler)
 * 21/08/2026 Pham Van Minh
 */

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { getToken, isTokenExpired } from '@/lib/auth/token';
import { APP_ROUTES } from '@/constants';

/**
 * Trang chủ - Điều hướng người dùng dựa theo trạng thái đăng nhập.
 * Khi tồn tại token hợp lệ trong sessionStorage -> chuyển sang màn hình list nhân viên ADM002.
 * Khi không tồn tại token hoặc token hết hạn -> chuyển sang màn hình login ADM001.
 */
export default function HomePage() {
  const router = useRouter();

  useEffect(() => {
    const token = getToken();
    if (token && !isTokenExpired(token.accessToken)) {
      router.push(APP_ROUTES.EMPLOYEE_LIST);
    } else {
      router.push(APP_ROUTES.LOGIN);
    }
  }, [router]);

  return null;
}
