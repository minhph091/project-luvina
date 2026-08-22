import { loginUser } from '@/lib/api/auth';
import { apiClient } from '@/lib/api/client';

jest.mock('@/lib/api/client');
const mockedApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('Auth API Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('calls POST /login and returns response data', async () => {
    const mockResponseData = {
      accessToken: 'sample-jwt-token',
      tokenType: 'Bearer',
    };
    mockedApiClient.post.mockResolvedValue({ data: mockResponseData });

    const result = await loginUser({ username: 'admin', password: '123' });

    expect(mockedApiClient.post).toHaveBeenCalledWith('/login', {
      username: 'admin',
      password: '123',
    });
    expect(result).toEqual(mockResponseData);
  });
});
