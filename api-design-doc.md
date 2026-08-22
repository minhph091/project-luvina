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

Lấy thông tin nhân viên và phòng ban cùng thông tin chứng chỉ tiếng Nhật (nếu có) của nhân viên theo điều kiện tìm kiếm để hiển thị màn hình list.

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


