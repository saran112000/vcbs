package com.bookings.vcbs.master.service;

import com.bookings.vcbs.config.LoginRepository;
import com.bookings.vcbs.master.dao.MasterDao;
import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.projection.LoginProjection;
import com.bookings.vcbs.utils.DtoEntityMapper;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MasterServiceImpl  implements MasterService{

    @Autowired
    private MasterDao masterDao;
    
    @Autowired
    private LoginRepository loginRepository;
    
    @Override
    public LoginDetails getUserDetails(String userName, String labcode) {
        List<Object[]> list = masterDao.getUserDetails(userName, labcode);
        LoginDetails loginDetails = new LoginDetails();
        if (list != null && !list.isEmpty()) {
            Object[] obj = list.get(0);
            if (obj != null) {
                loginDetails.setLoginId(obj[0] != null ? Long.parseLong(obj[0].toString()) : 0L);
                loginDetails.setUserName(obj[1] != null ? obj[1].toString() : null);
                loginDetails.setRoleId(obj[2] != null ? Long.parseLong(obj[2].toString()) : 0);
                loginDetails.setRoleName(obj[3] != null ? obj[3].toString() : null);
                loginDetails.setEmpId(obj[4] != null ? Long.parseLong(obj[4].toString()) : 0L);
                loginDetails.setEmpNo(obj[5] != null ? obj[5].toString() : null);
                loginDetails.setEmpName(obj[6] != null ? obj[6].toString() : null);
                loginDetails.setExtensionNo(obj[7] != null ? obj[7].toString() : null);
                loginDetails.setEmail(obj[8] != null ? obj[8].toString() : null);
                loginDetails.setDesigId(obj[9] != null ? Integer.parseInt(obj[9].toString()) : 0);
                loginDetails.setDesignation(obj[10] != null ? obj[10].toString() : null);
                loginDetails.setDivision_id(obj[11] != null ? Integer.parseInt(obj[11].toString()) : 0);
                loginDetails.setDivision_code(obj[12] != null ? obj[12].toString() : null);
                loginDetails.setDivision_name(obj[13] != null ? obj[13].toString() : null);
                loginDetails.setDivision_head_id(obj[14] != null ? Long.parseLong(obj[14].toString()) : 0L);
            }
        }
        return loginDetails;
    }
    
	@Override
	@Transactional
	public List<LoginProjection> getUserManagerList() {
		
		return loginRepository.getUserManagerList();
	}
	
	@Override
    public List<EmployeeDivisionDTO> getAllDivisions() {
        return masterDao.findAll();
    }

    @Override
    public EmployeeDivision getDivisionById(Long id) {
        return masterDao.findById(id);
    }

    @Override
    @Transactional
    public Long saveDivision(EmployeeDivisionDTO divisionDTO, String userName, String action) {
    	
    	if(divisionDTO == null)
    	{
    		return 0L;
    	}
    	
    	EmployeeDivision divisionEntity = null;
    	
    	if(action.equalsIgnoreCase("add"))
    	{
    		divisionEntity = DtoEntityMapper.map(divisionDTO, new EmployeeDivision());
        	divisionEntity.setIsActive(1);
    		divisionEntity.setCreatedBy(userName);
        	divisionEntity.setCreatedDate(LocalDateTime.now());
    	}
    	else if(action.equalsIgnoreCase("edit"))
    	{
    		divisionEntity = masterDao.getParticularDivisionDetails(divisionDTO.getDivisionId());
    		divisionEntity.setDivisionCode(divisionDTO.getDivisionCode());
    		divisionEntity.setDivisionName(divisionDTO.getDivisionName());
    		divisionEntity.setDivisionHeadId(divisionDTO.getDivisionHeadId());
    		divisionEntity.setModifiedBy(userName);
        	divisionEntity.setModifiedDate(LocalDateTime.now());
    	}
    	
        return masterDao.save(divisionEntity);
    }

    @Override
    public void updateDivision(EmployeeDivision division) {
        division.setModifiedDate(LocalDateTime.now());
        masterDao.update(division);
    }

    @Override
    @Transactional
    public Long deleteDivision(Long divisionId, String userName) {
        return masterDao.delete(divisionId, userName);
    }

    @Override
    public void updateStatus(Long id, Integer isActive) {
        EmployeeDivision division = masterDao.findById(id);
        if (division != null) {
            division.setIsActive(isActive);
            division.setModifiedDate(LocalDateTime.now());
            masterDao.update(division);
        }
    }
    
    @Override
    public List<EmployeeDTO> getEmployeeList() {
        return masterDao.getEmployeeList();
    }

    @Override
    public Employee getEmployee(Long empId) {
        return masterDao.getEmployeeById(empId);
    }

    @Override
    public void createEmployee(Employee employee) {
        employee.setCreatedDate(LocalDateTime.now());
        masterDao.saveEmployee(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        employee.setModifiedDate(LocalDateTime.now());
        masterDao.updateEmployee(employee);
    }

    @Override
    public void deleteEmployee(Long empId) {
        masterDao.deleteEmployee(empId);
    }

    @Override
    public void updateEmployeeStatus(Long empId, Integer isActive) {
        Employee emp = masterDao.getEmployeeById(empId);
        if (emp != null) {
            emp.setIsActive(isActive);
            emp.setModifiedDate(LocalDateTime.now());
            masterDao.updateEmployee(emp);
        }
    }
    
    
}
