'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - Logout Page
 * 21/08/2026 Pham Van Minh
 */

import { useEffect } from 'react';
import { useRouter } from 'next/navigation';
import { removeToken } from '@/lib/auth/token';
import { clearEmployeeSearchState } from '@/lib/storage/employeeSearchState';
import { APP_ROUTES } from '@/constants';

export default function LogoutPage() {
  const router = useRouter();

  useEffect(() => {
    removeToken();
    clearEmployeeSearchState();
    router.push(APP_ROUTES.LOGIN);
  }, [router]);

  return <div>Logging out...</div>;
}
