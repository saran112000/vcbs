package com.bookings.vcbs.master.service;

import java.util.List;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.projection.LoginProjection;

public interface MasterService {
	
    LoginDetails getUserDetails(String userName, String labcode);

	public List<LoginProjection> getUserManagerList();
	
	public List<EmployeeDivisionDTO> getAllDivisions();

	public EmployeeDivision getDivisionById(Long id);

	public Long saveDivision(EmployeeDivisionDTO division, String userName, String action);

	public void updateDivision(EmployeeDivision division);

	public Long deleteDivision(Long divisionId, String userName);

	public void updateStatus(Long id, Integer isActive);
	
	public List<EmployeeDTO> getEmployeeList();

	public Employee getEmployee(Long empId);

	public void createEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public void deleteEmployee(Long empId);

	public void updateEmployeeStatus(Long empId, Integer isActive);
}
