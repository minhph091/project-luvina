'use client';

/**
 * Copyright(C) 2026 Luvina
 * page.tsx - ADM001: Login Page
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import Image from 'next/image';
import { useGuest } from '@/hooks/useAuth';
import LoginForm from '@/components/auth/LoginForm';

export default function LoginPage() {
  useGuest();

  return (
    <div className="limiter">
      <div className="container-login100">
        <div className="wrap-login100">
          <div className="login100-pic js-tilt" data-tilt>
            <Image src="/assets/images/img-01.png" alt="IMG" width={400} height={400} />
          </div>
          <LoginForm />
        </div>
      </div>
    </div>
  );
}
