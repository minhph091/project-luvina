import { getEmployees } from '@/lib/api/employees';
import { apiClient } from '@/lib/api/client';

jest.mock('@/lib/api/client');
const mockedApiClient = apiClient as jest.Mocked<typeof apiClient>;

describe('Employees API Service', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  it('calls GET /employee with proper query parameters', async () => {
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

    expect(mockedApiClient.get).toHaveBeenCalledWith('/employee', {
      params: {
        employee_name: 'Nguyen',
        department_id: '1',
        ord_employee_name: 'DESC',
        ord_certification_name: 'ASC',
        ord_end_date: 'ASC',
        offset: 5,
        limit: 5,
      },
    });
    expect(result).toEqual(mockResponseData);
  });

  it('calls GET /employee with all 3 sort params prioritizing ord_certification_name when sortBy is certificationNameOrder', async () => {
    const mockResponseData = { code: 200, totalRecords: 0, employees: [] };
    mockedApiClient.get.mockResolvedValue({ data: mockResponseData });

    await getEmployees({
      certificationNameOrder: 'DESC',
      sortBy: 'certificationNameOrder',
    });

    expect(mockedApiClient.get).toHaveBeenCalledWith('/employee', {
      params: {
        ord_certification_name: 'DESC',
        ord_employee_name: 'ASC',
        ord_end_date: 'ASC',
      },
    });
  });

  it('calls GET /employee with all 3 sort params prioritizing ord_end_date when sortBy is endDateOrder', async () => {
    const mockResponseData = { code: 200, totalRecords: 0, employees: [] };
    mockedApiClient.get.mockResolvedValue({ data: mockResponseData });

    await getEmployees({
      endDateOrder: 'DESC',
      sortBy: 'endDateOrder',
    });

    expect(mockedApiClient.get).toHaveBeenCalledWith('/employee', {
      params: {
        ord_end_date: 'DESC',
        ord_employee_name: 'ASC',
        ord_certification_name: 'ASC',
      },
    });
  });
});
