/**
 * Copyright(C) 2026 Luvina
 * token.ts - Authentication Token Storage & Validation Utilities
 * 21/08/2026 Phạm Văn Minh
 */

import { STORAGE_KEYS } from '@/constants';
import { AuthToken } from '@/types/auth';
import { clearEmployeeSearchState } from '@/lib/storage/employeeSearchState';

/**
 * Lưu access token và token type vào sessionStorage.
 *
 * @param token Chuỗi access token (JWT)
 * @param tokenType Loại token (ví dụ: "Bearer")
 */
export function storeToken(token: string, tokenType: string): void {
  sessionStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, token);
  sessionStorage.setItem(STORAGE_KEYS.TOKEN_TYPE, tokenType);
}

/**
 * Lấy access token và token type từ sessionStorage.
 *
 * @returns Đối tượng AuthToken nếu tồn tại, ngược lại trả về null.
 */
export function getToken(): AuthToken | null {
  const accessToken = sessionStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN);
  const tokenType = sessionStorage.getItem(STORAGE_KEYS.TOKEN_TYPE);

  if (accessToken && tokenType) {
    return { accessToken, tokenType };
  }
  return null;
}

/**
 * Xóa token xác thực khỏi sessionStorage.
 */
export function removeToken(): void {
  sessionStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN);
  sessionStorage.removeItem(STORAGE_KEYS.TOKEN_TYPE);
  clearEmployeeSearchState();
}

/**
 * Kiểm tra xem JWT token đã hết hạn hay chưa.
 *
 * @param token Chuỗi access token (JWT)
 * @returns true nếu token đã hết hạn hoặc không hợp lệ, ngược lại trả về false.
 */
export function isTokenExpired(token: string): boolean {
  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    return Date.now() >= payload.exp * 1000;
  } catch {
    return true;
  }
}
