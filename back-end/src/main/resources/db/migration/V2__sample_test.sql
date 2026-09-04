CREATE TABLE IF NOT EXISTS departments (
                                           department_id BIGINT NOT NULL AUTO_INCREMENT,
                                           department_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (department_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS certifications (
                                              certification_id BIGINT NOT NULL AUTO_INCREMENT,
                                              certification_name VARCHAR(50) NOT NULL,
    certification_level INT NOT NULL,
    PRIMARY KEY (certification_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS employees (
                                         employee_id BIGINT NOT NULL AUTO_INCREMENT,
                                         department_id BIGINT NOT NULL,
                                         employee_name VARCHAR(100) NOT NULL,
    employee_name_kana VARCHAR(255),
    employee_birth_date DATE,
    employee_email VARCHAR(50) NOT NULL,
    employee_telephone VARCHAR(50),
    employee_login_id VARCHAR(50) NOT NULL,
    employee_login_password VARCHAR(100) DEFAULT NULL,
    employee_role VARCHAR(20) NOT NULL,
    PRIMARY KEY (employee_id),
    CONSTRAINT fk_employee_department
    FOREIGN KEY (department_id) REFERENCES departments (department_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE IF NOT EXISTS employees_certifications (
                                                        employee_certification_id BIGINT NOT NULL AUTO_INCREMENT,
                                                        employee_id BIGINT NOT NULL,
                                                        certification_id BIGINT NOT NULL,
                                                        start_date DATE NOT NULL,
                                                        end_date DATE,
                                                        score DECIMAL(5,2),
    PRIMARY KEY (employee_certification_id),
    CONSTRAINT fk_emp_cert_employee
    FOREIGN KEY (employee_id) REFERENCES employees (employee_id),
    CONSTRAINT fk_emp_cert_certification
    FOREIGN KEY (certification_id) REFERENCES certifications (certification_id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- 1. Departments
INSERT INTO departments (department_name)
VALUES
    ('DEV1'),
    ('DEV2'),
    ('DEV3'),
    ('DEV4'),
    ('DEV5');

-- 2. Certifications
INSERT INTO certifications (certification_name, certification_level)
VALUES
    ('Trình độ tiếng Nhật cấp 1', 1),
    ('Trình độ tiếng Nhật cấp 2', 2),
    ('Trình độ tiếng Nhật cấp 3', 3),
    ('Trình độ tiếng Nhật cấp 4', 4),
    ('Trình độ tiếng Nhật cấp 5', 5);

-- 3. Employees (chỉ định rõ employee_id để khớp với phần insert chứng chỉ)
INSERT INTO employees (
    employee_id,
    department_id,
    employee_name,
    employee_name_kana,
    employee_birth_date,
    employee_email,
    employee_telephone,
    employee_login_id,
    employee_login_password,
    employee_role
)
VALUES
-- employee_id = 2
(2, 1, 'Pham Thi Thanh Nga', 'グループ', '2002-02-02', 'ngantt@luvina.net', '778520123', 'ngaptt265', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'EMPLOYEE'),

-- employee_id = 3
(3, 2, 'QuỳnhNga/', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt266', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'EMPLOYEE'),

-- employee_id = 4
(4, 3, 'Quỳnh%Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt267', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'EMPLOYEE'),

-- employee_id = 5
(5, 3, 'Quỳnh_Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt268', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'EMPLOYEE'),

-- employee_id = 6
(6, 3, 'Quỳnh;Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt269', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'EMPLOYEE'),

-- employee_id = 7
(7, 4, 'Quỳnh,Nga', 'グループ', '2002-02-02', 'nga@luvina.net', '778520123', 'ngaptt270', '$2a$10$r.XIN4K9vTioiuYQwaTop.UVQ5r5FvrKk2V5Orm9Hc6n4i9Tvjthy', 'EMPLOYEE');

-- 4. employees_certifications
INSERT INTO employees_certifications (
    employee_id,
    certification_id,
    start_date,
    end_date,
    score
)
VALUES
    (2, 5, '2015-03-06', '2021-03-04', 89.00),
    (3, 1, '2016-04-07', '2022-08-09', 123.00),
    (4, 2, '2017-05-04', '2024-06-09', 123.00),
    (5, 4, '2019-04-05', '2024-03-08', 124.00),
    (6, 4, '2017-02-06', '2025-02-02', 127.00),
    (7, 3, '2018-03-03', '2025-02-01', 145.00);