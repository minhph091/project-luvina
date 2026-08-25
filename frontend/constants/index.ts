/**
 * Copyright(C) 2026 Luvina
 * index.ts - Common Application Constants
 * 21/08/2026 Phạm Văn Minh
 */

/**
 * Số bản ghi mặc định trên mỗi trang danh sách nhân viên theo tài liệu thiết kế.
 */
export const DEFAULT_PAGE_SIZE = 20;

/**
 * Key lưu trữ trong sessionStorage cho token xác thực.
 */
export const STORAGE_KEYS = {
  ACCESS_TOKEN: 'access_token',
  TOKEN_TYPE: 'token_type',
} as const;

/**
 * Các đường dẫn (routes) điều hướng trong ứng dụng.
 */
export const APP_ROUTES = {
  HOME: '/',
  LOGIN: '/login',
  LOGOUT: '/logout',
  EMPLOYEE_LIST: '/employees/adm002',
  EMPLOYEE_DETAIL: '/employees/adm003',
  EMPLOYEE_EDIT: '/employees/edit',
  EMPLOYEE_CONFIRM: '/employees/confirm',
  EMPLOYEE_COMPLETE: '/employees/complete',
} as const;

/**
 * Các câu thông báo lỗi mặc định.
 */
export const ERROR_MESSAGES = {
  FETCH_EMPLOYEES_FAILED: '従業員を取得できません',
  FETCH_DEPARTMENTS_FAILED: '部門を取得できません',
  LOGIN_FAILED: 'ログインに失敗しました。アカウント名またはパスワードを確認してください。',
  REQUIRED_USERNAME: 'Username is required',
  REQUIRED_PASSWORD: 'Password is required',
  NO_DATA_FOUND: '検索条件に該当するユーザが見つかりません。',
} as const;

/**
 * Thứ tự sắp xếp.
 */
export const SORT_ORDERS = {
  ASC: 'ASC',
  DESC: 'DESC',
} as const;

export * from './labels';
