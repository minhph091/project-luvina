/**
 * Copyright(C) 2026 Luvina
 * auth.ts - Validation Schemas for Authentication
 * 21/08/2026 Pham Van Minh
 */

import { z } from 'zod';
import { ERROR_MESSAGES } from '@/constants';

export const loginSchema = z.object({
  username: z.string().min(1, ERROR_MESSAGES.REQUIRED_USERNAME),
  password: z.string().min(1, ERROR_MESSAGES.REQUIRED_PASSWORD),
});

export type LoginFormValues = z.infer<typeof loginSchema>;
// Backward-compatibility alias
export type LoginForm = LoginFormValues;
