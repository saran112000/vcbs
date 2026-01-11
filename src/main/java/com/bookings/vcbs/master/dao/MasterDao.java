package com.bookings.vcbs.master.dao;


import java.util.List;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDesignationDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDTO;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.RoleSecurityDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.modal.Login;

public interface MasterDao {
	
	public List<Object[]> getUserDetails(String userName, String labcode);
    
	public List<EmployeeDivisionDTO> findAll();

	public EmployeeDivision findById(Long id);

	public Long save(EmployeeDivision division);

	public Long delete(Long divisionId, String userName);
	
	public List<EmployeeDTO> getEmployeeList();

	public EmployeeDivision getParticularDivisionDetails(Long divisionId);

	public List<EmployeeDesignationDTO> getEmployeeDesignationList();

	public List<EmployeeDTO> findAllEmployees();

	public Employee findEmployeeById(Long empId);

	public Long saveEmployee(Employee employeeEntity);

	public Long deleteEmployee(Long empId, String userName);

	public List<LoginDTO> findAllLogins();

	public Login findLoginById(Long loginId);

	Long saveLogin(Login login);

	Long deleteLogin(Long loginId, String userName);

	public List<RoleSecurityDTO> getRoleSecurityList();

	public boolean existsByUsername(String username);

	boolean existsByEmpNo(String empNo);

	boolean existsByDivisionCode(String divisionCode);

	public List<MainModuleDTO> getMainModuleList();

	public List<SubModuleDTO> getSubModuleList();
}
