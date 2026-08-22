/**
 * Copyright(C) 2026 Luvina
 * client.ts - Centralized Axios API Client Instance & Interceptors
 * 21/08/2026 Pham Van Minh
 */

import axios, { AxiosInstance } from 'axios';
import { STORAGE_KEYS, APP_ROUTES } from '@/constants';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8085';

/**
 * Axios instance dùng chung cho toàn bộ ứng dụng.
 */
const apiClient: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Cấu hình request và response interceptor cho Axios client.
 *
 * @param client Axios instance cần thiết lập interceptors
 */
export function setupInterceptors(client: AxiosInstance): void {
  client.interceptors.request.use(
    (config) => {
      const token = typeof window !== 'undefined'
        ? sessionStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN)
        : null;
      if (token && config.headers) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    },
    (error) => {
      return Promise.reject(error);
    }
  );

  client.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response?.status === 401) {
        if (typeof window !== 'undefined') {
          sessionStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN);
          sessionStorage.removeItem(STORAGE_KEYS.TOKEN_TYPE);
          window.location.href = APP_ROUTES.LOGIN;
        }
      }
      return Promise.reject(error);
    }
  );
}

setupInterceptors(apiClient);

export { apiClient };
