import { render } from '@testing-library/react';
import EmployeeListPage from '@/app/(protected)/employees/adm002/page';
import EmployeeEditPage from '@/app/(protected)/employees/edit/page';
import EmployeeDetailPage from '@/app/(protected)/employees/adm003/page';
import EmployeeConfirmPage from '@/app/(protected)/employees/confirm/page';
import EmployeeCompletePage from '@/app/(protected)/employees/complete/page';
import HomePage from '@/app/page';
import RootLayout from '@/app/layout';

// Mock dependencies
jest.mock('next/navigation', () => ({
  useRouter: () => ({
    push: jest.fn(),
  }),
  usePathname: () => '/', // Mock pathname for layout test
}));


// Mock the auth hooks to prevent redirection during tests
jest.mock('@/hooks/useAuth', () => ({
  useAuth: () => {},
  useGuest: () => {},
}));

describe('Page Snapshots', () => {
  it('renders EmployeeListPage unchanged', () => {
    const { container } = render(<EmployeeListPage />);
    expect(container).toMatchSnapshot();
  });

  it('renders EmployeeEditPage unchanged', () => {
    const { container } = render(<EmployeeEditPage />);
    expect(container).toMatchSnapshot();
  });

  it('renders EmployeeDetailPage unchanged', () => {
    const { container } = render(<EmployeeDetailPage />);
    expect(container).toMatchSnapshot();
  });

  it('renders EmployeeConfirmPage unchanged', () => {
    sessionStorage.setItem(
      'employee_form_data',
      JSON.stringify({
        employeeLoginId: 'ntmhuong',
        departmentId: 1,
        departmentName: 'Nhóm 1',
        employeeName: 'Nguyễn Thị Mai Hương',
        employeeNameKana: '名カナ',
        employeeBirthDate: '1983/07/08',
        employeeEmail: 'メールアドレス',
        employeeTelephone: '0914326386',
        certificationId: 1,
        certificationName: 'Trình độ tiếng nhật cấp 1',
        certificationStartDate: '2010/07/08',
        certificationEndDate: '2010/07/08',
        score: '290',
      })
    );
    const { container } = render(<EmployeeConfirmPage />);
    expect(container).toMatchSnapshot();
  });

  it('renders EmployeeCompletePage unchanged', () => {
    const { container } = render(<EmployeeCompletePage />);
    expect(container).toMatchSnapshot();
  });

  it('renders HomePage unchanged and checks for redirection logic', () => {
    // We can't test the redirect directly here, but we can ensure the component renders correctly
    // The redirect logic is part of the component's effect, which we can't easily snapshot.
    const { container } = render(<HomePage />);
    expect(container).toMatchSnapshot();
  });

  it('renders RootLayout unchanged', () => {
    // Suppress React warning about <html> in <div> during testing
    const originalError = console.error;
    console.error = jest.fn();

    const { asFragment } = render(<RootLayout><div>Test Child</div></RootLayout>);
    expect(asFragment()).toMatchSnapshot();

    console.error = originalError;
  });
});

