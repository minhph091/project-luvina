/**
 * Copyright(C) 2026 Luvina
 * departments.ts - API Service for Department Domain
 * 21/08/2026 Pham Van Minh
 */

import { apiClient } from './client';
import { DepartmentItem, GetDepartmentsApiResponse } from '@/types/department';

/**
 * Gọi API GET /departments để lấy toàn bộ danh sách phòng ban.
 *
 * @returns Promise chứa danh sách phòng ban từ backend.
 */
export async function getDepartments(): Promise<GetDepartmentsApiResponse> {
  const response = await apiClient.get<GetDepartmentsApiResponse>('/departments');
  return response.data;
}

export type { DepartmentItem, GetDepartmentsApiResponse };
