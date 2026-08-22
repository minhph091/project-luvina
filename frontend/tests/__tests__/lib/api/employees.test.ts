import { getEmployees } from '@/lib/api/employees';
import { apiClient } from '@/lib/api/client';

jest.mock('@/lib/api/client');
const mockedApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('Employees API Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('calls GET /employees with proper query parameters', async () => {
    const mockResponseData = {
      code: 200,
      totalRecords: 1,
      employees: [
        {
          employeeId: 1,
          employeeName: 'Nguyễn Văn A',
          employeeBirthDate: '1990-01-01',
          departmentName: 'DEV1',
          employeeEmail: 'a@luvina.net',
          employeeTelephone: '0123456789',
          certificationName: 'N1',
          endDate: '2025-12-31',
          score: 160,
        },
      ],
    };
    mockedApiClient.get.mockResolvedValue({ data: mockResponseData });

    const result = await getEmployees({
      employeeName: 'Nguyen',
      departmentId: 1,
      pageNo: 2,
      pageSize: 5,
      employeeNameOrder: 'DESC',
      certificationNameOrder: 'ASC',
      endDateOrder: 'ASC',
    });

    expect(mockedApiClient.get).toHaveBeenCalledWith('/employees', {
      params: {
        employeeName: 'Nguyen',
        departmentId: 1,
        pageNo: 2,
        pageSize: 5,
        employeeNameOrder: 'DESC',
        certificationNameOrder: 'ASC',
        endDateOrder: 'ASC',
      },
    });
    expect(result).toEqual(mockResponseData);
  });
});
