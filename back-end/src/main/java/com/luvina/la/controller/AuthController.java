package com.luvina.la.controller;

import com.luvina.la.config.jwt.AuthUserDetails;
import com.luvina.la.config.jwt.JwtTokenProvider;
import com.luvina.la.config.jwt.UserDetailsServiceImpl;
import com.luvina.la.entity.EmployeeEntity;
import com.luvina.la.payload.request.CreateAccountRequest;
import com.luvina.la.payload.request.LoginRequest;
import com.luvina.la.payload.response.LoginResponse;
import com.luvina.la.repository.EmployeeEntityRepository;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller xử lý xác thực và các tiện ích tài khoản.
 */
@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    final JwtTokenProvider tokenProvider;
    final AuthenticationManager authenticationManager;
    final UserDetailsServiceImpl userDetailsService;
    final EmployeeEntityRepository employeeEntityRepository;
    final PasswordEncoder passwordEncoder;
    final com.luvina.la.mapper.EmployeeMapper employeeMapper;

    AuthController(
            AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            UserDetailsServiceImpl userDetailsService,
            EmployeeEntityRepository employeeEntityRepository,
            PasswordEncoder passwordEncoder,
            com.luvina.la.mapper.EmployeeMapper employeeMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
        this.employeeEntityRepository = employeeEntityRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Login api
     *
     * @param loginRequest thông tin đăng nhập
     * @param request HttpServletRequest
     * @return LoginResponse
     */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = tokenProvider.generateToken((AuthUserDetails) authentication.getPrincipal());
            return new LoginResponse(accessToken);
        } catch (UsernameNotFoundException | BadCredentialsException ex) {
            log.warn(ex.getMessage());
            errors.put("code", "100");
        } catch (Exception ex) {
            log.warn(ex.getMessage());
            // unknown error
            errors.put("code", "000");
        }
        return new LoginResponse(errors);
    }

    /**
     * API tạo tài khoản test (Public endpoint).
     * Dùng để nhanh chóng tạo user test đăng nhập và thao tác dữ liệu.
     *
     * @param request thông tin tài khoản cần tạo
     * @return ResponseEntity chứa kết quả tạo tài khoản
     */
    @PostMapping({"/register", "/create-account"})
    public ResponseEntity<Map<String, Object>> createAccount(@RequestBody CreateAccountRequest request) {
        Map<String, Object> response = new HashMap<>();

        // Validate cơ bản username & password
        if (request.getUsername() == null || request.getUsername().trim().isEmpty()) {
            response.put("code", 400);
            response.put("message", "Username không được để trống.");
            return ResponseEntity.badRequest().body(response);
        }

        if (request.getPassword() == null || request.getPassword().trim().isEmpty()) {
            response.put("code", 400);
            response.put("message", "Password không được để trống.");
            return ResponseEntity.badRequest().body(response);
        }

        String username = request.getUsername().trim();

        // Kiểm tra xem username đã tồn tại chưa
        if (employeeEntityRepository.existsByEmployeeLoginId(username)) {
            response.put("code", 400);
            response.put("message", "Tên tài khoản '" + username + "' đã tồn tại.");
            return ResponseEntity.badRequest().body(response);
        }

        // Tạo mới EmployeeEntity thông qua MapStruct Mapper
        EmployeeEntity employee = employeeMapper.toEntity(request);
        employee.setEmployeeLoginPassword(passwordEncoder.encode(request.getPassword().trim()));

        // Tên nhân viên (mặc định lấy theo username nếu không truyền)
        if (employee.getEmployeeName() == null || employee.getEmployeeName().trim().isEmpty()) {
            employee.setEmployeeName(username);
        } else {
            employee.setEmployeeName(employee.getEmployeeName().trim());
        }

        // Email (mặc định username + @luvina.net nếu không truyền)
        if (employee.getEmployeeEmail() == null || employee.getEmployeeEmail().trim().isEmpty()) {
            employee.setEmployeeEmail(username + "@luvina.net");
        } else {
            employee.setEmployeeEmail(employee.getEmployeeEmail().trim());
        }

        // Phòng ban (mặc định 1 nếu không truyền)
        if (employee.getDepartmentId() == null) {
            employee.setDepartmentId(1L);
        }

        // Vai trò (mặc định USER nếu không truyền)
        if (employee.getEmployeeRole() == null || employee.getEmployeeRole().trim().isEmpty()) {
            employee.setEmployeeRole("USER");
        } else {
            employee.setEmployeeRole(employee.getEmployeeRole().trim().toUpperCase());
        }

        // Lưu vào cơ sở dữ liệu
        EmployeeEntity saved = employeeEntityRepository.save(employee);

        response.put("code", 200);
        response.put("message", "Tạo tài khoản test thành công.");
        response.put("employeeId", saved.getEmployeeId());
        response.put("username", saved.getEmployeeLoginId());
        response.put("employeeName", saved.getEmployeeName());
        response.put("employeeEmail", saved.getEmployeeEmail());
        response.put("departmentId", saved.getDepartmentId());
        response.put("employeeRole", saved.getEmployeeRole());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * test token API
     *
     * @return
     */
    @RequestMapping("/test-auth")
    public Map<String, String> testAuth() {
        Map<String, String> testData = new HashMap<>();
        testData.put("msg", "Token is valid");
        return testData;
    }
}
