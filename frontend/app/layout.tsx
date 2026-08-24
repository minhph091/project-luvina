'use client';

/**
 * Copyright(C) 2026 Luvina
 * layout.tsx - Root Application Layout
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import './globals.css';
import Header from '../components/layout/Header';
import Footer from '../components/layout/Footer';
import { usePathname } from 'next/navigation';
import { APP_ROUTES } from '@/constants';

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const pathname = usePathname();
  const showHeaderFooter = !pathname?.includes(APP_ROUTES.LOGIN);

  return (
    <html lang="ja">
      <body>
        {showHeaderFooter ? (
          <main>
            <div className="container">
              <Header />
              <div className="content">
                <div className="content-main">{children}</div>
              </div>
              <Footer />
            </div>
          </main>
        ) : (
          children
        )}
      </body>
    </html>
  );
}
