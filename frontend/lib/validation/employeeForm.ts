/**
 * Copyright(C) 2026 Luvina
 * employeeForm.ts - Zod Validation Schema & Field Validation Logic for ADM004
 * 04/09/2026 Pham Van Minh
 */

import { z } from 'zod';
import { FIELD_LABELS, VALIDATION_MESSAGES } from '@/constants';
import { EmployeeFormData, EmployeeFormErrors, EmployeeFormMode } from '@/types/employee';

/**
 * Regex kiểm tra ký tự Katakana nửa chữ (Halfsize Katakana: ｦ - ﾟ)
 */
export const HALFSIZE_KATAKANA_REGEX = /^[\uFF66-\uFF9F]+$/;

/**
 * Regex kiểm tra chuỗi nửa chữ số (Halfsize numeric: 0 - 9)
 */
export const HALFSIZE_NUMERIC_REGEX = /^[0-9]+$/;

/**
 * Regex kiểm tra chuỗi chữ và số nửa chữ (Halfsize alphanumeric: a-z, A-Z, 0-9)
 */
export const HALFSIZE_ALPHANUMERIC_REGEX = /^[a-zA-Z0-9_]+$/;

/**
 * Regex kiểm tra email chuẩn
 */
export const EMAIL_REGEX = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

/**
 * Regex kiểm tra định dạng ngày yyyy/MM/dd hoặc yyyy-MM-dd
 */
export const DATE_FORMAT_REGEX = /^\d{4}[/-]\d{1,2}[/-]\d{1,2}$/;

/**
 * Helper kiểm tra chuỗi ngày có phải ngày hợp lệ trên thực tế không (vd: 2026/02/29 hợp lệ không)
 */
export function isValidDateString(val: string): boolean {
  if (!val || !DATE_FORMAT_REGEX.test(val.trim())) return false;
  const parts = val.trim().split(/[/-]/).map(Number);
  if (parts.length !== 3) return false;
  const [year, month, day] = parts;
  if (month < 1 || month > 12 || day < 1 || day > 31) return false;
  const date = new Date(year, month - 1, day);
  return (
    date.getFullYear() === year &&
    date.getMonth() === month - 1 &&
    date.getDate() === day
  );
}

/**
 * Helper chuẩn hóa chuỗi ngày sang Date object để so sánh
 */
export function parseDateString(val: string | null | undefined): Date | null {
  if (!val || !isValidDateString(val)) return null;
  const parts = val.trim().split(/[/-]/).map(Number);
  return new Date(parts[0], parts[1] - 1, parts[2]);
}

/**
 * Tạo Zod Schema cho form Employee dựa theo Mode (ADD hoặc EDIT)
 */
export function createEmployeeFormSchema(mode: EmployeeFormMode = 'ADD') {
  return z
    .object({
      employeeId: z.number().optional(),

      // 1. employeeLoginId
      employeeLoginId: z
        .string()
        .trim()
        .min(1, VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.ACCOUNT_NAME))
        .max(50, VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.ACCOUNT_NAME, 50))
        .regex(
          HALFSIZE_ALPHANUMERIC_REGEX,
          VALIDATION_MESSAGES.ER019_HALF_ALPHANUMERIC(FIELD_LABELS.ACCOUNT_NAME)
        ),

      // 2. departmentId
      departmentId: z
        .union([z.number(), z.string()])
        .refine((val) => val !== '' && val !== undefined && val !== null && Number(val) > 0, {
          message: VALIDATION_MESSAGES.ER002_REQUIRED_SELECT(FIELD_LABELS.GROUP),
        }),

      // 3. employeeName
      employeeName: z
        .string()
        .trim()
        .min(1, VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.NAME))
        .max(100, VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.NAME, 100)),

      // 4. employeeNameKana
      employeeNameKana: z
        .string()
        .trim()
        .min(1, VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.KATAKANA_NAME))
        .max(100, VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.KATAKANA_NAME, 100))
        .regex(
          HALFSIZE_KATAKANA_REGEX,
          VALIDATION_MESSAGES.ER008_KATAKANA(FIELD_LABELS.KATAKANA_NAME)
        ),

      // 5. employeeBirthDate
      employeeBirthDate: z
        .string()
        .trim()
        .min(1, VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.BIRTHDAY))
        .refine((val) => isValidDateString(val), {
          message: VALIDATION_MESSAGES.ER005_INVALID_FORMAT(FIELD_LABELS.BIRTHDAY),
        }),

      // 6. employeeEmail
      employeeEmail: z
        .string()
        .trim()
        .min(1, VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.EMAIL))
        .max(100, VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.EMAIL, 100))
        .regex(EMAIL_REGEX, VALIDATION_MESSAGES.ER005_INVALID_FORMAT(FIELD_LABELS.EMAIL)),

      // 7. employeeTelephone
      employeeTelephone: z
        .string()
        .trim()
        .min(1, VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.TEL))
        .max(14, VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.TEL, 14))
        .regex(HALFSIZE_NUMERIC_REGEX, VALIDATION_MESSAGES.ER018_HALF_NUMBER(FIELD_LABELS.TEL)),

      // 8. employeeLoginPassword
      employeeLoginPassword: z.string().optional(),

      // 9. employeeLoginPasswordConfirm
      employeeLoginPasswordConfirm: z.string().optional(),

      // 10. certificationId
      certificationId: z.union([z.number(), z.string()]).optional().nullable(),

      // 11. certificationStartDate
      certificationStartDate: z.string().optional().nullable(),

      // 12. certificationEndDate
      certificationEndDate: z.string().optional().nullable(),

      // 13. score
      score: z.union([z.number(), z.string()]).optional().nullable(),
    })
    // Validate Password & Confirm Password
    .superRefine((data, ctx) => {
      const password = data.employeeLoginPassword?.trim() ?? '';
      const confirm = data.employeeLoginPasswordConfirm?.trim() ?? '';

      if (mode === 'ADD') {
        if (!password) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['employeeLoginPassword'],
            message: VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.PASSWORD),
          });
        } else if (password.length < 8 || password.length > 50) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['employeeLoginPassword'],
            message: VALIDATION_MESSAGES.ER007_LENGTH_RANGE(FIELD_LABELS.PASSWORD, 8, 50),
          });
        }

        if (password && !confirm) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['employeeLoginPasswordConfirm'],
            message: VALIDATION_MESSAGES.ER017_PASSWORD_MISMATCH,
          });
        } else if (password && confirm && password !== confirm) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['employeeLoginPasswordConfirm'],
            message: VALIDATION_MESSAGES.ER017_PASSWORD_MISMATCH,
          });
        }
      } else {
        // Mode EDIT: Password is optional
        if (password) {
          if (password.length < 8 || password.length > 50) {
            ctx.addIssue({
              code: z.ZodIssueCode.custom,
              path: ['employeeLoginPassword'],
              message: VALIDATION_MESSAGES.ER007_LENGTH_RANGE(FIELD_LABELS.PASSWORD, 8, 50),
            });
          }

          if (password !== confirm) {
            ctx.addIssue({
              code: z.ZodIssueCode.custom,
              path: ['employeeLoginPasswordConfirm'],
              message: VALIDATION_MESSAGES.ER017_PASSWORD_MISMATCH,
            });
          }
        } else if (confirm) {
          // Password rỗng nhưng confirm có nhập
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['employeeLoginPasswordConfirm'],
            message: VALIDATION_MESSAGES.ER017_PASSWORD_MISMATCH,
          });
        }
      }

      // Validate Japanese Certificate Fields if certificationId is selected
      const hasCert =
        data.certificationId !== undefined &&
        data.certificationId !== null &&
        data.certificationId !== '' &&
        Number(data.certificationId) > 0;

      if (hasCert) {
        // 11. certificationStartDate
        const startDateStr = data.certificationStartDate?.trim() ?? '';
        let isStartDateValid = false;
        if (!startDateStr) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['certificationStartDate'],
            message: VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.START_DATE),
          });
        } else if (!isValidDateString(startDateStr)) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['certificationStartDate'],
            message: VALIDATION_MESSAGES.ER005_INVALID_FORMAT(FIELD_LABELS.START_DATE),
          });
        } else {
          isStartDateValid = true;
        }

        // 12. certificationEndDate
        const endDateStr = data.certificationEndDate?.trim() ?? '';
        let isEndDateValid = false;
        if (!endDateStr) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['certificationEndDate'],
            message: VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.END_DATE),
          });
        } else if (!isValidDateString(endDateStr)) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['certificationEndDate'],
            message: VALIDATION_MESSAGES.ER005_INVALID_FORMAT(FIELD_LABELS.END_DATE),
          });
        } else {
          isEndDateValid = true;
        }

        // Compare endDate > startDate (ER012)
        if (isStartDateValid && isEndDateValid) {
          const startDate = parseDateString(startDateStr);
          const endDate = parseDateString(endDateStr);
          if (startDate && endDate && endDate.getTime() <= startDate.getTime()) {
            ctx.addIssue({
              code: z.ZodIssueCode.custom,
              path: ['certificationEndDate'],
              message: VALIDATION_MESSAGES.ER012_DATE_AFTER(
                FIELD_LABELS.END_DATE,
                FIELD_LABELS.START_DATE
              ),
            });
          }
        }

        // 13. score
        const scoreStr = String(data.score ?? '').trim();
        if (!scoreStr) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['score'],
            message: VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.SCORE),
          });
        } else if (!HALFSIZE_NUMERIC_REGEX.test(scoreStr)) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['score'],
            message: VALIDATION_MESSAGES.ER018_HALF_NUMBER(FIELD_LABELS.SCORE),
          });
        } else if (Number(scoreStr) < 0) {
          ctx.addIssue({
            code: z.ZodIssueCode.custom,
            path: ['score'],
            message: VALIDATION_MESSAGES.ER018_HALF_NUMBER(FIELD_LABELS.SCORE),
          });
        }
      }
    });
}

/**
 * Validate một trường duy nhất phục vụ sự kiện onBlur (Realtime Validation).
 *
 * @param fieldName Tên trường cần validate
 * @param formData Dữ liệu form hiện tại
 * @param mode Mode ADD hoặc EDIT
 * @returns Thông báo lỗi nếu có, hoặc null nếu hợp lệ
 */
export function validateField(
  fieldName: keyof EmployeeFormData,
  formData: EmployeeFormData,
  mode: EmployeeFormMode = 'ADD'
): string | null {
  const schema = createEmployeeFormSchema(mode);
  const result = schema.safeParse(formData);

  if (result.success) return null;

  // Lọc lấy lỗi đầu tiên tương ứng với fieldName
  const matchError = result.error.issues.find((issue) => issue.path[0] === fieldName);
  return matchError ? matchError.message : null;
}

/**
 * Validate toàn bộ form phục vụ sự kiện onSubmit (Xác nhận / 確認).
 *
 * @param formData Dữ liệu form hiện tại
 * @param mode Mode ADD hoặc EDIT
 * @returns Object chứa lỗi của tất cả các trường (EmployeeFormErrors)
 */
export function validateEmployeeForm(
  formData: EmployeeFormData,
  mode: EmployeeFormMode = 'ADD'
): { isValid: boolean; errors: EmployeeFormErrors } {
  const schema = createEmployeeFormSchema(mode);
  const result = schema.safeParse(formData);

  if (result.success) {
    return { isValid: true, errors: {} };
  }

  const errors: EmployeeFormErrors = {};
  for (const issue of result.error.issues) {
    const field = issue.path[0] as keyof EmployeeFormData;
    if (field && !errors[field]) {
      errors[field] = issue.message;
    }
  }

  return { isValid: false, errors };
}
