/**
 * Copyright(C) 2026 Luvina
 * Header.tsx - Header Layout Component
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import Link from 'next/link';
import Image from 'next/image';
import { APP_ROUTES } from '@/constants';

const Header: React.FC = () => {
  return (
    <nav className="nav-bar">
      <div className="content-main">
        <div className="d-flex">
          <Link href={APP_ROUTES.EMPLOYEE_LIST} className="navbar-brand">
            <Image src="/assets/images/Logo-Luvina.svg" title="Logo" alt="logo" width={100} height={50} />
          </Link>
          <h5 className="title-brand mr-auto">Luvina Software</h5>
          <ul className="navbar-nav flex-row d-flex">
            <li className="nav-item">
              <Link href={APP_ROUTES.EMPLOYEE_LIST}>トップ</Link>
            </li>
            <li className="nav-item">
              <Link href={APP_ROUTES.LOGOUT}>ログアウト</Link>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default Header;
