'use client';

/**
 * Copyright(C) 2026 Luvina
 * LoginForm.tsx - Component Form Đăng Nhập
 * 21/08/2026 Pham Van Minh
 */

import React from 'react';
import { useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { loginSchema, LoginFormValues } from '@/lib/validation/auth';
import { useLogin } from '@/hooks/useAuth';

export default function LoginForm() {
  const { handleLogin, loading, loginErrorMessage } = useLogin();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
  });

  /**
   * Xử lý submit form đăng nhập thông qua hook.
   */
  const handleFormSubmit = async (formData: LoginFormValues) => {
    await handleLogin(formData);
  };

  return (
    <form className="login100-form validate-form" onSubmit={handleSubmit(handleFormSubmit)}>
      {loginErrorMessage && (
        <span className="login100-form-title err">
          {loginErrorMessage}
        </span>
      )}
      {!loginErrorMessage && (
        <span className="login100-form-title">
          <br /><br />
        </span>
      )}

      <div className="wrap-input100 validate-input">
        <input
          className="input100"
          type="text"
          placeholder="アカウント名:"
          {...register('username')}
        />
        <span className="focus-input100"></span>
        <span className="symbol-input100">
          <i className="fa fa-envelope" aria-hidden="true"></i>
        </span>
      </div>
      {errors.username && (
        <span className="login100-form-title err" style={{ fontSize: '12px', paddingBottom: '10px' }}>
          {errors.username.message}
        </span>
      )}

      <div className="wrap-input100 validate-input">
        <input
          className="input100"
          type="password"
          placeholder="パスワード:"
          {...register('password')}
        />
        <span className="focus-input100"></span>
        <span className="symbol-input100">
          <i className="fa fa-lock" aria-hidden="true"></i>
        </span>
      </div>
      {errors.password && (
        <span className="login100-form-title err" style={{ fontSize: '12px', paddingBottom: '10px' }}>
          {errors.password.message}
        </span>
      )}

      <div className="container-login100-form-btn">
        <button type="submit" className="login100-form-btn" disabled={loading}>
          {loading ? 'ログイン中...' : 'ログイン'}
        </button>
      </div>
    </form>
  );
}
