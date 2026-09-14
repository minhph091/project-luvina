# Rule: No Test-Induced Damage (Không Viết Code Phòng Thủ Cho Unit Test)

Áp dụng cho toàn bộ dự án ManageUser (cả **Backend Spring Boot** và **Frontend Next.js/React**).

---

## 1. Nguyên Tắc Cốt Lõi (Core Principles)
1. **Production Code phục vụ Business Logic & User Flow thực tế:**
   - Mã nguồn production chỉ xử lý các trạng thái và kịch bản có thật ở runtime theo đúng tài liệu thiết kế.
2. **Fix the Test, Never Pollute Production Code:**
   - Khi một Unit Test bị fail do mock thiếu dữ liệu hoặc do giả lập trạng thái phi thực tế: **Bắt buộc phải sửa mock data và kịch bản trong file Test**.
   - **Tuyệt đối KHÔNG** thêm câu lệnh `if`, nhánh rẽ phụ, fallback rỗng hay `try-catch` nuốt lỗi vào mã nguồn production chỉ để làm cho test case đó pass.
3. **Strict Contracts & Typing:**
   - Không chuyển prop/field từ bắt buộc thành `optional (?)` hoặc nullable chỉ để thuận tiện cho việc viết mock object trong test.

---

## 2. Quy Tắc Cụ Thể Cho BACKEND (Spring Boot / Java)
- **Không thêm check null giả định cho Mockito:**
  - Trong Controller, Service, Custom Repository: Không thêm các kiểm tra `if (entity == null)`, `if (dependency == null)` hoặc `Optional.empty()` vô nghĩa vào những vị trí mà quan hệ CSDL (Foreign Key, Not Null) hoặc logic nghiệp vụ bảo đảm đối tượng luôn tồn tại.
  - Khi mock test ném `NullPointerException` do Mockito chưa cấu hình stubbing (`when(...).thenReturn(...)`): Sửa file test bằng cách cấu hình đầy đủ mock behavior, KHÔNG sửa Service/Controller.
- **Không bao bọc try-catch phòng thủ cho Test:**
  - Không bọc các khối `try-catch` nuốt exception chỉ để tránh crash trong unit test khi mock object trả về null.
- **Dữ liệu Mock phải hợp lệ:**
  - Khi viết Unit Test cho Service/Controller, dữ liệu entity/DTO mock phải tuân thủ đúng các ràng buộc của schema database và logic tiền điều kiện của API.

---

## 3. Quy Tắc Cụ Thể Cho FRONTEND (Next.js / React / TypeScript)
- **Không tạo nhánh rỗng / form giả lập cho Unit Test:**
  - Không thêm các khối `if (!data && error) return <div id="error">...</div>` hoặc form giả lập chỉ để phục vụ cho test case truyền object rỗng/null kèm lỗi.
- **Không lạm dụng Optional Chaining (`?.`) và Nullish Coalescing (`??`) để chiều Test:**
  - Các trường bắt buộc theo TypeScript type/interface phải được component sử dụng trực tiếp; không bọc `?.` hay `?? ''` vô tội vạ chỉ vì file test truyền mock object thiếu field.
- **Không khai báo Prop dư thừa:**
  - Không khai báo prop trong ComponentProps hoặc nhận prop vào component chỉ để phục vụ assertion của test mà component không hề sử dụng.
- **Mock trong Test phải mô phỏng đúng Runtime:**
  - Các test file (Jest / React Testing Library) phải mock đúng state mà hook hoặc API thật sinh ra; cấm mock các trạng thái mâu thuẫn (như `loading = false`, `employee = null` nhưng lại có `apiError` trong khi runtime chuyển hướng sang trang System Error).
