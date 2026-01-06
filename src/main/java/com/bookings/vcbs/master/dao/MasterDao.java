package com.bookings.vcbs.master.dao;


import java.util.List;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;

public interface MasterDao {
	
	public List<Object[]> getUserDetails(String userName, String labcode);
    
	public List<EmployeeDivisionDTO> findAll();

	public EmployeeDivision findById(Long id);

	public Long save(EmployeeDivision division);

	public void update(EmployeeDivision division);

	public Long delete(Long divisionId, String userName);
	
	public List<EmployeeDTO> getEmployeeList();

	public Employee getEmployeeById(Long empId);

	public void saveEmployee(Employee employee);

	public void updateEmployee(Employee employee);

	public void deleteEmployee(Long empId);

	public EmployeeDivision getParticularDivisionDetails(Long divisionId);
}
