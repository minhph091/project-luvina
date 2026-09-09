'use client';

/**
 * Copyright(C) 2026 Luvina
 * Adm006.tsx - Component thông báo hoàn thành thao tác nhân viên (ADM006)
 * 09/09/2026 Pham Van Minh
 */

import React from 'react';
import { BUTTON_LABELS } from '@/constants';

export interface Adm006Props {
  completeMessage: string;
  handleNavigateToList: () => void;
}

export const Adm006: React.FC<Adm006Props> = ({
  completeMessage,
  handleNavigateToList,
}) => {
  return (
    <div className="box-shadow">
      <div className="notification-box">
        <h1 className="msg-title">{completeMessage}</h1>
        <div className="notification-box-btn">
          <button
            type="button"
            onClick={handleNavigateToList}
            className="btn btn-primary btn-sm"
          >
            {BUTTON_LABELS.OK}
          </button>
        </div>
      </div>
    </div>
  );
};

export default Adm006;
