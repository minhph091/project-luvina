/**
 * Copyright(C) 2026 Luvina
 * employeeForm.test.ts - Unit Tests for ADM004 Form Validation Logic
 * 04/09/2026 Pham Van Minh
 */

import {
  validateEmployeeForm,
  validateField,
  isValidDateString,
  HALFSIZE_KATAKANA_REGEX,
  HALFSIZE_NUMERIC_REGEX,
  HALFSIZE_ALPHANUMERIC_REGEX,
} from '@/lib/validation/employeeForm';
import { EmployeeFormData } from '@/types/employee';
import { FIELD_LABELS, VALIDATION_MESSAGES } from '@/constants';

describe('employeeForm validation regex & helpers', () => {
  test('HALFSIZE_KATAKANA_REGEX validates halfsize katakana characters correctly', () => {
    expect(HALFSIZE_KATAKANA_REGEX.test('ﾔﾏﾀﾞ')).toBe(true);
    expect(HALFSIZE_KATAKANA_REGEX.test('ﾀﾛｳ')).toBe(true);
    expect(HALFSIZE_KATAKANA_REGEX.test('山田')).toBe(false); // Kanji
    expect(HALFSIZE_KATAKANA_REGEX.test('やまだ')).toBe(false); // Hiragana
    expect(HALFSIZE_KATAKANA_REGEX.test('ヤマダ')).toBe(false); // Fullsize Katakana
    expect(HALFSIZE_KATAKANA_REGEX.test('Yamada')).toBe(false); // Latin
  });

  test('HALFSIZE_NUMERIC_REGEX validates halfsize numbers', () => {
    expect(HALFSIZE_NUMERIC_REGEX.test('0123456789')).toBe(true);
    expect(HALFSIZE_NUMERIC_REGEX.test('１２３')).toBe(false); // Fullsize digits
    expect(HALFSIZE_NUMERIC_REGEX.test('091-234')).toBe(false); // Hyphen
    expect(HALFSIZE_NUMERIC_REGEX.test('abc')).toBe(false);
  });

  test('HALFSIZE_ALPHANUMERIC_REGEX validates halfsize alphanumeric', () => {
    expect(HALFSIZE_ALPHANUMERIC_REGEX.test('user_123')).toBe(true);
    expect(HALFSIZE_ALPHANUMERIC_REGEX.test('Admin')).toBe(true);
    expect(HALFSIZE_ALPHANUMERIC_REGEX.test('ｔｅｓｔ')).toBe(false); // Fullsize
  });

  test('isValidDateString validates correct dates and rejects invalid dates', () => {
    expect(isValidDateString('2026/09/04')).toBe(true);
    expect(isValidDateString('2024-02-29')).toBe(true); // Leap year
    expect(isValidDateString('2023/02/29')).toBe(false); // Not a leap year
    expect(isValidDateString('2026/13/01')).toBe(false); // Invalid month
    expect(isValidDateString('2026/04/31')).toBe(false); // April has 30 days
    expect(isValidDateString('invalid')).toBe(false);
  });
});

describe('validateEmployeeForm - Mode ADD', () => {
  const validAddFormData: EmployeeFormData = {
    employeeLoginId: 'minhpv',
    departmentId: 1,
    employeeName: 'Phạm Văn Minh',
    employeeNameKana: 'ﾐﾝ',
    employeeBirthDate: '1995/05/10',
    employeeEmail: 'minhpv@luvina.net',
    employeeTelephone: '0987654321',
    employeeLoginPassword: 'Password123',
    employeeLoginPasswordConfirm: 'Password123',
    certificationId: '',
    certificationStartDate: '',
    certificationEndDate: '',
    score: '',
  };

  test('returns isValid: true for valid form data without certification in ADD mode', () => {
    const result = validateEmployeeForm(validAddFormData, 'ADD');
    expect(result.isValid).toBe(true);
    expect(result.errors).toEqual({});
  });

  test('validates required fields in ADD mode (ER001, ER002)', () => {
    const emptyForm: EmployeeFormData = {
      employeeLoginId: '',
      departmentId: '',
      employeeName: '',
      employeeNameKana: '',
      employeeBirthDate: '',
      employeeEmail: '',
      employeeTelephone: '',
      employeeLoginPassword: '',
      employeeLoginPasswordConfirm: '',
      certificationId: '',
      certificationStartDate: '',
      certificationEndDate: '',
      score: '',
    };

    const result = validateEmployeeForm(emptyForm, 'ADD');
    expect(result.isValid).toBe(false);
    expect(result.errors.employeeLoginId).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.ACCOUNT_NAME)
    );
    expect(result.errors.departmentId).toBe(
      VALIDATION_MESSAGES.ER002_REQUIRED_SELECT(FIELD_LABELS.GROUP)
    );
    expect(result.errors.employeeName).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.NAME)
    );
    expect(result.errors.employeeNameKana).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.KATAKANA_NAME)
    );
    expect(result.errors.employeeBirthDate).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.BIRTHDAY)
    );
    expect(result.errors.employeeEmail).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.EMAIL)
    );
    expect(result.errors.employeeTelephone).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.TEL)
    );
    expect(result.errors.employeeLoginPassword).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.PASSWORD)
    );
  });

  test('validates password length range 8-50 and mismatch (ER007, ER017)', () => {
    const shortPasswordForm: EmployeeFormData = {
      ...validAddFormData,
      employeeLoginPassword: '123',
      employeeLoginPasswordConfirm: '123',
    };
    const resultShort = validateEmployeeForm(shortPasswordForm, 'ADD');
    expect(resultShort.isValid).toBe(false);
    expect(resultShort.errors.employeeLoginPassword).toBe(
      VALIDATION_MESSAGES.ER007_LENGTH_RANGE(FIELD_LABELS.PASSWORD, 8, 50)
    );

    const mismatchForm: EmployeeFormData = {
      ...validAddFormData,
      employeeLoginPassword: 'Password123',
      employeeLoginPasswordConfirm: 'Password456',
    };
    const resultMismatch = validateEmployeeForm(mismatchForm, 'ADD');
    expect(resultMismatch.isValid).toBe(false);
    expect(resultMismatch.errors.employeeLoginPasswordConfirm).toBe(
      VALIDATION_MESSAGES.ER017_PASSWORD_MISMATCH
    );
  });

  test('validates email halfsize requirement and max length (ER008, ER006)', () => {
    const nonHalfsizeEmailForm: EmployeeFormData = {
      ...validAddFormData,
      employeeEmail: 'ｔｅｓｔ＠ｇｍａｉｌ．ｃｏｍ', // Fullwidth
    };
    const resultInvalid = validateEmployeeForm(nonHalfsizeEmailForm, 'ADD');
    expect(resultInvalid.isValid).toBe(false);
    expect(resultInvalid.errors.employeeEmail).toBe(
      VALIDATION_MESSAGES.ER008_BYTE_HALFSIZE(FIELD_LABELS.EMAIL)
    );

    const spaceEmailForm: EmployeeFormData = {
      ...validAddFormData,
      employeeEmail: 'test email@luvina.net', // Chứa khoảng trắng
    };
    const resultSpace = validateEmployeeForm(spaceEmailForm, 'ADD');
    expect(resultSpace.isValid).toBe(false);
    expect(resultSpace.errors.employeeEmail).toBe(
      VALIDATION_MESSAGES.ER008_BYTE_HALFSIZE(FIELD_LABELS.EMAIL)
    );

    const longEmailForm: EmployeeFormData = {
      ...validAddFormData,
      employeeEmail: `${'a'.repeat(120)}@test.com`, // 129 chars > 125
    };
    const resultLong = validateEmployeeForm(longEmailForm, 'ADD');
    expect(resultLong.isValid).toBe(false);
    expect(resultLong.errors.employeeEmail).toBe(
      VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.EMAIL, 125)
    );
  });

  test('validates telephone halfsize requirement (ER008)', () => {
    const fullwidthTelForm: EmployeeFormData = {
      ...validAddFormData,
      employeeTelephone: '０９８７６５４３２１', // Fullwidth
    };
    const result = validateEmployeeForm(fullwidthTelForm, 'ADD');
    expect(result.isValid).toBe(false);
    expect(result.errors.employeeTelephone).toBe(
      VALIDATION_MESSAGES.ER008_BYTE_HALFSIZE(FIELD_LABELS.TEL)
    );

    // Should accept hyphens and standard telephone numbers
    const hyphenTelForm: EmployeeFormData = {
      ...validAddFormData,
      employeeTelephone: '090-1234-5678',
    };
    const resultHyphen = validateEmployeeForm(hyphenTelForm, 'ADD');
    expect(resultHyphen.errors.employeeTelephone).toBeUndefined();
  });

  test('validates max length for all text fields (ER006)', () => {
    // 1. Account name > 50 chars
    const longAccountForm: EmployeeFormData = {
      ...validAddFormData,
      employeeLoginId: 'a'.repeat(51),
    };
    const resultAccount = validateEmployeeForm(longAccountForm, 'ADD');
    expect(resultAccount.isValid).toBe(false);
    expect(resultAccount.errors.employeeLoginId).toBe(
      VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.ACCOUNT_NAME, 50)
    );

    // 2. Name > 125 chars
    const longNameForm: EmployeeFormData = {
      ...validAddFormData,
      employeeName: 'a'.repeat(126),
    };
    const resultName = validateEmployeeForm(longNameForm, 'ADD');
    expect(resultName.isValid).toBe(false);
    expect(resultName.errors.employeeName).toBe(
      VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.NAME, 125)
    );

    // 3. Name kana > 125 chars
    const longKanaForm: EmployeeFormData = {
      ...validAddFormData,
      employeeNameKana: 'ｱ'.repeat(126),
    };
    const resultKana = validateEmployeeForm(longKanaForm, 'ADD');
    expect(resultKana.isValid).toBe(false);
    expect(resultKana.errors.employeeNameKana).toBe(
      VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.KATAKANA_NAME, 125)
    );

    // 4. Telephone > 50 chars
    const longTelForm: EmployeeFormData = {
      ...validAddFormData,
      employeeTelephone: '1'.repeat(51),
    };
    const resultTel = validateEmployeeForm(longTelForm, 'ADD');
    expect(resultTel.isValid).toBe(false);
    expect(resultTel.errors.employeeTelephone).toBe(
      VALIDATION_MESSAGES.ER006_MAX_LENGTH(FIELD_LABELS.TEL, 50)
    );

    // 5. Password > 50 chars (ER007)
    const longPasswordForm: EmployeeFormData = {
      ...validAddFormData,
      employeeLoginPassword: 'A1'.repeat(26), // 52 chars > 50
      employeeLoginPasswordConfirm: 'A1'.repeat(26),
    };
    const resultPassword = validateEmployeeForm(longPasswordForm, 'ADD');
    expect(resultPassword.isValid).toBe(false);
    expect(resultPassword.errors.employeeLoginPassword).toBe(
      VALIDATION_MESSAGES.ER007_LENGTH_RANGE(FIELD_LABELS.PASSWORD, 8, 50)
    );
  });

  test('validates katakana halfsize requirement (ER009)', () => {
    const fullsizeKanaForm: EmployeeFormData = {
      ...validAddFormData,
      employeeNameKana: 'ヤマダ', // Fullsize
    };
    const result = validateEmployeeForm(fullsizeKanaForm, 'ADD');
    expect(result.isValid).toBe(false);
    expect(result.errors.employeeNameKana).toBe(
      VALIDATION_MESSAGES.ER009_KATAKANA(FIELD_LABELS.KATAKANA_NAME)
    );
  });

  test('validates invalid date format for birthday (ER011)', () => {
    const invalidDateForm: EmployeeFormData = {
      ...validAddFormData,
      employeeBirthDate: '2026/02/30', // Invalid day
    };
    const result = validateEmployeeForm(invalidDateForm, 'ADD');
    expect(result.isValid).toBe(false);
    expect(result.errors.employeeBirthDate).toBe(
      VALIDATION_MESSAGES.ER011_INVALID_DATE(FIELD_LABELS.BIRTHDAY)
    );
  });

  test('validates future date for birthday (ER011)', () => {
    const futureDate = new Date();
    futureDate.setDate(futureDate.getDate() + 1);
    const yyyy = futureDate.getFullYear();
    const mm = String(futureDate.getMonth() + 1).padStart(2, '0');
    const dd = String(futureDate.getDate()).padStart(2, '0');
    const futureDateForm: EmployeeFormData = {
      ...validAddFormData,
      employeeBirthDate: `${yyyy}/${mm}/${dd}`,
    };
    const result = validateEmployeeForm(futureDateForm, 'ADD');
    expect(result.isValid).toBe(false);
    expect(result.errors.employeeBirthDate).toBe(
      VALIDATION_MESSAGES.ER011_INVALID_DATE(FIELD_LABELS.BIRTHDAY)
    );
  });

  test('validates certification fields when certificationId is selected', () => {
    const certFormMissingDetails: EmployeeFormData = {
      ...validAddFormData,
      certificationId: 1,
      certificationStartDate: '',
      certificationEndDate: '',
      score: '',
    };
    const resultMissing = validateEmployeeForm(certFormMissingDetails, 'ADD');
    expect(resultMissing.isValid).toBe(false);
    expect(resultMissing.errors.certificationStartDate).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.START_DATE)
    );
    expect(resultMissing.errors.certificationEndDate).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.END_DATE)
    );
    expect(resultMissing.errors.score).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.SCORE)
    );

    // End date must be after Start date (ER012)
    const certFormInvalidEndDate: EmployeeFormData = {
      ...validAddFormData,
      certificationId: 1,
      certificationStartDate: '2026/05/01',
      certificationEndDate: '2026/04/01',
      score: 180,
    };
    const resultEndDate = validateEmployeeForm(certFormInvalidEndDate, 'ADD');
    expect(resultEndDate.isValid).toBe(false);
    expect(resultEndDate.errors.certificationEndDate).toBe(
      VALIDATION_MESSAGES.ER012_DATE_AFTER(FIELD_LABELS.END_DATE, FIELD_LABELS.START_DATE)
    );

    // Valid cert form
    const validCertForm: EmployeeFormData = {
      ...validAddFormData,
      certificationId: 1,
      certificationStartDate: '2025/01/01',
      certificationEndDate: '2027/01/01',
      score: 180,
    };
    const resultValid = validateEmployeeForm(validCertForm, 'ADD');
    expect(resultValid.isValid).toBe(true);
    expect(resultValid.errors).toEqual({});
  });
});

describe('validateEmployeeForm - Mode EDIT', () => {
  const validEditFormData: EmployeeFormData = {
    employeeId: 1,
    employeeLoginId: 'minhpv',
    departmentId: 2,
    employeeName: 'Phạm Văn Minh',
    employeeNameKana: 'ﾐﾝ',
    employeeBirthDate: '1995/05/10',
    employeeEmail: 'minhpv@luvina.net',
    employeeTelephone: '0987654321',
    employeeLoginPassword: '',
    employeeLoginPasswordConfirm: '',
    certificationId: null,
    certificationStartDate: null,
    certificationEndDate: null,
    score: null,
  };

  test('allows empty password in EDIT mode', () => {
    const result = validateEmployeeForm(validEditFormData, 'EDIT');
    expect(result.isValid).toBe(true);
    expect(result.errors).toEqual({});
  });

  test('validates password length and match if password is entered in EDIT mode', () => {
    const editWithNewPassword: EmployeeFormData = {
      ...validEditFormData,
      employeeLoginPassword: 'NewPassword123',
      employeeLoginPasswordConfirm: 'NewPassword123',
    };
    const result = validateEmployeeForm(editWithNewPassword, 'EDIT');
    expect(result.isValid).toBe(true);

    const editMismatch: EmployeeFormData = {
      ...validEditFormData,
      employeeLoginPassword: 'NewPassword123',
      employeeLoginPasswordConfirm: 'WrongPassword',
    };
    const resultMismatch = validateEmployeeForm(editMismatch, 'EDIT');
    expect(resultMismatch.isValid).toBe(false);
    expect(resultMismatch.errors.employeeLoginPasswordConfirm).toBe(
      VALIDATION_MESSAGES.ER017_PASSWORD_MISMATCH
    );
  });
});

describe('validateField - Realtime field validation', () => {
  const sampleFormData: EmployeeFormData = {
    employeeLoginId: 'minhpv',
    departmentId: 1,
    employeeName: 'Phạm Văn Minh',
    employeeNameKana: 'ﾐﾝ',
    employeeBirthDate: '1995/05/10',
    employeeEmail: 'minhpv@luvina.net',
    employeeTelephone: '0987654321',
    employeeLoginPassword: 'Password123',
    employeeLoginPasswordConfirm: 'Password123',
    certificationId: '',
    certificationStartDate: '',
    certificationEndDate: '',
    score: '',
  };

  test('returns null for a valid field', () => {
    expect(validateField('employeeLoginId', sampleFormData, 'ADD')).toBeNull();
    expect(validateField('employeeNameKana', sampleFormData, 'ADD')).toBeNull();
  });

  test('returns error message for an invalid field', () => {
    const invalidData: EmployeeFormData = {
      ...sampleFormData,
      employeeLoginId: '',
    };
    expect(validateField('employeeLoginId', invalidData, 'ADD')).toBe(
      VALIDATION_MESSAGES.ER001_REQUIRED_INPUT(FIELD_LABELS.ACCOUNT_NAME)
    );
  });
});
