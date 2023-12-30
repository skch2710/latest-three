package com.springboot.latestthree.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.latestthree.dao.EmployeeDAO;
import com.springboot.latestthree.dto.EmployeeDTO;
import com.springboot.latestthree.dto.JwtDTO;
import com.springboot.latestthree.dto.LoginRequest;
import com.springboot.latestthree.dto.LoginResponse;
import com.springboot.latestthree.dto.Result;
import com.springboot.latestthree.exception.CustomException;
import com.springboot.latestthree.mapper.ObjectMapper;
import com.springboot.latestthree.model.Employee;
import com.springboot.latestthree.service.LoginService;
import com.springboot.latestthree.util.CacheService;
import com.springboot.latestthree.util.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
	
	private ObjectMapper MAPPER = ObjectMapper.INSTANCE;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private EmployeeDAO employeeDAO;

	/** The otp service. */
	@Autowired
	public CacheService cacheService;

	@Autowired
	private JwtUtil jwtUtil;

	@Value("${app.isOtpEnable}")
	private Boolean isOtpEnable;

	@Override
	public Result login(LoginRequest request) {
		Result result = null;
		LoginResponse loginResponse = null;
		try {
			result = new Result();
			loginResponse = new LoginResponse();
			Employee employee = employeeDAO.findByEmailIdIgnoreCase(request.getEmailId().toLowerCase());

			if (employee == null) {
				result.setErrorMessage("Invalid Email Address or Not Registered.");
				result.setStatusCode(HttpStatus.BAD_REQUEST.value());
			} else {
				if (!bCryptPasswordEncoder.matches(request.getPassword(), employee.getPasswordSalt())) {
					result.setErrorMessage("Invalid PassWord.");
					result.setStatusCode(HttpStatus.BAD_REQUEST.value());
				} else {
					loginResponse.setIsOtpEnable(isOtpEnable);
					if (isOtpEnable) {
						String otp = cacheService.generateOTP(request.getEmailId().toLowerCase().trim());
						loginResponse.setOtp(otp);
						result.setData(loginResponse);
						result.setSuccessMessage("OTP has been send to Mail");
						result.setStatusCode(HttpStatus.OK.value());
					} else {
						JwtDTO jwtDTO = jwtUtil.getToken(request);
						EmployeeDTO employeeDTO = MAPPER.fromEmployeeModel(employee);
						loginResponse.setEmployee(employeeDTO);
						loginResponse.setJwtDTO(jwtDTO);
						result.setData(loginResponse);
						result.setSuccessMessage("Login Succesfully.....");
						result.setStatusCode(HttpStatus.OK.value());
					}
				}
			}
		} catch (Exception e) {
			log.error("Error in login...::", e);
			throw new CustomException("Error in Login :: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result verifyOTP(LoginRequest request) {
		Result resut = null;
		LoginResponse loginResponse = null;
		try {
			resut = new Result();
			loginResponse = new LoginResponse();
			String emailId = request.getEmailId().toLowerCase().trim();
			String serverOtp = cacheService.getOtp(emailId);
			if (request.getOtp().equals(serverOtp)) {
				Employee employee = employeeDAO.findByEmailIdIgnoreCase(emailId);
				resut.setStatusCode(HttpStatus.OK.value());
				resut.setSuccessMessage("OTP is successfully validated");
				cacheService.clearOTP(emailId);

				JwtDTO jwtDTO = jwtUtil.getToken(request);
				loginResponse.setEmployee(MAPPER.fromEmployeeModel(employee));
				loginResponse.setJwtDTO(jwtDTO);
				resut.setData(loginResponse);

			} else if (Integer.valueOf(serverOtp) == 0) {
				resut.setStatusCode(HttpStatus.NOT_FOUND.value());
				resut.setSuccessMessage("OTP is expired");
			} else {
				resut.setStatusCode(HttpStatus.NOT_FOUND.value());
				resut.setSuccessMessage("OTP is invalid");
			}
		} catch (Exception e) {
			log.error("Error in verifyOTP ... ", e);
			throw new CustomException("Error in verifyOTP :: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resut;
	}

}
