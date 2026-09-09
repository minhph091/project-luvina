'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM003: Employee Detail Page (会員情報詳細)
 * 21/08/2026 Pham Van Minh
 */

import React, { Suspense } from 'react';
import { useAuth } from '@/hooks/useAuth';
import { useAdm003 } from '@/hooks/useAdm003';
import Adm003 from '@/components/employees/Adm003';
import { COMMON_LABELS } from '@/constants';

function EmployeeDetailContent() {
  useAuth();
  const adm003Props = useAdm003();

  return <Adm003 {...adm003Props} />;
}

export default function EmployeeDetailPage() {
  return (
    <Suspense fallback={<div style={{ padding: '24px', textAlign: 'center', color: '#888' }}>{COMMON_LABELS.LOADING}</div>}>
      <EmployeeDetailContent />
    </Suspense>
  );
}
