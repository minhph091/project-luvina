import { renderHook, act } from '@testing-library/react';
import { useAuth, useGuest, useLogin } from '@/hooks/useAuth';
import { getToken, storeToken } from '@/lib/auth/token';
import { loginUser } from '@/lib/api/auth';
import { useRouter } from 'next/navigation';

jest.mock('@/lib/auth/token');
jest.mock('@/lib/api/auth');
jest.mock('next/navigation', () => ({
  useRouter: jest.fn(),
}));

const mockPush = jest.fn();
(useRouter as jest.Mock).mockReturnValue({
  push: mockPush,
});

const mockedGetToken = getToken as jest.Mock;
const mockedStoreToken = storeToken as jest.Mock;
const mockedLoginUser = loginUser as jest.Mock;

describe('Authentication Hooks', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('useAuth', () => {
    it('should redirect to /login if no token is found', () => {
      mockedGetToken.mockReturnValue(null);
      renderHook(() => useAuth());
      expect(mockPush).toHaveBeenCalledWith('/login');
    });

    it('should not redirect if a token is found', () => {
      mockedGetToken.mockReturnValue({ accessToken: 'fake-token', tokenType: 'Bearer' });
      renderHook(() => useAuth());
      expect(mockPush).not.toHaveBeenCalled();
    });
  });

  describe('useGuest', () => {
    it('should redirect to /employees/adm002 if a token is found', () => {
      mockedGetToken.mockReturnValue({ accessToken: 'fake-token', tokenType: 'Bearer' });
      renderHook(() => useGuest());
      expect(mockPush).toHaveBeenCalledWith('/employees/adm002');
    });

    it('should not redirect if no token is found', () => {
      mockedGetToken.mockReturnValue(null);
      renderHook(() => useGuest());
      expect(mockPush).not.toHaveBeenCalled();
    });
  });

  describe('useLogin', () => {
    it('handles successful login', async () => {
      mockedLoginUser.mockResolvedValue({
        accessToken: 'fake-token',
        tokenType: 'Bearer',
      });

      const { result } = renderHook(() => useLogin());

      let success = false;
      await act(async () => {
        success = await result.current.handleLogin({
          username: 'admin',
          password: 'password123',
        });
      });

      expect(success).toBe(true);
      expect(mockedLoginUser).toHaveBeenCalledWith({
        username: 'admin',
        password: 'password123',
      });
      expect(mockedStoreToken).toHaveBeenCalledWith('fake-token', 'Bearer');
      expect(mockPush).toHaveBeenCalledWith('/employees/adm002');
      expect(result.current.loginErrorMessage).toBeNull();
    });

    it('handles failed login', async () => {
      mockedLoginUser.mockRejectedValue(new Error('Unauthorized'));

      const { result } = renderHook(() => useLogin());

      let success = true;
      await act(async () => {
        success = await result.current.handleLogin({
          username: 'wrong',
          password: 'bad',
        });
      });

      expect(success).toBe(false);
      expect(mockPush).not.toHaveBeenCalled();
      expect(result.current.loginErrorMessage).toBe(
        'ログインに失敗しました。アカウント名またはパスワードを確認してください。'
      );
    });
  });
});
