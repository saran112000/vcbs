package com.bookings.vcbs.master.service;

import com.bookings.vcbs.master.dao.MasterDao;
import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDesignationDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDTO;
import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.RoleSecurityDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.modal.Login;
import com.bookings.vcbs.master.projection.LoginProjection;
import com.bookings.vcbs.master.repository.LoginRepository;
import com.bookings.vcbs.utils.DtoEntityMapper;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MasterServiceImpl  implements MasterService{

    @Autowired
    private MasterDao masterDao;
    
    @Autowired
    private LoginRepository loginRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Value("${defaultPassword}")
    private String defaultPassword;
    
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
		List<EmployeeDivisionDTO> list = masterDao.findAll();
		list.forEach(row -> System.out.println(row));
        return list;
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
    	
    	if(action.equalsIgnoreCase("add"))
    	{
    		boolean check = masterDao.existsByDivisionCode(divisionDTO.getDivisionCode());
    		if(check) {
    			
    		}
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
    	
    	Long status = masterDao.save(divisionEntity);
    	System.out.println("status****"+status);
        return status;
    }

    @Override
    @Transactional
    public Long deleteDivision(Long divisionId, String userName) {
        return masterDao.delete(divisionId, userName);
    }

    @Override
    public List<EmployeeDTO> getEmployeeList() {
    	List<EmployeeDTO> list = masterDao.getEmployeeList();
        return list;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return masterDao.findAllEmployees();
    }

    @Override
    @Transactional
    public Long saveEmployee(EmployeeDTO dto, String userName, String action) {
        Employee employeeEntity;
        
        if(action.equalsIgnoreCase("add")) {
            employeeEntity = new Employee();
            employeeEntity.setIsActive(1);
            employeeEntity.setCreatedBy(userName);
            employeeEntity.setCreatedDate(LocalDateTime.now());
        } else {
            employeeEntity = masterDao.findEmployeeById(dto.getEmpId());
            employeeEntity.setModifiedBy(userName);
            employeeEntity.setModifiedDate(LocalDateTime.now());
        }
        
        employeeEntity.setEmpNo(dto.getEmpNo());
        employeeEntity.setEmpName(dto.getEmpName());
        employeeEntity.setDesigId(dto.getDesigId());
        employeeEntity.setDivisionId(dto.getDivisionId());
        employeeEntity.setExtensionNo(dto.getExtensionNo());
        employeeEntity.setEmail(dto.getEmail());
        employeeEntity.setLabcode(dto.getLabcode());

        return masterDao.saveEmployee(employeeEntity);
    }

    @Override
    @Transactional
    public Long deleteEmployee(Long empId, String userName) {
        return masterDao.deleteEmployee(empId, userName);
    }

	@Override
	public List<EmployeeDesignationDTO> getEmployeeDesignationList() {
		List<EmployeeDesignationDTO> list = masterDao.getEmployeeDesignationList();
        return list;
	}
    
	@Override
	public List<LoginDTO> getAllLogins() {
	    return masterDao.findAllLogins();
	}

	@Override
	@Transactional
	public Long saveLogin(LoginDTO loginDTO, String userName, String action) {
	    Login loginEntity;
	    
	    if(action.equalsIgnoreCase("add")) {
	        loginEntity = new Login();
	        loginEntity.setPassword(defaultPassword!=null ? passwordEncoder.encode(defaultPassword) : "123"); 
	        loginEntity.setUsername(loginDTO.getUsername());
	        loginEntity.setCreatedBy(userName);
	        loginEntity.setCreatedDate(LocalDateTime.now());
	    } else {
	        loginEntity = masterDao.findLoginById(loginDTO.getLoginId());
	        loginEntity.setModifiedBy(userName);
	        loginEntity.setModifiedDate(LocalDateTime.now());
	    }
	    
	    loginEntity.setEmpId(loginDTO.getEmpId());
	    loginEntity.setRoleId(loginDTO.getRoleId());
	    loginEntity.setIsActive(1);
	    
	    return masterDao.saveLogin(loginEntity);
	}

	@Override
	@Transactional
	public Long deleteLogin(Long loginId, String userName) {
	    return masterDao.deleteLogin(loginId, userName);
	}

	@Override
	public List<RoleSecurityDTO> getRoleSecurityList() {
		return masterDao.getRoleSecurityList();
	}

	@Override
	public boolean existsByUsername(String username) {
		// TODO Auto-generated method stub
		return masterDao.existsByUsername(username);
	}

	@Override
	public boolean existsByDivisionCode(String divisionCode) {
		return masterDao.existsByDivisionCode(divisionCode);
	}
	
	@Override
	public boolean existsByEmpNo(String empNo) {
		return masterDao.existsByEmpNo(empNo);
	}

	@Override
	public List<MainModuleDTO> getMainModuleList() {
		return masterDao.getMainModuleList();
	}

	@Override
	public List<SubModuleDTO> getSubModuleList() {
		return masterDao.getSubModuleList();
	}
    
}
