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

  it('calls PUT /employee with proper body including certifications', async () => {
    const mockResponseData = { code: 200, employeeId: 5, message: { code: 'MSG002', params: [] } };
    mockedApiClient.put.mockResolvedValue({ data: mockResponseData });

    const formData = {
      employeeId: 5,
      employeeLoginId: 'testuser',
      employeeLoginPassword: 'newpassword',
      employeeName: 'Nguyễn Văn B',
      employeeNameKana: 'ｱｲｳｴｵ',
      employeeBirthDate: '1995/05/10',
      employeeEmail: 'b@luvina.net',
      employeeTelephone: '0987654321',
      departmentId: 2,
      certificationId: 3,
      certificationStartDate: '2023/01/01',
      certificationEndDate: '2024/01/01',
      score: '800',
    };

    const result = await (await import('@/lib/api/employees')).updateEmployee(formData);

    expect(mockedApiClient.put).toHaveBeenCalledWith('/employee', {
      employeeId: 5,
      employeeLoginId: 'testuser',
      employeeLoginPassword: 'newpassword',
      employeeName: 'Nguyễn Văn B',
      employeeNameKana: 'ｱｲｳｴｵ',
      employeeBirthDate: '1995/05/10',
      employeeEmail: 'b@luvina.net',
      employeeTelephone: '0987654321',
      departmentId: '2',
      certifications: {
        certificationId: '3',
        startDate: '2023/01/01',
        endDate: '2024/01/01',
        score: '800',
      },
    });
    expect(result).toEqual(mockResponseData);
  });

  it('calls PUT /employee without password if empty, and without certifications if none', async () => {
    const mockResponseData = { code: 200, employeeId: 5, message: { code: 'MSG002', params: [] } };
    mockedApiClient.put.mockResolvedValue({ data: mockResponseData });

    const formData: import('@/types/employee').EmployeeFormData = {
      employeeId: 5,
      employeeLoginId: 'testuser',
      employeeLoginPassword: '',
      employeeName: 'Nguyễn Văn B',
      employeeNameKana: 'ｱｲｳｴｵ',
      employeeBirthDate: '1995/05/10',
      employeeEmail: 'b@luvina.net',
      employeeTelephone: '0987654321',
      departmentId: 2,
      certificationId: '',
    };

    await (await import('@/lib/api/employees')).updateEmployee(formData);

    expect(mockedApiClient.put).toHaveBeenCalledWith('/employee', {
      employeeId: 5,
      employeeLoginId: 'testuser',
      employeeName: 'Nguyễn Văn B',
      employeeNameKana: 'ｱｲｳｴｵ',
      employeeBirthDate: '1995/05/10',
      employeeEmail: 'b@luvina.net',
      employeeTelephone: '0987654321',
      departmentId: '2',
    });
  });
});

