/**
 * Copyright(C) 2026 Luvina
 * labels.ts - Định nghĩa tập trung Label & Message Constants
 * 25/08/2026 Phạm Văn Minh
 */

/**
 * Nhãn các nút thao tác trên toàn bộ hệ thống (BUTTON_LABELS)
 */
export const BUTTON_LABELS = {
  SEARCH: '検索',
  ADD_NEW: '新規追加',
  EDIT: '編集',
  DELETE: '削除',
  BACK: '戻る',
  CONFIRM: '確認',
  OK: 'OK',
  LOGIN: 'ログイン',
  LOGGING_IN: 'ログイン中...',
  LOGOUT: 'ログアウト',
  TOP: 'トップ',
} as const;

/**
 * Tên trường form & tiêu đề cột bảng (FIELD_LABELS)
 */
export const FIELD_LABELS = {
  ID: 'ID',
  ACCOUNT_NAME: 'アカウント名',
  ACCOUNT_NAME_COLON: 'アカウント名:',
  GROUP: 'グループ',
  GROUP_COLON: 'グループ:',
  NAME: '氏名',
  NAME_COLON: '氏名:',
  KATAKANA_NAME: 'カタカナ氏名',
  KATAKANA_NAME_COLON: 'カタカナ氏名:',
  BIRTHDAY: '生年月日',
  BIRTHDAY_COLON: '生年月日:',
  EMAIL: 'メールアドレス',
  EMAIL_COLON: 'メールアドレス:',
  TEL: '電話番号',
  TEL_COLON: '電話番号:',
  JAPANESE_LEVEL: '日本語能力',
  CERTIFICATION: '資格',
  CERTIFICATION_COLON: '資格:',
  START_DATE: '資格交付日',
  START_DATE_COLON: '資格交付日:',
  END_DATE: '失効日',
  END_DATE_COLON: '失効日:',
  SCORE: '点数',
  SCORE_COLON: '点数:',
  PASSWORD: 'パスワード',
  PASSWORD_COLON: 'パスワード:',
  PASSWORD_CONFIRM: 'パスワード（確認）',
  PASSWORD_CONFIRM_COLON: 'パスワード（確認）:',
} as const;

/**
 * Nhãn thông dụng, placeholder & bản quyền (COMMON_LABELS)
 */
export const COMMON_LABELS = {
  ALL: '全て',
  SELECT_DEFAULT: '選択してください',
  LOADING: '読み込み中...',
  BRAND_NAME: 'Luvina Software',
  COPYRIGHT: 'Copyright © 2026 ルビナソフトウエア株式会社. All rights reserved.',
  DATE_PLACEHOLDER: 'yyyy/MM/dd',
  EMPTY_ERROR_BOX: 'Hiển thị lỗi chung lại đây',
} as const;

/**
 * Tiêu đề màn hình và khối thông tin (PAGE_TITLES)
 */
export const PAGE_TITLES = {
  SEARCH_MEMBERS_DESCRIPTION: '会員名称で会員を検索します。検索条件無しの場合は全て表示されます。',
  INFO_CONFIRM: '情報確認',
  ADD_EMPLOYEE: '会員情報追加',
  EDIT_EMPLOYEE: '会員情報編集',
  CONFIRM_DESCRIPTION: '入力された情報をＯＫボタンクリックでＤＢへ保存してください',
} as const;

/**
 * Thông điệp hệ thống / Thông báo hoàn thành theo mã thiết kế (SYSTEM_MESSAGES)
 */
export const SYSTEM_MESSAGES = {
  MSG001_USER_ADD_COMPLETE: 'ユーザの登録が完了しました。',
  MSG002_USER_UPDATE_COMPLETE: 'ユーザの更新が完了しました。',
  MSG003_USER_DELETE_COMPLETE: 'ユーザの削除が完了しました。',
  MSG004_DELETE_CONFIRM: '削除しますが、よろしいでしょうか。',
  MSG005_NO_DATA_FOUND: '検索条件に該当するユーザが見つかりません。',
} as const;

/**
 * Mã lỗi và định dạng thông báo validation theo tài liệu thiết kế (VALIDATION_MESSAGES / ERROR_CODES)
 */
export const ERROR_CODES = {
  ER001: 'ER001',
  ER002: 'ER002',
  ER003: 'ER003',
  ER004: 'ER004',
  ER005: 'ER005',
  ER006: 'ER006',
  ER007: 'ER007',
  ER008: 'ER008',
  ER009: 'ER009',
  ER010: 'ER010',
  ER011: 'ER011',
  ER012: 'ER012',
  ER013: 'ER013',
  ER014: 'ER014',
  ER015: 'ER015',
  ER016: 'ER016',
  ER017: 'ER017',
  ER018: 'ER018',
  ER019: 'ER019',
} as const;

export const VALIDATION_MESSAGES = {
  ER001_REQUIRED_INPUT: (field: string) => `「${field}」を入力してください`,
  ER002_REQUIRED_SELECT: (field: string) => `「${field}」を選択してください`,
  ER003_ALREADY_EXISTS: (field: string) => `「${field}」は既に存在しています。`,
  ER004_NOT_EXISTS: (field: string) => `「${field}」は存在していません。`,
  ER005_INVALID_FORMAT: (field: string, format = 'email') => `「${field}」を${format}形式で入力してください`,
  ER006_MAX_LENGTH: (field: string, max: number) => `${max}桁以内の「${field}」を入力してください`,
  ER007_LENGTH_RANGE: (field: string, min: number, max: number) => `「${field}」を${min}<= 桁数、<=${max} 桁で入力してください`,
  ER008_BYTE_HALFSIZE: (field: string) => `「${field}」に半角英数を入力してください`,
  ER008_KATAKANA: (field: string) => `「${field}」をカタカナで入力してください`,
  ER009_KATAKANA: (field: string) => `「${field}」をカタカナで入力してください`,
  ER010_HIRAGANA: (field: string) => `「${field}」をひらがなで入力してください`,
  ER011_INVALID_DATE: (field: string) => `「${field}」は無効になっています。`,
  ER012_DATE_AFTER: (fieldEnd: string = '失効日', fieldStart: string = '資格交付日') => `「${fieldEnd}」は「${fieldStart}」より未来の日で入力してください。`,
  ER013_USER_NOT_FOUND: '該当するユーザは存在していません。',
  ER014_USER_NOT_FOUND: '該当するユーザは存在していません。',
  ER015_SYSTEM_ERROR: 'システムエラーが発生しました。',
  ER016_LOGIN_FAILED: '「アカウント名」または「パスワード」は不正です。',
  ER017_PASSWORD_MISMATCH: '「パスワード（確認）」が不正です。',
  ER018_HALF_NUMBER: (field: string) => `「${field}」は半角で入力してください。`,
  ER019_HALF_ALPHANUMERIC: (field: string = 'アカウント名') => `[${field}]は(a-z, A-Z, 0-9 と _)の桁のみです。最初の桁は数字ではない。`,
} as const;

/**
 * Định dạng thông báo lỗi trả về từ API backend theo mã lỗi và tham số.
 *
 * @param code Mã lỗi từ API (ER001 ~ ER023).
 * @param params Danh sách tham số đính kèm mã lỗi.
 * @returns Chuỗi thông báo lỗi tiếng Nhật tương ứng.
 */
export function formatApiErrorMessage(code?: string, params: string[] = []): string {
  if (!code) return VALIDATION_MESSAGES.ER015_SYSTEM_ERROR;
  const p0 = params[0] || '';
  const p1 = params[1] || '';
  const p2 = params[2] || '';

  switch (code) {
    case ERROR_CODES.ER001:
      return VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(p0);
    case ERROR_CODES.ER002:
      return VALIDATION_MESSAGES.ER002_REQUIRED_SELECT(p0);
    case ERROR_CODES.ER003:
      return VALIDATION_MESSAGES.ER003_ALREADY_EXISTS(p0);
    case ERROR_CODES.ER004:
      return VALIDATION_MESSAGES.ER004_NOT_EXISTS(p0);
    case ERROR_CODES.ER005:
      return VALIDATION_MESSAGES.ER005_INVALID_FORMAT(p0, p1);
    case ERROR_CODES.ER006:
      return VALIDATION_MESSAGES.ER006_MAX_LENGTH(p0, Number(p1) || 125);
    case ERROR_CODES.ER007:
      return VALIDATION_MESSAGES.ER007_LENGTH_RANGE(p0, Number(p1) || 8, Number(p2) || 50);
    case ERROR_CODES.ER008:
      return VALIDATION_MESSAGES.ER008_BYTE_HALFSIZE(p0);
    case ERROR_CODES.ER009:
      return VALIDATION_MESSAGES.ER009_KATAKANA(p0);
    case ERROR_CODES.ER011:
      return VALIDATION_MESSAGES.ER011_INVALID_DATE(p0);
    case ERROR_CODES.ER012:
      return VALIDATION_MESSAGES.ER012_DATE_AFTER(p0 || '失効日', p1 || '資格交付日');
    case ERROR_CODES.ER013:
      return VALIDATION_MESSAGES.ER013_USER_NOT_FOUND;
    case ERROR_CODES.ER014:
      return VALIDATION_MESSAGES.ER014_USER_NOT_FOUND;
    case ERROR_CODES.ER015:
      return VALIDATION_MESSAGES.ER015_SYSTEM_ERROR;
    case ERROR_CODES.ER018:
      return VALIDATION_MESSAGES.ER018_HALF_NUMBER(p0);
    case ERROR_CODES.ER019:
      return VALIDATION_MESSAGES.ER019_HALF_ALPHANUMERIC(p0);
    default:
      return VALIDATION_MESSAGES.ER015_SYSTEM_ERROR;
  }
}

