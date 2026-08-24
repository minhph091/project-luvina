import { getDepartments } from '@/lib/api/departments';
import { apiClient } from '@/lib/api/client';

jest.mock('@/lib/api/client');
const mockedApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('Departments API Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('calls GET /department and returns response data', async () => {
    const mockResponseData = {
      code: 200,
      departments: [
        { departmentId: 1, departmentName: 'Phòng Phát triển 1' },
      ],
    };
    mockedApiClient.get.mockResolvedValue({ data: mockResponseData });

    const result = await getDepartments();

    expect(mockedApiClient.get).toHaveBeenCalledWith('/department');
    expect(result).toEqual(mockResponseData);
  });
});
