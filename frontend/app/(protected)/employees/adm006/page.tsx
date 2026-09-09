'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM006: Employee Action Complete Page
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useAdm006 } from '@/hooks/useAdm006';
import Adm006 from '@/components/employees/Adm006';

export default function EmployeeCompletePage() {
  useAuth();
  const adm006Props = useAdm006();

  return <Adm006 {...adm006Props} />;
}
