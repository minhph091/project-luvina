# Tài liệu thiết kế API - List employees

| Tên system     | Loại system       | TKCB | Người tạo | Ngày tạo   | Người update | Ngày update | Version |
|----------------|-------------------|------|-----------|------------|--------------|-------------|---------|
| Manager User   | 13_Thiết kế API   |      | ThanhPD   | 2023-01-04 | ThanhPD      | 2023-01-04  | 0.1     |

**Category chức năng:** List employees

---

## 1. Bìa

| Mục              | Nội dung              |
|------------------|-----------------------|
| Tên system       | Manager User          |
| Loại system      | 13_Thiết kế API       |
| Hạng mục         | List employees        |
| Người tạo        | ThanhPD               |
| Ngày tạo         | 2023-01-04            |
| Người update     | ThanhPD               |
| Ngày update      | 2023-01-04            |
| Version          | 0.1                   |

**Đường dẫn:** 13_Thiết kế API//List employees

| Ngày phê chuẩn | Người phê chuẩn | Version |
|----------------|-----------------|---------|
|                |                 |         |

---

## 2. Lịch sử thay đổi

| Date       | Người update | Version | Nội dung thay đổi   | Ngày phê chuẩn | Người phê chuẩn |
|------------|--------------|---------|---------------------|----------------|-----------------|
| 2023-01-04 | ThanhPD      | 0.1     | Tạo mới tài liệu    |                |                 |

---

## 3. Khái quát

### 3.1. Khái quát

Lấy thông tin nhân viên (ngoại trừ tài khoản có role ADMIN) và phòng ban cùng thông tin chứng chỉ tiếng Nhật (nếu có) của nhân viên theo điều kiện tìm kiếm để hiển thị màn hình list.

### 3.2. Request

#### Request URL

| No. | Service  | API name       | Method HTTP | Note |
|-----|----------|----------------|-------------|------|
| 1   | employee | List employees | GET         |      |

#### Request Parameter

| No. | Parameter              | Bắt buộc | Kiểu   | Giá trị default | Tên hạng mục                          | Note                                                                 |
|-----|------------------------|----------|--------|-----------------|---------------------------------------|----------------------------------------------------------------------|
| 1   | employee_name          | -        | string | ""              | Tên nhân viên                         | Tìm kiếm theo tên nhân viên                                          |
| 2   | department_id          | -        | string | ""              | ID phòng ban                          | Tìm kiếm theo tên phòng ban                                          |
| 3   | ord_employee_name      | -        | string | ""              | Tên hạng mục                          | = "ASC" hoặc "DESC"                                                  |
| 4   | ord_certification_name | -        | string | ""              | Tên chứng chỉ                         | = "ASC" hoặc "DESC"                                                  |
| 5   | ord_end_date           | -        | string | ""              | Ngày kết thúc hiệu lực chứng chỉ      | = "ASC" hoặc "DESC"                                                  |
| 6   | offset                 | -        | string | ""              | Vị trí bắt đầu get records            | Không truyền vào sẽ lấy từ bản ghi đầu tiên (tức là offset = 0)     |
| 7   | limit                  | -        | string | ""              | Giới hạn số records get               | Không truyền vào sẽ lấy 5                                            |

#### ※Sample

```
/employee?employee_name=A&department_id=1&ord_employee_name=ASC&ord_certification_name=ASC&ord_end_date=DESC&offset=2&limit=30
```

### 3.3. Response

#### Trường hợp API trả về response bình thường

| No. | json key name       | Kiểu     | Tên hạng mục                  | Note                          |
|-----|---------------------|----------|-------------------------------|-------------------------------|
| 1   | code                | number   |                               |                               |
| 2   | totalRecords        | number   |                               | Tổng số nhân viên             |
| 3   | employees           | array    |                               | Mảng chứa thông tin nhân viên |
| 4   | └ employeeId        | number   |                               |                               |
| 5   | └ employeeName      | string   |                               |                               |
| 6   | └ employeeBirthDate | date     |                               |                               |
| 7   | └ departmentName    | string   |                               |                               |
| 8   | └ employeeEmail     | string   |                               |                               |
| 9   | └ employeeTelephone | string   |                               |                               |
| 10  | └ certificationName | string   |                               |                               |
| 11  | └ endDate           | date     |                               |                               |
| 12  | └ score             | decimal  |                               |                               |

#### ※Sample

```json
{
  "code": "200",
  "totalRecords": 2,
  "employees": [
    {
      "employeeId": "1",
      "employeeName": "Nguyễn Văn A",
      "employeeBirthDate": "1983/01/01",
      "departmentName": "Phòng DevN",
      "employeeEmail": "nguyenvana@luvina.net",
      "employeeTelephone": "01234567",
      "certificationName": "Trình độ tiếng Nhật cấp 1",
      "endDate": "9999/12/31",
      "score": "999"
    },
    {
      "employeeId": "2",
      "employeeName": "Nguyễn Văn B",
      "employeeBirthDate": "1983/01/02",
      "departmentName": "Phòng DevN",
      "employeeEmail": "nguyenvanb@luvina.net",
      "employeeTelephone": "01234568",
      "certificationName": "Trình độ tiếng Nhật cấp 2",
      "endDate": "9999/12/31",
      "score": "999"
    }
  ]
}
```

#### Trường hợp API trả về lỗi

| No. | json key name | Kiểu   | Tên hạng mục | Note        |
|-----|---------------|--------|--------------|-------------|
| 1   | code          | number |              |             |
| 2   | message       | object |              | Nội dung lỗi|

#### ※Sample

```json
{
  "code": "500",
  "message": {
    "code": "ER015",
    "params": []
  }
}
```

---

## 4. Flow xử lý

*(Nội dung flow xử lý được mô tả chi tiết tại phần Chi tiết xử lý)*

---

## 5. Chi tiết xử lý

### Xử lý common

`<Không có>`

### Xử lý chi tiết

#### 1. Validate parameter

##### 1.1. Validate parameter `[ord_employee_name]`, `[ord_certification_name]`, `[ord_end_date]`

- Nếu giá trị order không phải là `"ASC"` hoặc `"DESC"` thì trả về lỗi có mã code **ER021**.

##### 1.2. Validate parameter `[offset]`

- Nếu giá trị parameter này không phải là số nguyên dương thì trả về lỗi có mã code **ER018**, tham số `"オフセット"`.

##### 1.3. Validate parameter `[limit]`

- Nếu giá trị parameter này không phải là số nguyên dương thì trả về lỗi có mã code **ER018**, tham số `"リミット"`.

> Nếu có lỗi thì chuyển sang bước **[3. Tạo dữ liệu response cho API]**.

#### 2. Get danh sách nhân viên

##### 2.1. Thực hiện lấy tổng số nhân viên từ database

**Danh sách bảng sử dụng**

| No. | Tên bảng logic       | ID bảng vật lý | Create | Refer | Update | Xóa |
|-----|----------------------|----------------|--------|-------|--------|-----|
| 1   | Thông tin nhân viên  | employees      |        | 〇    |        |     |
| 2   | Thông tin bộ phận    | departments    |        | 〇    |        |     |

**Table access**

**① Hạng mục lấy**

- Trường hợp get data hiển thị màn hình:

| No. | Tên bảng  | Alias | Tên trường          |
|-----|-----------|-------|---------------------|
| 1   | employees | -     | COUNT(employee_id)  |

**② Điều kiện kết hợp**

- Lấy thông tin phòng ban:

| No. | Tên bảng  | Tên trường     | Tên trường     | Tên bảng / Alias | Điều kiện liên kết |
|-----|-----------|----------------|----------------|------------------|--------------------|
| 1   | employees | department_id  | department_id  | departments      | INNER JOIN         |

**③ Điều kiện lấy**

- Điều kiện mặc định: Loại trừ người dùng có role admin:

| No. | Tên bảng  | Tên trường     | Điều kiện (giá trị, tên hạng mục màn hình) |
|-----|-----------|----------------|--------------------------------------------|
| 1   | employees | employee_role  | != 'ADMIN'                                 |

- Nếu tồn tại parameter `[department_id]` và không rỗng → Thêm điều kiện search theo phòng ban:

| No. | Tên bảng  | Tên trường     | Điều kiện (giá trị, tên hạng mục màn hình) |
|-----|-----------|----------------|--------------------------------------------|
| 1   | employees | department_id  | = parameter `[department_id]`              |

- Nếu tồn tại parameter `[employee_name]` và không rỗng → Thêm điều kiện search theo tên nhân viên:

| No. | Tên bảng  | Tên trường     | Điều kiện (giá trị, tên hạng mục màn hình)          |
|-----|-----------|----------------|-----------------------------------------------------|
| 1   | employees | employee_name  | LIKE '%' + parameter `[employee_name]` + '%'        |

> Nếu tổng số bản ghi là 0 thì chuyển đến **[3. Tạo dữ liệu response cho API]**.

##### 2.2. Thực hiện get danh sách nhân viên từ database

**Danh sách bảng sử dụng**

| No. | Tên bảng logic                          | ID bảng vật lý           | Create | Refer | Update | Xóa |
|-----|-----------------------------------------|--------------------------|--------|-------|--------|-----|
| 1   | Thông tin nhân viên                     | employees                |        | 〇    |        |     |
| 2   | Thông tin bộ phận                       | departments              |        | 〇    |        |     |
| 3   | Thông tin chứng chỉ tiếng Nhật          | certifications           |        | 〇    |        |     |
| 4   | Thông tin nhân viên chứng chỉ tiếng Nhật| employees_certifications |        | 〇    |        |     |

**Table access**

**① Hạng mục lấy**

- Trường hợp get data hiển thị màn hình:

| No. | Tên bảng                 | Alias | Tên trường           |
|-----|--------------------------|-------|----------------------|
| 1   | employees                | -     | employee_id          |
| 2   | employees                | -     | employee_name        |
| 3   | employees                | -     | employee_birthdate   |
| 4   | employees                | -     | employee_email       |
| 5   | employees                | -     | employee_telephone   |
| 7   | departments              | -     | department_name      |
| 8   | certifications           | -     | certification_name   |
| 9   | employees_certifications | -     | end_date             |
| 10  | employees_certifications | -     | score                |

**② Điều kiện kết hợp**

- Lấy thông tin phòng ban:

| No. | Tên bảng  | Tên trường     | Tên trường     | Tên bảng / Alias | Điều kiện liên kết |
|-----|-----------|----------------|----------------|------------------|--------------------|
| 1   | employees | department_id  | department_id  | departments      | INNER JOIN         |

- Lấy thông tin chứng chỉ tiếng Nhật:

| No. | Tên bảng                 | Tên trường        | Tên trường        | Tên bảng / Alias         | Điều kiện liên kết |
|-----|--------------------------|-------------------|-------------------|--------------------------|--------------------|
| 2   | employees                | employee_id       | employee_id       | employees_certifications | LEFT JOIN          |
| 3   | employees_certifications | certification_id  | certification_id  | certifications           | LEFT JOIN          |

**③ Điều kiện lấy**

- Điều kiện mặc định: Loại trừ người dùng có role admin:

| No. | Tên bảng  | Tên trường     | Điều kiện (giá trị, tên hạng mục màn hình) |
|-----|-----------|----------------|--------------------------------------------|
| 1   | employees | employee_role  | != 'ADMIN'                                 |

- Nếu tồn tại parameter `[department_id]` và không rỗng → Thêm điều kiện search theo phòng ban:

| No. | Tên bảng  | Tên trường     | Điều kiện (giá trị, tên hạng mục màn hình) |
|-----|-----------|----------------|--------------------------------------------|
| 1   | employees | department_id  | = parameter `[department_id]`              |

- Nếu tồn tại parameter `[employee_name]` và không rỗng → Thêm điều kiện search theo tên nhân viên:

| No. | Tên bảng  | Tên trường     | Điều kiện (giá trị, tên hạng mục màn hình)          |
|-----|-----------|----------------|-----------------------------------------------------|
| 1   | employees | employee_name  | LIKE '%' + parameter `[employee_name]` + '%'        |

**④ Sort**

- Nếu tồn tại `[ord_employee_name]` và không rỗng → Thêm sort theo hạng mục **[氏名]**:

| No. | Tên bảng  | Tên hạng mục    | Sort order                                      |
|-----|-----------|-----------------|-------------------------------------------------|
| 1   | employees | employee_name   | parameter `[ord_employee_name]` (ASC hoặc DESC) |

- Nếu tồn tại `[ord_certification_name]` và không rỗng → Thêm sort theo hạng mục **[日本語能力]**:

| No. | Tên bảng       | Tên hạng mục         | Sort order                                           |
|-----|----------------|----------------------|------------------------------------------------------|
| 1   | certifications | certification_name   | parameter `[ord_certification_name]` (ASC hoặc DESC) |

- Nếu tồn tại `[ord_end_date]` và không rỗng → Thêm sort theo hạng mục **[失効日]**:

| No. | Tên bảng                 | Tên hạng mục | Sort order                                   |
|-----|--------------------------|--------------|----------------------------------------------|
| 1   | employees_certifications | end_date     | parameter `[ord_end_date]` (ASC hoặc DESC)   |

> ※ Nếu không chỉ định các tham số orderBy thì sort mặc định theo `employee_id` tăng dần.

**⑤ Phân trang**

| No. | Tên bảng  | Tên hạng mục | Giá trị                                                              |
|-----|-----------|--------------|----------------------------------------------------------------------|
| 1   | employees | limit        | = parameter limit (nếu không có giá trị thì để mặc định là 5)        |
| 2   | employees | offset       | = parameter offset (nếu không có giá trị thì để mặc định là 0)       |

#### 3. Tạo dữ liệu response cho API

**Trường hợp không có lỗi xảy ra:**

| No. | Key                 | Giá trị                  | Note |
|-----|---------------------|--------------------------|------|
| 1   | code                | 200                      |      |
| 2   | totalRecords        | Lấy giá trị từ No 2.1    |      |
| 3   | employees           | Lấy giá trị từ No 2.2    |      |
| 4   | └ employeeId        |                          |      |
| 5   | └ employeeName      |                          |      |
| 6   | └ employeeBirthDate |                          |      |
| 7   | └ departmentName    |                          |      |
| 8   | └ employeeEmail     |                          |      |
| 9   | └ employeeTelephone |                          |      |
| 10  | └ certificationName |                          |      |
| 11  | └ endDate           |                          |      |
| 12  | └ score             |                          |      |

**Trường hợp có lỗi xảy ra:**

| No. | Key     | Giá trị                                      | Note |
|-----|---------|----------------------------------------------|------|
| 1   | code    | 500                                          |      |
| 2   | message | Lấy giá trị từ No 1. Format `{code: "", params: []}` |      |

> Kết thúc xử lý.

---

## 6. Tham chiếu

### Danh sách tài liệu tham chiếu

| No. | Mã tham chiếu | Tài liệu tham chiếu |
|-----|---------------|---------------------|
|     |               |                     |


# Thiết kế API: Get Employee

| Mục | Giá trị |
|-----|---------|
| **Tên system** | Manager User |
| **Loại system** | Thiết kế API |
| **Hạng mục** | Get employee |
| **Người tạo** | ThanhPD |
| **Ngày tạo** | 2023-01-04 |
| **Người update** | ThanhPD |
| **Ngày update** | 2023-01-04 |
| **Version** | 0.1 |

---

## 1. Lịch sử thay đổi

| Date | Người update | Version | Nội dung thay đổi | Ngày phê chuẩn | Người phê chuẩn |
|------|--------------|---------|-------------------|----------------|-----------------|
| 2023-01-04 | ThanhPD | 0.1 | Tạo mới tài liệu | | |

---

## 2. Khái quát

### 2.1. Mô tả
Lấy thông tin chi tiết nhân viên theo `employeeId`.

### 2.2. Request

#### Request URL

| No. | Service  | API name     | Method HTTP | Note |
|-----|----------|--------------|-------------|------|
| 1   | employee | Get employee | GET         |      |

**Sample**
```
GET /employee/1
```

#### Request Parameter

| No. | Parameter  | Bắt buộc | Kiểu   | Default | Mô tả                              |
|-----|------------|----------|--------|---------|------------------------------------|
| 1   | employeeId | ○        | number |         | ID của employee cần lấy thông tin |

### 2.3. Response

#### Response thành công

| No. | Key                  | Kiểu    | Mô tả                                      |
|-----|----------------------|---------|--------------------------------------------|
| 1   | code                 | number  | Mã kết quả (200)                           |
| 2   | employeeId           | number  | ID nhân viên                               |
| 3   | employeeName         | string  | Tên nhân viên                              |
| 4   | employeeBirthDate    | date    | Ngày sinh                                  |
| 5   | departmentId         | string  | ID phòng ban                               |
| 6   | departmentName       | string  | Tên phòng ban                              |
| 7   | employeeEmail        | string  | Email                                      |
| 8   | employeeTelephone    | string  | Số điện thoại                              |
| 9   | employeeNameKana     | string  | Tên kana                                   |
| 10  | employeeLoginId      | string  | Login ID                                   |
| 11  | certifications       | array   | Mảng chứng chỉ tiếng Nhật                  |
|     | └ certificationId    | number  | ID chứng chỉ                               |
|     | └ certificationName  | string  | Tên chứng chỉ                              |
|     | └ startDate          | date    | Ngày bắt đầu hiệu lực                      |
|     | └ endDate            | date    | Ngày kết thúc hiệu lực                     |
|     | └ score              | decimal | Điểm số                                    |

**Sample**
```json
{
  "code": "200",
  "employeeId": "1",
  "employeeName": "Nguyễn Văn A",
  "employeeBirthDate": "1983/01/02",
  "departmentId": "1",
  "departmentName": "Phòng DEVN",
  "employeeEmail": "nguyenvana@luvina.net",
  "employeeTelephone": "01234567",
  "employeeNameKana": "名カナ",
  "employeeLoginId": "nguyenvana",
  "certifications": [
    {
      "certificationId": "1",
      "certificationName": "chứng chỉ tiếng Nhật cấp 1",
      "startDate": "2023/01/01",
      "endDate": "2024/01/01",
      "score": "180"
    },
    {
      "certificationId": "2",
      "certificationName": "chứng chỉ tiếng Nhật cấp 2",
      "startDate": "2023/02/01",
      "endDate": "2024/02/01",
      "score": "90"
    }
  ]
}
```

#### Response lỗi

| No. | Key     | Kiểu   | Mô tả        |
|-----|---------|--------|--------------|
| 1   | code    | number | Mã kết quả (500) |
| 2   | message | object | Nội dung lỗi |

**Sample**
```json
{
  "code": "500",
  "message": {
    "code": "ER013",
    "params": []
  }
}
```

---

## 3. Flow xử lý

```mermaid
flowchart TD
    Start([Bắt đầu]) --> Validate[1. Validate parameter]

    Validate --> CheckParam{employeeId<br/>có tồn tại?}
    CheckParam -->|Không| Err001[Trả lỗi ER001<br/>params: "ＩＤ"]
    CheckParam -->|Có| CheckDB{employeeId tồn tại<br/>trong bảng employees?}

    CheckDB -->|Không| Err013[Trả lỗi ER013<br/>params: "ＩＤ"]
    CheckDB -->|Có| GetEmp[2. Lấy thông tin nhân viên<br/>employees + departments]

    GetEmp --> GetCert[3. Lấy danh sách chứng chỉ<br/>certifications + employees_certifications]
    GetCert --> Success[4. Tạo response thành công<br/>code = 200]

    Err001 --> Error[4. Tạo response lỗi<br/>code = 500]
    Err013 --> Error

    Success --> End([Kết thúc])
    Error --> End
```

---

## 4. Chi tiết xử lý

### 4.1. Xử lý common
Không có.

### 4.2. Xử lý chi tiết

#### Bước 1. Validate parameter

**1.1. Validate `employeeId`**

| Điều kiện | Mã lỗi | Params | Hành động |
|-----------|--------|--------|-----------|
| Parameter không tồn tại | ER001 | `"ＩＤ"` | Chuyển sang Bước 4 (response lỗi) |
| Không tồn tại trong bảng `employees.employee_id` | ER013 | `"ＩＤ"` | Chuyển sang Bước 4 (response lỗi) |

#### Bước 2. Lấy thông tin chi tiết nhân viên

**2.1. Danh sách bảng sử dụng**

| No | Tên bảng logic       | Bảng vật lý  | Create | Refer | Update | Xóa |
|----|----------------------|--------------|--------|-------|--------|-----|
| 1  | Thông tin nhân viên  | employees    |        | ○     |        |     |
| 2  | Thông tin bộ phận    | departments  |        | ○     |        |     |

**2.2. Các trường lấy ra**

| No | Bảng        | Trường                |
|----|-------------|-----------------------|
| 1  | employees   | employee_id           |
| 2  | employees   | employee_name         |
| 3  | employees   | employee_birth_date   |
| 4  | employees   | employee_email        |
| 5  | employees   | employee_telephone    |
| 6  | employees   | employee_name_kana    |
| 7  | employees   | employee_login_id     |
| 8  | departments | department_id         |
| 9  | departments | department_name       |

**2.3. Điều kiện kết hợp**

| No | Bảng nguồn | Trường nguồn  | Bảng đích   | Trường đích   | Loại join  |
|----|------------|---------------|-------------|---------------|------------|
| 1  | employees  | department_id | departments | department_id | INNER JOIN |

#### Bước 3. Lấy thông tin chứng chỉ tiếng Nhật

**3.1. Danh sách bảng sử dụng**

| No | Tên bảng logic                          | Bảng vật lý              | Create | Refer | Update | Xóa |
|----|-----------------------------------------|--------------------------|--------|-------|--------|-----|
| 1  | Thông tin chứng chỉ tiếng Nhật          | certifications           |        | ○     |        |     |
| 2  | Thông tin nhân viên – chứng chỉ tiếng Nhật | employees_certifications |        | ○     |        |     |

**3.2. Các trường lấy ra**

| No | Bảng                     | Trường             |
|----|--------------------------|--------------------|
| 1  | certifications           | certification_id   |
| 2  | certifications           | certification_name |
| 3  | employees_certifications | start_date         |
| 4  | employees_certifications | end_date           |
| 5  | employees_certifications | score              |

**3.3. Điều kiện kết hợp**

| No | Bảng nguồn               | Trường nguồn     | Bảng đích      | Trường đích      | Loại join  |
|----|--------------------------|------------------|----------------|------------------|------------|
| 1  | employees_certifications | certification_id | certifications | certification_id | INNER JOIN |

**3.4. Sort**

| No | Bảng           | Trường              | Thứ tự |
|----|----------------|---------------------|--------|
| 1  | certifications | certification_level | ASC    |

**3.5. WHERE**

| No | Bảng                     | Trường      | Điều kiện                                      |
|----|--------------------------|-------------|------------------------------------------------|
| 1  | employees_certifications | employee_id | = `employeeId` từ request parameter            |

#### Bước 4. Tạo dữ liệu response

**Trường hợp thành công (không có lỗi)**

| No. | Key                  | Giá trị                              |
|-----|----------------------|--------------------------------------|
| 1   | code                 | `200`                                |
| 2   | employeeId           | Lấy từ Bước 2                        |
| 3   | employeeName         | Lấy từ Bước 2                        |
| 4   | employeeBirthDate    | Lấy từ Bước 2                        |
| 5   | departmentId         | Lấy từ Bước 2                        |
| 6   | departmentName       | Lấy từ Bước 2                        |
| 7   | employeeEmail        | Lấy từ Bước 2                        |
| 8   | employeeTelephone    | Lấy từ Bước 2                        |
| 9   | employeeNameKana     | Lấy từ Bước 2                        |
| 10  | employeeLoginId      | Lấy từ Bước 2                        |
| 11  | certifications       | Lấy từ Bước 3 (mảng)                 |
|     | └ certificationId    |                                      |
|     | └ certificationName  |                                      |
|     | └ startDate          |                                      |
|     | └ endDate            |                                      |
|     | └ score              |                                      |

**Trường hợp có lỗi**

| No. | Key     | Giá trị                                          |
|-----|---------|--------------------------------------------------|
| 1   | code    | `500`                                            |
| 2   | message | `{ "code": "<mã lỗi>", "params": [...] }`        |



# Thiết kế API – Get List Certifications

| Thông tin | Giá trị |
|-----------|---------|
| Tên system | Manager User |
| Loại system | Thiết kế API |
| Người tạo | ThanhPD |
| Ngày tạo | 2023-01-04 |
| Người update | ThanhPD |
| Ngày update | 2023-01-04 |
| Version | 0.1 |
| Hạng mục | Get List certifications |

---

## 1. Lịch sử thay đổi

| Date | Người update | Version | Nội dung thay đổi | Ngày phê chuẩn | Người phê chuẩn |
|------|--------------|---------|-------------------|----------------|-----------------|
| 2023-01-04 | ThanhPD | 0.1 | Tạo mới tài liệu | | |

---

## 2. Khái quát

Lấy thông tin danh sách chứng chỉ tiếng Nhật.

### 2.1. Request

#### Request URL

| No. | Service | API name | Method HTTP | Note |
|-----|---------|----------|-------------|------|
| 1 | certifications | Get List certifications | GET | |

#### Request Parameter

| No. | Parameter | Bắt buộc | Kiểu | Giá trị default | Tên hạng mục | Note |
|-----|-----------|----------|------|-----------------|--------------|------|
| | | | | | | |

**Sample**

```
GET /certifications
```

### 2.2. Response

#### Trường hợp thành công

| No. | json key name | Kiểu | Note |
|-----|---------------|------|------|
| 1 | code | number | |
| 2 | certifications | array | Mảng chứa thông tin chứng chỉ tiếng Nhật |
| 3 | └ certificationId | number | |
| 4 | └ certificationName | string | |

**Sample**

```json
{
  "code": "200",
  "certifications": [
    {
      "certificationId": "1",
      "certificationName": "Trình độ tiếng Nhật cấp 1"
    },
    {
      "certificationId": "2",
      "certificationName": "Trình độ tiếng Nhật cấp 2"
    }
  ]
}
```

#### Trường hợp lỗi

| No. | json key name | Kiểu | Note |
|-----|---------------|------|------|
| 1 | code | number | |
| 2 | message | object | Nội dung lỗi |

**Sample**

```json
{
  "code": "500",
  "message": {
    "code": "ER023",
    "params": []
  }
}
```

---

## 3. Flow xử lý

*(Không có nội dung trong tài liệu gốc)*

---

## 4. Chi tiết xử lý

### 4.1. Xử lý common

`<Không có>`

### 4.2. Xử lý chi tiết

#### 4.2.1. Get thông tin chứng chỉ tiếng Nhật

**Get tất cả chứng chỉ từ database**

**Danh sách bảng sử dụng**

| No | Tên bảng logic | ID bảng vật lý | Create | Refer | Update | Xóa |
|----|----------------|----------------|--------|-------|--------|-----|
| 1 | Thông tin chứng chỉ tiếng Nhật | certifications | | 〇 | | |

**Table access**

Hạng mục lấy (trường hợp get data hiển thị màn hình):

| No | Tên bảng | Alias | Tên trường |
|----|----------|-------|------------|
| 1 | certifications | - | certification_id |
| 2 | certifications | - | certification_name |

#### 4.2.2. Tạo dữ liệu response cho API

**Trường hợp không có lỗi**

| No. | Key | Giá trị | Note |
|-----|-----|---------|------|
| 1 | code | 200 | |
| 2 | certifications | Lấy giá trị từ bước Get | |
| 3 | └ certificationId | | |
| 4 | └ certificationName | | |

**Trường hợp có lỗi**

| No. | Key | Giá trị | Note |
|-----|-----|---------|------|
| 1 | code | 500 | |
| 2 | message | `{ "code": "ER023", "params": [] }` | |

Kết thúc xử lý.

---

## 5. Tham chiếu

| No. | Mã tham chiếu | Tài liệu tham chiếu |
|-----|---------------|---------------------|
| | | |
