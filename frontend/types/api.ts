/**
 * Copyright(C) 2026 Luvina
 * api.ts - Type Definitions for Common API Responses & Errors
 * 21/08/2026 Pham Van Minh
 */

export interface ApiError {
  message: string;
  status: number;
  code?: string;
}

export interface PaginationParams {
  page: number;
  limit: number;
}

export interface PaginationMeta {
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}

