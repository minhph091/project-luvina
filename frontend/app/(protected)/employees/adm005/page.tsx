'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM005: Employee Confirm Page (会員情報確認)
 * 05/09/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useAdm005 } from '@/hooks/useAdm005';
import Adm005 from '@/components/employees/Adm005';

export default function EmployeeConfirmPage() {
  useAuth();
  const adm005Props = useAdm005();

  return <Adm005 {...adm005Props} />;
}
