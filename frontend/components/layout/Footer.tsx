/**
 * Copyright(C) 2026 Luvina
 * Footer.tsx - Footer Layout Component
 * 21/08/2026 Phạm Văn Minh
 */

import React from 'react';
import { COMMON_LABELS } from '@/constants';

const Footer: React.FC = () => {
  return (
    <footer className="footer">
      <div className="content-main">
        <p>{COMMON_LABELS.COPYRIGHT}</p>
      </div>
    </footer>
  );
};

export default Footer;
