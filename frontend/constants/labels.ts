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
