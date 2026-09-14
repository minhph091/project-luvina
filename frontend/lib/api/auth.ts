/**
 * Copyright(C) 2026 Luvina
 * auth.ts - API Service for Authentication
 * 21/08/2026 Pham Van Minh
 */

import { apiClient } from './client';
import { API_ENDPOINTS } from '@/constants';
import { LoginRequest, LoginResponse } from '@/types/auth';

/**
 * Gửi yêu cầu đăng nhập lên backend.
 *
 * @param credentials Thông tin tài khoản và mật khẩu của người dùng.
 * @returns Promise chứa dữ liệu token trả về từ máy chủ.
 */
export async function loginUser(credentials: LoginRequest): Promise<LoginResponse> {
  const response = await apiClient.post<LoginResponse>(API_ENDPOINTS.AUTH.LOGIN, credentials);
  return response.data;
}
