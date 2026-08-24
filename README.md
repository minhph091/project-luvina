# Hệ Thống Quản Lý Nhân Viên (Employee Management System)

Dự án **Quản lý Nhân viên (User/Employee Management System)** là ứng dụng web toàn diện được xây dựng theo kiến trúc Client - Server tách biệt, phục vụ công tác quản lý thông tin nhân sự, phòng ban, và theo dõi trình độ chứng chỉ tiếng Nhật (JLPT) của nhân viên.

---

## 📑 Mục lục

1. [Tổng quan dự án](#-tổng-quan-dự-án)
2. [Kiến trúc & Công nghệ sử dụng](#-kiến-trúc--công-nghệ-sử-dụng)
3. [Cấu trúc thư mục dự án](#-cấu-trúc-thư-mục-dự-án)
4. [Các chức năng chính & Luồng màn hình](#-các-chức-năng-chính--luồng-màn-hình)
5. [Yêu cầu môi trường (Prerequisites)](#-yêu-cầu-môi-trường-prerequisites)
6. [Hướng dẫn cài đặt & Khởi chạy](#-hướng-dẫn-cài-đặt--khởi-chạy)
   - [Bước 1: Chuẩn bị Cơ sở dữ liệu](#bước-1-chuẩn-bị-cơ-sở-dữ-liệu-mysql)
   - [Bước 2: Cài đặt và chạy Back-end](#bước-2-cài-đặt-và-chạy-back-end-spring-boot)
   - [Bước 3: Cài đặt và chạy Front-end](#bước-3-cài-đặt-và-chạy-front-end-nextjs)
7. [Tài khoản đăng nhập mặc định](#-tài-khoản-đăng-nhập-mặc-định)
8. [Danh sách API chính](#-danh-sách-api-chính)
9. [Kiểm thử (Running Tests)](#-kiểm-thử-running-tests)
10. [Tài liệu tham khảo](#-tài-liệu-tham-khảo)

---

## 🌟 Tổng quan dự án

Hệ thống cung cấp giải pháp quản trị nhân sự nội bộ với các nghiệp vụ:
- Xác thực và phân quyền người dùng qua **JWT (JSON Web Token)**.
- Quản lý danh sách nhân viên: tìm kiếm đa điều kiện, phân trang, sắp xếp linh hoạt theo tên, chứng chỉ tiếng Nhật, ngày hết hạn chứng chỉ.
- Thêm mới, chỉnh sửa thông tin nhân sự kèm chứng chỉ tiếng Nhật (JLPT N1 ~ N5).
- Cơ chế xác nhận 2 bước (**Confirm Screen -> Complete Screen**) đảm bảo tính chính xác của dữ liệu.
- Xóa và bảo toàn toàn vẹn dữ liệu quan hệ phòng ban và chứng chỉ.

---

## 🛠 Kiến trúc & Công nghệ sử dụng

### 1. Back-end
- **Ngôn ngữ & Framework**: Java 17, Spring Boot 2.7.x
- **Bảo mật**: Spring Security, JWT (HS512), BCrypt Password Encoder
- **Database Access**: Spring Data JPA, Hibernate, HikariCP Connection Pool
- **Database Migration**: Flyway Migration (quản lý version CSDL tự động qua script SQL)
- **Mapping & Utilities**: MapStruct, Lombok
- **Quản lý build**: Maven Wrapper (`mvnw` / `mvnw.cmd`)

### 2. Front-end
- **Framework**: Next.js 16 (App Router), React 19
- **Ngôn ngữ**: TypeScript
- **HTTP Client**: Axios (kèm Interceptor tự động gắn Bearer Token và xử lý 401 Unauthorized)
- **Quản lý Form & Validation**: React Hook Form, Zod Resolver
- **UI Components & Datepicker**: Vanilla CSS Module, React Datepicker, Date-fns
- **Unit Test**: Jest, React Testing Library, ts-jest

### 3. Database
- **Hệ quản trị CSDL**: MySQL 8.0+ (hoặc MySQL 5.7)
- **Các bảng chính**:
  - `departments`: Thông tin các phòng ban.
  - `certifications`: Danh mục các cấp độ chứng chỉ tiếng Nhật (JLPT N1 - N5).
  - `employees`: Thông tin hồ sơ nhân viên và tài khoản.
  - `employees_certifications`: Quan hệ nhân viên - chứng chỉ tiếng Nhật (điểm số, ngày bắt đầu, ngày hết hạn).
  - `flyway_schema_history`: Lịch sử migration của Flyway.

---

## 📂 Cấu trúc thư mục dự án

```text
project/
├── api-design-doc.md        # Tài liệu thiết kế đặc tả API chi tiết
├── design-doc.md            # Tài liệu thiết kế màn hình và luồng nghiệp vụ
├── README.md                # Tài liệu hướng dẫn dự án tổng quan (file này)
│
├── back-end/                # Nguồn mã nguồn Back-end (Spring Boot)
│   ├── mvnw / mvnw.cmd      # Maven Wrapper script
│   ├── pom.xml              # Khai báo dependencies và plugin Maven
│   └── src/
│       ├── main/
│       │   ├── java/com/luvina/la/
│       │   │   ├── config/       # Cấu hình Security, JWT, WebMvc, Beans
│       │   │   ├── controller/   # REST Controllers (Auth, Employee, Department)
│       │   │   ├── dto/          # Data Transfer Objects
│       │   │   ├── entity/       # JPA Entities ánh xạ CSDL
│       │   │   ├── mapper/       # MapStruct mappers
│       │   │   ├── payload/      # Request & Response payload models
│       │   │   ├── repository/   # Spring Data JPA Repositories
│       │   │   ├── service/      # Business logic interfaces & implementations
│       │   │   └── validator/    # Custom validation logic
│       │   └── resources/
│       │       ├── config/       # application.yaml, application-dev.yaml, application-prod.yaml
│       │       ├── db/migration/ # SQL migration scripts (V1__init_schema.sql, ...)
│       │       └── messages.properties # Đa ngôn ngữ / thông điệp lỗi
│       └── test/                 # Unit tests & Integration tests cho Back-end
│
└── frontend/                # Nguồn mã nguồn Front-end (Next.js)
    ├── app/
    │   ├── (auth)/login/    # Màn hình đăng nhập ADM001
    │   └── (protected)/     # Các màn hình yêu cầu xác thực
    │       └── employees/
    │           ├── adm002/  # Danh sách nhân viên (Tìm kiếm, Sắp xếp, Phân trang)
    │           ├── adm003/  # Chi tiết nhân viên
    │           ├── edit/    # Thêm mới / Chỉnh sửa thông tin nhân viên (ADM004)
    │           ├── confirm/ # Màn hình xác nhận thông tin (ADM005)
    │           └── complete/# Màn hình thông báo hoàn tất (ADM006)
    ├── components/          # Các component tái sử dụng (Header, Footer, Pagination, Form controls)
    ├── constants/           # Hằng số, API Routes, Storage Keys, Message Keys
    ├── hooks/               # Custom React Hooks
    ├── lib/
    │   ├── api/             # Axios client, Auth API, Employee API, Department API
    │   ├── auth/            # Helpers quản lý Session/Token
    │   └── validation/      # Schema validation Zod
    ├── package.json         # Khai báo dependencies npm
    ├── tsconfig.json        # Cấu hình TypeScript
    └── tests/               # Unit tests cho Front-end (Jest)
```

---

## 🖥 Các chức năng chính & Luồng màn hình

```
[ ADM001: Đăng nhập ]
          │
          ▼
[ ADM002: Danh sách nhân viên ] ──(Click 'Thêm mới')──┐
          │                                           │
          ├─► [ ADM003: Chi tiết ] ──(Click 'Sửa')───┤
          │                                           ▼
          │                            [ ADM004: Thêm / Sửa nhân viên ]
          │                                           │
          │                                           ▼
          │                            [ ADM005: Xác nhận thông tin ]
          │                                           │
          │                                           ▼
          └─────────────────────────── [ ADM006: Hoàn tất thao tác ]
```

- **ADM001 - Đăng nhập (`/login`)**: Xác thực tài khoản với username/password, lưu JWT vào Session.
- **ADM002 - Danh sách nhân viên (`/employees/adm002`)**:
  - Tìm kiếm theo họ tên nhân viên và theo phòng ban.
  - Sắp xếp theo: Tên nhân viên (氏名), Trình độ tiếng Nhật (日本語能力), Ngày hết hạn chứng chỉ (失効日).
  - Phân trang (mặc định 20 bản ghi/trang).
  - Không hiển thị tài khoản có quyền `ADMIN` trên danh sách.
- **ADM003 - Chi tiết nhân viên (`/employees/adm003` hoặc `/employees/[id]`)**: Xem toàn bộ thông tin cá nhân, phòng ban và chứng chỉ tiếng Nhật. Hỗ trợ chuyển sang sửa hoặc xóa.
- **ADM004 - Thêm mới / Cập nhật nhân viên (`/employees/edit`)**: Nhập thông tin nhân viên kèm chọn trình độ tiếng Nhật, ngày cấp, hạn chứng chỉ, điểm số.
- **ADM005 - Xác nhận thông tin (`/employees/confirm`)**: Màn hình trung gian kiểm tra lại toàn bộ thông tin trước khi ghi nhận vào CSDL.
- **ADM006 - Hoàn tất (`/employees/complete`)**: Thông báo kết quả thực hiện thành công (Thêm / Sửa / Xóa) và điều hướng quay lại danh sách.

---

## 📋 Yêu cầu môi trường (Prerequisites)

Trước khi khởi chạy hệ thống, đảm bảo môi trường đã cài đặt:

1. **Java Development Kit (JDK)**: Phiên bản **17** trở lên.
   - Kiểm tra bằng lệnh: `java -version`
   - Đảm bảo biến môi trường `JAVA_HOME` đã được cấu hình trỏ đến thư mục cài JDK 17.
2. **Node.js**: Phiên bản **18.x** trở lên (khuyến nghị Node 20 LTS).
   - Kiểm tra bằng lệnh: `node -v` và `npm -v`
3. **MySQL Server**: Phiên bản **8.0+** (hoặc 5.7+).
   - Đang chạy tại cổng mặc định `3306`.
4. **Git**: Dùng để clone mã nguồn.

---

## 🚀 Hướng dẫn cài đặt & Khởi chạy

### Bước 1: Chuẩn bị Cơ sở dữ liệu (MySQL)

1. Mở MySQL Client / Workbench / DBeaver và tạo database:
   ```sql
   CREATE DATABASE `user-manage` CHARACTER SET utf8 COLLATE utf8_general_ci;
   ```
2. **Lưu ý**: Bạn không cần chạy script tạo bảng thủ công. Khi Back-end khởi động, **Flyway** sẽ tự động thực thi script `V1__init_schema.sql` để tạo cấu trúc bảng và nạp dữ liệu mẫu ban đầu.

---

### Bước 2: Cài đặt và chạy Back-end (Spring Boot)

1. Mở terminal, chuyển vào thư mục `back-end`:
   ```bash
   cd back-end
   ```

2. Kiểm tra cấu hình kết nối database trong file `src/main/resources/config/application-dev.yaml`:
   ```yaml
   spring:
     datasource:
       jdbcUrl: jdbc:mysql://localhost:3306/user-manage?createDatabaseIfNotExist=true
       username: root
       password: YOUR_MYSQL_PASSWORD  # <-- Đổi thành mật khẩu MySQL của bạn nếu khác
   ```

3. Khởi chạy ứng dụng Back-end:
   - **Trên Windows (cmd/PowerShell)**:
     ```bash
     .\mvnw.cmd spring-boot:run
     ```
   - **Trên Linux / macOS / Git Bash**:
     ```bash
     ./mvnw spring-boot:run
     ```

4. Sau khi khởi động thành công, Back-end sẽ lắng nghe tại:
   - **Base URL**: `http://localhost:8085`

---

### Bước 3: Cài đặt và chạy Front-end (Next.js)

1. Mở một cửa sổ terminal mới, chuyển vào thư mục `frontend`:
   ```bash
   cd frontend
   ```

2. Cài đặt các gói phụ thuộc (Dependencies):
   ```bash
   npm install
   ```

3. *(Tùy chọn)* Cấu hình biến môi trường:
   - Tạo file `.env.local` tại thư mục `frontend` (nếu cần thay đổi URL Back-end):
     ```env
     NEXT_PUBLIC_API_URL=http://localhost:8085
     ```
   - Nếu không tạo, hệ thống sẽ tự động dùng mặc định `http://localhost:8085`.

4. Khởi chạy server phát triển Front-end:
   ```bash
   npm run dev
   ```

5. Mở trình duyệt web và truy cập:
   - **URL Ứng dụng**: [http://localhost:3000](http://localhost:3000)
   - Hệ thống sẽ tự động điều hướng đến trang Đăng nhập [http://localhost:3000/login](http://localhost:3000/login).

---

## 🔑 Tài khoản đăng nhập mặc định

Hệ thống được khởi tạo sẵn tài khoản Quản trị viên (Admin) trong database:

| Tên đăng nhập (Username) | Mật khẩu (Password) | Vai trò (Role) | Mô tả |
|--------------------------|---------------------|----------------|-------|
| `admin`                  | `admin`             | `ADMIN`        | Quản trị viên hệ thống |

> **Ghi chú về mật khẩu**: Mật khẩu trong cơ sở dữ liệu được mã hóa bằng thuật toán `BCrypt`:
> `$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy`

---

## 📡 Danh sách API chính

Các API được bảo vệ yêu cầu Header: `Authorization: Bearer <access_token>`

| STT | Phương thức | Endpoint | Mô tả chức năng | Yêu cầu Auth |
|:---:|:-----------:|:---------|:----------------|:------------:|
| 1 | `POST` | `/login` | Đăng nhập hệ thống, trả về JWT Token | ❌ Không |
| 2 | `POST` | `/test-auth` | Kiểm tra tính hợp lệ của Token | ⭕ Có |
| 3 | `GET` | `/departments` | Lấy danh sách tất cả phòng ban | ⭕ Có |
| 4 | `GET` | `/employees` | Lấy danh sách nhân viên (hỗ trợ tìm kiếm, sắp xếp, phân trang) | ⭕ Có |
| 5 | `GET` | `/employees/{id}` | Lấy chi tiết thông tin một nhân viên theo ID | ⭕ Có |
| 6 | `POST` | `/employees` | Thêm mới một nhân viên | ⭕ Có |
| 7 | `PUT` | `/employees/{id}` | Cập nhật thông tin nhân viên theo ID | ⭕ Có |
| 8 | `DELETE` | `/employees/{id}` | Xóa nhân viên theo ID | ⭕ Có |

---

## 🧪 Kiểm thử (Running Tests)

### Kiểm thử Back-end (JUnit / Mockito)
Di chuyển vào thư mục `back-end` và thực thi:
```bash
cd back-end
.\mvnw.cmd test    # Windows
./mvnw test        # Linux / macOS
```

### Kiểm thử Front-end (Jest)
Di chuyển vào thư mục `frontend` và thực thi:
```bash
cd frontend
npm run test
```

---

## 📚 Tài liệu tham khảo

- [Tài liệu thiết kế màn hình (design-doc.md)](file:///c:/Users/LA/Music/project/design-doc.md): Chi tiết bố cục UI, quy tắc hiển thị, validation từng trường dữ liệu.
- [Tài liệu thiết kế API (api-design-doc.md)](file:///c:/Users/LA/Music/project/api-design-doc.md): Đặc tả chi tiết Request payload, Response code, Error responses.
