'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - Logout Page
 * 21/08/2026 Pham Van Minh
 */

import { useLogout } from '@/hooks/useAuth';

export default function LogoutPage() {
  useLogout();

  return <div>Logging out...</div>;
}
