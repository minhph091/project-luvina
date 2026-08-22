/**
 * Copyright(C) 2026 Luvina
 * useAuth.ts - Custom Hooks for Authentication & Route Protection
 * 21/08/2026 Pham Van Minh
 */

import { useEffect, useState } from 'react';
import { useRouter } from 'next/navigation';
import { getToken, isTokenExpired, storeToken, removeToken } from '@/lib/auth/token';
import { loginUser } from '@/lib/api/auth';
import { LoginRequest } from '@/types/auth';
import { APP_ROUTES, ERROR_MESSAGES } from '@/constants';

/**
 * Hook bảo vệ các trang yêu cầu đăng nhập.
 * Chuyển hướng người dùng về trang /login nếu chưa đăng nhập hoặc token đã hết hạn.
 */
export const useAuth = (): void => {
  const router = useRouter();

  useEffect(() => {
    const token = getToken();
    if (!token || isTokenExpired(token?.accessToken)) {
      router.push(APP_ROUTES.LOGIN);
    }
  }, [router]);
};

/**
 * Hook dành cho trang đăng nhập/khách.
 * Chuyển hướng người dùng vào trang danh sách nếu đã đăng nhập và token còn hạn.
 */
export const useGuest = (): void => {
  const router = useRouter();

  useEffect(() => {
    const token = getToken();
    if (token && !isTokenExpired(token?.accessToken)) {
      router.push(APP_ROUTES.EMPLOYEE_LIST);
    }
  }, [router]);
};

interface UseLoginReturn {
  handleLogin: (credentials: LoginRequest) => Promise<boolean>;
  loading: boolean;
  loginErrorMessage: string | null;
  setLoginErrorMessage: (message: string | null) => void;
}

/**
 * Custom Hook xử lý luồng đăng nhập (gọi API -> lưu token -> chuyển hướng).
 *
 * @returns Object chứa hàm handleLogin, trạng thái loading và error message.
 */
export const useLogin = (): UseLoginReturn => {
  const router = useRouter();
  const [loading, setLoading] = useState<boolean>(false);
  const [loginErrorMessage, setLoginErrorMessage] = useState<string | null>(null);

  const handleLogin = async (credentials: LoginRequest): Promise<boolean> => {
    setLoading(true);
    setLoginErrorMessage(null);
    try {
      const response = await loginUser(credentials);
      storeToken(response.accessToken, response.tokenType);
      router.push(APP_ROUTES.EMPLOYEE_LIST);
      return true;
    } catch {
      setLoginErrorMessage(ERROR_MESSAGES.LOGIN_FAILED);
      return false;
    } finally {
      setLoading(false);
    }
  };

  return {
    handleLogin,
    loading,
    loginErrorMessage,
    setLoginErrorMessage,
  };
};

export { storeToken, removeToken, getToken };
