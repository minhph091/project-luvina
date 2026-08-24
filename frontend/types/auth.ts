/**
 * Copyright(C) 2026 Luvina
 * auth.ts - Type Definitions for Authentication
 * 21/08/2026 Pham Van Minh
 */

/**
 * Dữ liệu gửi lên API đăng nhập.
 */
export interface LoginRequest {
  username: string;
  password: string;
}

/**
 * Phản hồi từ API đăng nhập trả về từ backend.
 */
export interface LoginResponse {
  accessToken: string;
  tokenType: string;
}

/**
 * Cặp token xác thực lưu trong client.
 */
export interface AuthToken {
  accessToken: string;
  tokenType: string;
}

/**
 * Payload giải mã từ JWT token.
 */
export interface TokenPayload {
  exp: number;
  [key: string]: unknown;
}
