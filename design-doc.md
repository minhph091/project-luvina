# Thiết kế màn hình - Manage User (TKMH)

**Hệ thống:** Manage User  
**Loại:** Thiết kế màn hình (TKCB)  
**Người tạo / Update:** LongLD  
**Ngày:** 2023-06-01  
**Version:** 0.1

---

## 1. Lịch sử thay đổi

| Date | Người thay đổi | Version | Nội dung thay đổi |
|------|----------------|---------|-------------------|
| 2023-06-01 | LongLD | 0.1 | Tạo mới |

---

## 2. Layout MH (Bố cục màn hình)

### ■ Login (ADM001)

- Màn hình yêu cầu nhập username/password để vào hệ thống
- URL vào là http(s)://ip_address/login

### ■ Nhân viên > List nhân viên (ADM002)

- Màn hình list nhân viên
- URL vào là http(s)://ip_address/user/list
- Không hiển thị người dùng có role admin (ADMIN) trên danh sách nhân viên
- Có các chức năng chính
- Search: search all và điều kiện (có sort)
- Add: Link đến màn hình add mới ADM004
- Sort: sort theo các trường
- 氏名 (name)
- 日本語能力 (trình độ JP)
- 失効日 (ngày chứng chỉ JP hết hạn)
- Chi tiết: Link đến màn hình chi tiết ADM003
- Paging: Phân trang, mỗi page 20 bản ghi

### ■ Nhân viên > Chi tiết nhân viên (ADM003)

- Màn hình hiển thị chi tiết nhân viên
- Có các chức năng chính:
- View: chi tiết một nhận viên

### ■ Nhân viên > Edit/Add nhân viên (ADM004)

- Delete: Delete nhân viên trong CSDL
- Màn hình edit/add nhân viên
- Khi đi từ màn hình chi tiết: Sẽ là màn hình edit nhân viên
- Khi đi từ màn hình list nhân viên: Sẽ là màn hình add nhân viên
- Có các chức năng chính
- Hiển thị ban đầu:
- `- Trường hợp add: các Trường để rỗng
- `- Trường hợp edit: các Trường hiển thị thông tin của nhân viên

### ■ Nhân viên > Confirm add/edit nhân viên (ADM005)

- Màn hình xác nhận thông tin nhân viên trước khi update vào database
- Hiển thị ban đầu:
- Hiện thi thông tin hợp lệ do user nhập vào từ màn hình add\edit
- để xác nhận trước khi update vào database
- Có các chức năng chính

### ■ Nhân viên > Complete add/edit/delete nhân viên (ADM006)

- Màn hình hiển thị thông báo đã update/add/delete user thành công

### ■ Common > System Error

---

## 3. Định nghĩa hạng mục

### ■Login (ADM001)

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Title | O | － | Label | － |
| 3 | Username | I | 〇 | Text | Tiếng Anh halfsize |
| 4 | Password | I | 〇 | Password | Tiếng Anh halfsize |
| 5 | Login | O | － | Button | － |

### ■ Nhân viên > List nhân viên  (ADM002)

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Sign out | O | － | Link | － |
| 3 | Top | O | － | Link | － |
| 4 | Title | O | － | Label | － |
| 5 | Title hạng mục search Fullname | O | － | Label | － |
| 6 | Điều kiện search Fullname | I | － | Text | － |
| 7 | Title hạng mục search Department | O | － | Label | － |
| 8 | Điều kiện search Department | I | － | Dropdown List | － |
| 9 | Button Search | － | － | Button | － |
| 10 | Button Add mới | － | － | Button | － |
| 11 | Header Bảng danh sách nhân viên | O | Label | － |
| 12 | Danh sách ID nhân viên | O | － | Link | － |
| 13 | Danh sách tên nhân viên | O | － | Label | － |
| 14 | Danh sách ngày sinh nhân viên | O | － | Label | － |
| 15 | Danh sách phòng ban nhân viên | O | － | Label | － |
| 16 | Danh sách email nhân viên | O | － | Label | － |
| 17 | Danh sách số điện thoại nhân viên | O | － | Label | － |
| 18 | Danh sách chứng chỉ JP nhân viên | O | － | Label | － |
| 19 | Danh sách ngày hết han chứng chỉ JP nhân viên | O | － | Label | － |
| 20 | Danh sách điểm thi chứng chỉ JP nhân viên | O | － | Label | － |
| 21 | Phân trang | － | － | Button |
| 22 | Footer | O | － | Label |

### ■ Nhân viên > Chi tiết nhân viên  (ADM003)

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Sign out | O | － | Link | － |
| 3 | Top | O | － | Link | － |
| 4 | Title thông tin nhân viên cơ bản | O | － | Label | － |
| 5 | Title hạng mục account nhân viên | O | － | Label | － |
| 6 | Hạng mục account nhân viên | O | － | Label | Tiếng Anh halfsize |
| 7 | Title hạng mục phòng ban | O | － | Label | － |
| 8 | Hạng mục phòng ban | O | － | Label | － |
| 9 | Title hạng mục tên nhân viên | O | － | Label | － |
| 10 | Hạng mục tên nhân viên | O | － | Label | － |
| 11 | Title hạng mục tên katakana nhân viên | O | － | Label | － |
| 12 | Hạng mục tên katakana nhân viên | O | － | Label | Kana halfsize |
| 13 | Title hạng mục ngày sinh nhân viên | O | － | Label | － |
| 14 | Hạng mục ngày sinh nhân viên | O | － | Label | Tiếng Anh halfsize |
| 15 | Title hạng mục email nhân viên | O | － | Label | － |
| 16 | Hạng mục email nhân viên | O | － | Label | Tiếng Anh halfsize |
| 17 | Title Số điện thoại nhân viên | O | － | Label |
| 18 | Hạng mục số điện thoại nhân viên | O | － | Label | Tiếng Anh halfsize |
| 19 | Title thông tin chứng chỉ JP của nhân viên | O | － | Label | － |
| 20 | Title hạng mục tên chứng chỉ JP nhân viên | O | － | Label | － |
| 21 | Hạng mục tên chứng chỉ JP nhân viên | O | － | Label | － |
| 22 | Title ngày hiệu lực của chứng chỉ JP | O | － | Label | － |
| 23 | Hạng mục ngày hiệu lực của chứng chỉ JP | O | － | Label | Tiếng Anh halfsize |
| 24 | Title ngày hết hạn của chứng chỉ JP | O | － | Label | － |
| 25 | Hạng mục ngày hết hạn của chứng chỉ JP | O | － | Label | Tiếng Anh halfsize |
| 26 | Title điểm số chứng chỉ JP | O | － | Label | － |
| 27 | Hạng mục điểm số chứng chỉ JP | O | － | Label | Số halfsize |
| 28 | Button edit nhân viên | － | － | Button |
| 29 | Button delete nhân viên | － | － | Button |
| 30 | Button cancel | － | － | Button |
| 31 | Footer | O | － | Label |

### ■ Nhân viên > edit/add nhân viên  (ADM004)

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Sign out | O | － | Link | － |
| 3 | Top | O | － | Link | － |
| 4 | Title thông tin nhân viên cơ bản | O | － | Label | － |
| 4a | Vùng thông báo lỗi | O | － | Label | － |
| 5 | Title hạng mục account nhân viên | O | － | Label | － |
| 6 | Hạng mục account nhân viên | I | 〇 | Text | Tiếng Anh halfsize |
| 7 | Title hạng mục phòng ban | O | － | Label | － |
| 8 | Hạng mục phòng ban | I | 〇 | Dropdown List | － |
| 9 | Title hạng mục tên nhân viên | O | － | Label | － |
| 10 | Hạng mục tên nhân viên | I | 〇 | Text | － |
| 11 | Title hạng mục tên katakana nhân viên | O | － | Label | － |
| 12 | Hạng mục tên katakana nhân viên | O | 〇 | Text | Kana halfsize |
| 13 | Title hạng mục ngày sinh nhân viên | O | － | Label | － |
| 14 | Hạng mục ngày sinh nhân viên | I | 〇 | Text | Tiếng Anh halfsize |
| 15 | Title hạng mục email nhân viên | O | － | Label | － |
| 16 | Hạng mục email nhân viên | I | 〇 | Text | Tiếng Anh halfsize |
| 17 | Title Số điện thoại nhân viên | O | － | Label |
| 18 | Hạng mục số điện thoại nhân viên | I | 〇 | Text | Tiếng Anh halfsize |
| 19 | Title mật khẩu nhân viên | O | － | Label |
| 20 | Hạng mục mật khẩu nhân viên | I | － | Password |
| 21 | Title nhâp lại mật khẩu nhân viên | O | － | Label | － |
| 22 | Hạng mục nhắc lại mật khẩu nhân viên | I | － | Password |
| 23 | Title thông tin chứng chỉ JP của nhân viên | O | － | Label | － |
| 24 | Title hạng mục tên chứng chỉ JP nhân viên | O | － | Label | － |
| 25 | Hạng mục tên chứng chỉ JP nhân viên | I | － | Dropdown List |
| 26 | Title ngày hiệu lực của chứng chỉ JP | O | － | Label | － |
| 27 | Hạng mục ngày bắt đầu hiệu lực của chứng chỉ JP | I | － | Text | Tiếng Anh halfsize |
| 28 | Title ngày hết hạn của chứng chỉ JP | O | － | Label | － |
| 29 | Hạng mục ngày hết hạn của chứng chỉ JP | I | － | Text | Tiếng Anh halfsize |
| 30 | Title điểm số chứng chỉ JP | O | － | Label | － |
| 31 | Hạng mục điểm số chứng chỉ JP | I | － | Text | Số halfsize |
| 32 | Button xác nhận | － | － | Button |
| 33 | Button cancel | － | － | Button |
| 34 | Footer | O | － | Label |

### ■ Nhân viên > xác nhận thông tin edit/add nhân viên  (ADM005)

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Sign out | O | － | Link | － |
| 3 | Top | O | － | Link | － |
| 4 | Title thông tin nhân viên cơ bản | O | － | Label | － |
| 5 | Title hạng mục account nhân viên | O | － | Label | － |
| 6 | Hạng mục account nhân viên | O | － | Label | Tiếng Anh halfsize |
| 7 | Title hạng mục phòng ban | O | － | Label | － |
| 8 | Hạng mục phòng ban | O | － | Label | － |
| 9 | Title hạng mục tên nhân viên | O | － | Label | － |
| 10 | Hạng mục tên nhân viên | O | － | Label | － |
| 11 | Title hạng mục tên katakana nhân viên | O | － | Label | － |
| 12 | Hạng mục tên katakana nhân viên | O | － | Label | Kana halfsize |
| 13 | Title hạng mục ngày sinh nhân viên | O | － | Label | － |
| 14 | Hạng mục ngày sinh nhân viên | O | － | Label | Tiếng Anh halfsize |
| 15 | Title hạng mục email nhân viên | O | － | Label | － |
| 16 | Hạng mục email nhân viên | O | － | Label | Tiếng Anh halfsize |
| 17 | Title Số điện thoại nhân viên | O | － | Label |
| 18 | Hạng mục số điện thoại nhân viên | O | － | Label | Tiếng Anh halfsize |
| 19 | Title thông tin chứng chỉ JP của nhân viên | O | － | Label | － |
| 20 | Title hạng mục tên chứng chỉ JP nhân viên | O | － | Label | － |
| 21 | Hạng mục tên loại chứng chỉ JP nhân viên | O | － | Label | － |
| 22 | Title ngày hiệu lực của chứng chỉ JP | O | － | Label | － |
| 23 | Hạng mục ngày hiệu lực của chứng chỉ JP | O | － | Label | Tiếng Anh halfsize |
| 24 | Title ngày hết hạn của chứng chỉ JP | O | － | Label | － |
| 25 | Hạng mục ngày hết hạn của chứng chỉ JP | O | － | Label | Tiếng Anh halfsize |
| 26 | Title điểm số chứng chỉ JP | O | － | Label | － |
| 27 | Hạng mục điểm số chứng chỉ JP | O | － | Label | Số halfsize |
| 28 | Button update nhân viên | － | － | Button |
| 29 | Button cancel | － | － | Button |
| 30 | Footer | O | － | Label |

### ■ Nhân viên > complete edit/add nhân viên  (ADM006)

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Sign out | O | － | Link | － |
| 3 | Top | O | － | Link | － |
| 4 | Tilte | O | － | Label | － |
| 5 | Buton OK | － | － | Button |
| 6 | Footer | O | － | Label | － |

### ■ System error

| No | Tên hạng mục | I/O | Bắt buộc | Loại control | Type |
|---|---|---|---|---|---|
| 1 | Logo | O | － | Image | － |
| 2 | Sign out | O | － | Link | － |
| 3 | Top | O | － | Link | － |
| 4 | Message | O | － | Label |
| 5 | Buton OK | － | － | Button |
| 6 | Footer | O | － | Label |

---

## 4. API sử dụng

- Manage User | Thiết kế màn hình | LongLD | 2023-06-01 | LongLD | 2023-06-01 | 0.1
- ■Danh sách API sử dụng
- No. | Tên service | Tên vật lý | Phương thức | File tham chiếu
- 1 | Login | /login | POST | TKAPI_Login.xslx
- 2 | List departments | /department | GET | TKAPI_ListDepartments.xslx
- 3 | List certifications | /certification | GET | TKAPI_ListCertifications.xslx
- 4 | List employees | /employee | GET | TKAPI_ListEmployee.xslx
- 5 | Add employee | /employee | POST | TKAPI_AddEmployee.xslx
- 6 | Edit employee | /employee | PUT | TKAPI_UpdateEmployee.xslx
- 7 | Delete employee | /employee/:id | DELETE | TKAPI_DeleteEmployee.xslx
- 8 | Get employee | /employee/:id | GET | TKAPI_GetEmployee.xslx

---

## 5. Flow xử lý

- Manage User Thiết kế màn hình LongLD 2023-06-01 LongLD 2023-06-01 0.1

### ■Xử lý login

### ■Xử lý hiển thị search list nhân viên

### ■Xử lý hiển thị view chi tiết nhân viên

### ■Xử lý delete nhân viên

### ■Xử lý add/edit nhân viên

- Hiển thị ban đầu
- Button Confirm

### ■Xử lý confirm add/update nhân viên

- Hiển thị ban đầu
- Button Update

---

## 6. Chi tiết xử lý

- Manage User Thiết kế màn hình LongLD 2023-06-01 LongLD 2023-06-01 0.1
- I. Xử lý common
- Phần header: (tất cả các màn hình, trừ màn hình login)
- Logout:
- Clear session storage, di chuyển về màn hình login
- Thực hiện scroll lên top
- Lỗi gọi API
- Khi gọi API mà server trả về HTTP status code
- 5xx: redirect sang màn hình System error
- 401, 403: redirect sang màn hình Login
- Các lỗi khác thông báo tại màn hình
- Lỗi truy cập URL không tồn tại
- Khi user cố tình truy cập vào URL ko tồn tại, redirect sang màn hình System error với message "Page not found" (ER022)
- Lỗi bất thường xảy ra
- Redirect sang màn hình System error với message "System Error" (ER023)
- Hạng mục nhập date
- Hạng mục nhập date gồm textbox và icon calendar
- Trong đó textbox ko cho phép nhập trực tiếp bằng keyboard (disable)
- Muốn nhập user phải click chọn calendar từ icon calendar để mở popup calendar như hình dưới,
- User chọn 1 date từ popup calendar thì fill text vào textbox (định dạng yyyy/MM/dd)
- Khi cần disable thì ko cho click từ icon calendar
- Validate
- Tất cả các hạng mục nhập yêu cầu validate thì thực hiện validate ngay khi user tương tác với control
- Với các hạng mục có lỗi thì trên UI hightlight hạng mục đó (tô màu border thành màu đỏ), khi không có lỗi thì để như ban đầu (giống mock)
- Khi hạng mục lỗi, message lỗi hiển thị ngay dưới hạng mục, tô màu đỏ, khi ko có lỗi thì clear message lỗi
- Với các lỗi chung ko liên quan đến hạng mục như server trả về lỗi, gọi API ko thành công,... hiển thị message lỗi ngay dưới header của page (tô màu đỏ)
- Các hạng mục bắt buộc nhập hiển thị kí hiệu (*) màu đỏ ngay sau tiêu đề hạng mục (nếu hạng mục có tiêu đề)
- Focus và tab index
- Với các màn hình nhập: khi mới vào lần đầu, focus con trỏ vào hạng mục nhập đầu tiên
- Các hạng mục để tab index theo thứ tự từ trái qua phải, từ trên xuống dưới
- Dropdown:
- Tất cả các dropdown nếu ko đề cập gì đều có phần tử rỗng ở đầu
- Phân trang
- Hiển thị
- Luôn hiển thị button trang đầu tiên và trang cuối cùng. Nếu tổng số trang = 1 thì không hiển thị phần phân trang
- Hiển thị button trang hiện tại, kèm button trang ngay trước và trang ngay sau của trang hiện tại
- Ví dụ nếu đang ở trang 5, tổng số trang là 15 thì hiển thị như sau < 1 … 4 5 6 … 15 >
- Nếu đang ở trang 1, disable button <
- Nếu đang ở trang cuối cùng, disable button >
- Action
- Click vào button trang số 1, 2, 3… để di chuyển tới trang tương ứng
- Button > : di chuyển đến trang tiếp theo của trang đang hiển thị
- Button < : di chuyển đến trang ngay trước trang đang hiển thị
- Không có action khi click button …
- II.Xử lý chi tiết

### 1. Hiển thị ban đầu

- Khi mới vào hệ thống, kiểm tra xem nếu tồn tại token trong session storage thì redirect sang màn hình list employees ADM002
- Khi không tồn tại token di chuyển sang màn hình login ADM001

### 2. Màn hình login

- Hiển thị ban đầu:
- Để rỗng
- Khi user nhập user name, password: thực hiện validate trực tiếp (xem sheet [Định nghĩa hạng mục] để biết yêu cầu validate, và hiện thị message lỗi nếu có
- Khi user click Login:
  `- Thực hiện validate lại, nếu lỗi hiện thị message lỗi, nếu ko lỗi chuyển sang xử lý bên dưới
  `- Gọi API login (tham chiếu file thiết kế API):
- API trả về status code 200: lưu lại token vào session storage (xem file thiết kế API để biết thông tin reponse trả về), và redirect sang màn hình list employee
- API trả về lỗi khác 200 hoặc mã lỗi login ko thành công: hiển thị message lỗi.

### 3. Màn hình list employee

### 3.1 Hiển thị ban đầu

  `- Khởi tạo điều kiện search ban đầu:
- Hạng mục fullname giá trị rỗng
- Hạng mục department là rỗng
- Page hiện tại = 1
- Hạng mục sort: 氏名, 日本語能力, 失効日 tất cả đều là ASC
- Số record tối đa hiển thị trong page luôn luôn là 20
  `- Gọi API list departments (tham chiếu tài liệu TK API)
- TH API trả về lỗi hiện thị message lỗi: "部門を取得できません"
- TH API thành công, lấy list department từ reponse trả về (xem TK API), binding list department vào dropdownlít (có phần tử rỗng ở đầu)
  `- Gọi API list employees (tham chiếu tài liệu TK API)
- Danh sách nhân viên chỉ hiển thị user thông thường, không bao gồm các tài khoản có role admin (ADMIN)
- TH API trả về lỗi hiện thị message lỗi: "従業員を取得できません"
- TH API thành công, lấy list employee từ reponse trả về (xem TK API), binding list employee vào bảng nhân viên

### 3.2 Binding data vào màn hình list

  `- Clear data trong list trên MH nếu có, Thực hiện binding theo đúng thứ tự response trả về
  `- Cách mapping từng hạng mục từ response tham chiếu sheet Định nghĩa hạng mục
  `- TH response với list rỗng, ẩn control paging, đồng thời hiển thị message "検索条件に該当するユーザが見つかりません。" (MSG005) ngay trên bảng nhân viên
  `- TH response trả về tổng tất cả số record (không tính phân trang) <= 20, ko hiển thị control paging

### 3.3 Action Search

  `- Reset page hiện tại = 1
  `- Giữ nguyên điều kiện sort
  `- Get giá trị fullname ở textbox, department ở dropdownlist cùng với giá trị page, sort ở trên đưa vào tham số để gọi API get employee (tham khảo tài liệu thiết kế API)
- (nếu user ko nhập fullname, hoặc chọn department rỗng mà click search thì ko cần đưa giá trị fullname, department vào tham số để search)
  `- Cách gọi và binding tham khảo phần 3.1 và 3.2

### 3.4 Action Sort

  `- Khi user click vào cột nào có biểu tượng sort thì đảo ngược giá trị sort hiện tại, các cột khác giữa nguyên (tham khảo sheet Định nghĩa hạng mục)
  `- Giữ nguyên điều kiện tìm kiếm
  `- Reset page hiện tại = 1
  `- Gọi API list employees với tham số như trên
  `- Cách gọi và binding tham khảo phần 3.1 và 3.2

### 3.5 Action Paging

  `- Khi user click vào page nào thì thực hiện gọi lại API giữ nguyên điều kiện search và sort
  `- Cách gọi và binding tham khảo phần 3.1 và 3.2

### 3.6 Action Add

  `- User click vào button Add, di chuyển sang màn hình edit\add ADM004

### 3.7 Action view chi tiết

  `- User click vào link ở cột ID nhân viên, redirect sang màn hình view chi tiết ADM003 kèm ID tương ứng (ko để ID trên URL mà truyền qua router)

### 4. Màn hình view chi tiết nhân viên

### 4.1 Hiển thị ban đầu

  `- Kiểm tra xem có trong router có ID hợp lệ không (dạng số), nếu ko có hoặc ko hợp lệ redirect sang màn hình system error
  `- TH có ID hợp lệ gọi API get thông tin một employee tương ứng với ID
- Nếu API trả về lỗi hoặc ko tồn tại employee data di chuyển sang MH system error
- Nếu API trả về 200 thì binding data employee từ response trả về vào các hạng mục (Cách mapping xem định nghĩa hạng mục)

### 4.2 Action Cancel

  `- User click vào button Cancel di chuyển về MH list ADM002, page như trước khi di chuyển

### 4.3 Action Edit

  `- User click vào button Edit di chuyển sang MH edit nhân viên ADM004, gửi kèm theo ID tương ứng (ko để ID trên URL mà truyền qua router)

### 4.4 Action Delete

- - Hiển thị message confirm "削除しますが、よろしいでしょうか。" (MSG004). Nếu user click OK thì xử lý tiếp.
- - User click vào button Delete thực hiện gọi API để xóa data nhân viên trong database (tham khảo thiết kế API)
- TH API trả về trạng thái thành công: di chuyển sang MH complete với mã message được trả về từ API
- TH API trả về lỗi hiển thị ở vùng thông báo lỗi với mã message lấy từ API

### 5. Màn hình edit/add nhân viên

### 5.1 Hiển thị ban đầu

  `- Kiểm tra xem trong router xem có ID hợp lệ ko
- Nếu tồn tại ID trong router thì xác định MH là edit
- Nếu không tồn tại ID trong router là MH add
  `- Gọi các API list departments, list cerfiticates  để binding data vào các dropdown list (có phần tử rỗng ở đầu)
- TH API trả về lỗi thì thông báo lỗi lên MH (ngay phía dưới phần Header), ngược lại binding data reponse của các API vào dropdown list (chi tiết data xem tài liệu TK API)
  `- Kiểm tra xem có phải là từ MH confirm quay về hay không? (check qua router có data ko)
- TH không phải từ MH confirm quay về về
  `- Nếu là mode edit:
- gọi API get employee tương ứng với ID
- Nếu API trả về lỗi hoặc ko tồn tại employee data di chuyển sang MH system error
- Nếu API trả về 200 thì binding data employee từ response trả về vào các hạng mục nhập (Cách mapping xem định nghĩa hạng mục)
  `- Nêú la mode add:
- Để rỗng các hạng mục nhập trên MH
- TH từ MH confirm quay về:
- Lấy data từ MH confirm trả lại, binding lên các hạng mục nhập

### 5.2 Tương tác với các hạng mục nhập

- Các hạng mục nhập có yêu cầu validate thì thực hiện validate ngay khi user tương tác
- Khi hạng mục lỗi, message lỗi hiển thị ngay dưới hạng mục, tô màu đỏ, khi ko có lỗi thì clear message lỗi
- Chú ý các validate đặc biệt
- Validate ngày hiệu lưc/hết hạn của chứng chỉ:
- Validate từng hạng mục theo như sheet Định nghĩa hạng mục
- Khi 2 ngày tháng đã hợp lệ thì validate sao cho ngày hết hạn phải lớn hơn ngày hiệu lực, nếu có lỗi hightlight hạng mục ngày hết hạn, và message lỗi nằm dưới ngày hết hạn
- Validate phần password, re-password
- Khi add y/c bắt buộc nhập, khi edit hiển thị ban đầu ko hiển thị ko yêu cầu nhập
- TH nhập cả 2: hạng mục password validate max-length, hạng mục repassword chỉ validate trùng lặp khi hạng mục password nhập đúng
- TH password nhập đúng, re-password ko nhập => validate trùng lặp re-password
- TH password ko nhập, re-password có nhập => validate trùng lặp
- Xử lý phần chứng chỉ tiếng nhật cho cả add/edit
- Các hạng mục ngày hiệu lực, ngày hết hạn, số điểm chỉ enable khi có chọn dropdown loại chứng chỉ khác rỗng
- Khi dropdown tên loại chứng chỉ thay đổi từ có giá trị về rỗng
- Disable 3 hạng mục trên, và clear hết data, hightlight, message lỗi nếu có của 3 hạng mục, xóa bỏ kí hiệu bắt buộc nhập
- Khi dropdown tên loại chứng chỉ thay đổi từ giá trị rỗng về có giá trị
- Enable 3 hang mục trên
- Đỗi với edit: set giá trị ban đầu cho 3 hạng mục (từ API) nếu có giá trị, và hiển thị kí hiệu bắt buộc nhập
- Đối với add: cứ để rỗng và hiển thị kí hiệu bắt buộc nhập

### 5.3 Action Cancel

- Khi user click cancel
- TH add mới: Di chuyển về MH list ADM002, page như trước khi di chuyển
- TH edit: Di chuyển về MH view chi tiết (gửi kèm ID qua router)

### 5.4 Action Confirm

- Nếu có lỗi thông báo lỗi, nếu ko lỗi di chuyển sang MH confirm ADM005, gửi sang MH confirm toàn bộ data các hạng mục nhập, cùng với ID nếu là edit qua router

### 6. Màn hình confirm

### 6.1 Hiển thị ban đầu

  `- Kiểm tra xem trong router xem có ID hợp lệ ko
- Nếu tồn tại ID trong router thì xác định MH là confirm cho edit
- Nếu không tồn tại ID trong router là MH confirm cho add
  `- Nếu là mode edit:
- gọi API get employee tương ứng với ID
- Nếu API trả về lỗi hoặc ko tồn tại employee data di chuyển sang MH system error
  `- Binding data từ MH edit/add gửi sang lên màn hình (Chi tiết xem sheet Định nghĩa hạng mục)

### 6.2 Action update

- Khi user click button OK
- Gọi API add nếu là MH confim Add hoặc update nếu là MH confirm edit data nhân viên (tham chiếu tài liệu TK API)
- Nếu API trả về lỗi  hiển thị thông báo lỗi ở vùng Thông báo lỗi
- Nếu API trả về thành công di chuyển sang MH complete ADM006

### 6.3 Action Cancel

- Khi user click button Cancel
- Redirect về MH add\edit: gửi lại data đã truyền sang về MH add\edit ADM004 qua router

### 7. Màn hình complete

### 7.1 Hiển thị ban đầu

- Hiển thị message:
- TH add mới thì hiển thị: ユーザの登録が完了しました。 (MSG001)
- TH edit thì hiển thị:  ユーザの更新が完了しました。  (MSG002)
- TH delete thì hiển thị: ユーザの削除が完了しました。 (MSG003)

### 7.2. Action OK

- Di chuyển về MH list ADM002, page 1

---

## 7. Phụ lục

*Không có nội dung chi tiết*

## 8. List tồn đọng

| No. | Nội dung tồn đọng |
|-----|-------------------|
| | *(Trống)* |

## 9. Tham chiếu

| No. | Mã tham chiếu | Tài liệu tham chiếu |
|-----|---------------|---------------------|
| 1 | TKMH | Flow nghiệp vụ_di chuyển màn hình.xlsx |
