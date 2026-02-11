package com.bookings.vcbs.master.dao;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDesignationDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDTO;
import com.bookings.vcbs.master.dto.MainModuleDTO;
import com.bookings.vcbs.master.dto.RoleAccessDTO;
import com.bookings.vcbs.master.dto.RoleSecurityDTO;
import com.bookings.vcbs.master.dto.SubModuleDTO;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;
import com.bookings.vcbs.master.modal.Login;
import com.bookings.vcbs.master.modal.RoleSecurityAccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class MasterDaoImpl implements MasterDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getUserDetails(String userName, String labcode) {
        String sql = """
            SELECT l.login_id, l.user_name, l.role_id, r.role_name, 
                   l.emp_id, e.emp_no, e.emp_name, e.extension_no, 
                   e.email, e.desig_id, ed.designation, e.division_id, 
                   eg.division_code, eg.division_name, eg.division_head_id 
            FROM login l 
            INNER JOIN role_security r ON r.role_id = l.role_id 
            INNER JOIN employee e ON e.emp_id = l.emp_id AND e.is_active = '1' 
            LEFT JOIN employee_desig ed ON ed.desig_id = e.desig_id 
            INNER JOIN employee_division eg ON eg.division_id = e.division_id AND eg.is_active = '1' 
            WHERE l.is_active = '1' AND l.user_name = :userName
            """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userName", userName);

        return query.getResultList();
    }
    
    @Override
    public List<EmployeeDivisionDTO> findAll() {
        return entityManager.createQuery(
            """
        		
            SELECT new com.bookings.vcbs.master.dto.EmployeeDivisionDTO(
                d.divisionId, d.divisionCode, d.divisionName, d.divisionHeadId, e.empName, ed.designation, d.isActive
            )
            FROM EmployeeDivision d
            LEFT JOIN Employee e ON e.empId = d.divisionHeadId
            LEFT JOIN EmployeeDesignation ed ON ed.desigId = e.desigId
            WHERE d.isActive = 1
            
            """,
            EmployeeDivisionDTO.class
        ).getResultList();
    }


    @Override
    public EmployeeDivision findById(Long id) {
        return entityManager.find(EmployeeDivision.class, id);
    }

    @Override
    public Long save(EmployeeDivision division) {
        
    	EmployeeDivision savedDivision = entityManager.merge(division);
        
        return savedDivision.getDivisionId();
    }

    @Override
    public Long delete(Long divisionId, String userName) {
        EmployeeDivision division = entityManager.find(EmployeeDivision.class, divisionId);
        if (division != null) {
        	division.setIsActive(0);
        	division.setModifiedBy(userName);
        	division.setModifiedDate(LocalDateTime.now());
            entityManager.merge(division);
            return division.getDivisionId();
        }
        return 0L;
    }
    
    @Override
    public List<EmployeeDTO> getEmployeeList() {
        return entityManager.createQuery(
            """
            SELECT new com.bookings.vcbs.master.dto.EmployeeDTO(
                e.empId, 
                e.labcode, 
                e.empNo, 
                e.empName, 
                e.desigId, 
                ed.designation, 
                e.extensionNo, 
                e.divisionId,
                d.divisionCode, 
                d.divisionName, 
                e.email, 
                e.isActive
            )
            FROM Employee e
            LEFT JOIN EmployeeDesignation ed ON ed.desigId = e.desigId
            INNER JOIN EmployeeDivision d ON d.divisionId = e.divisionId
            WHERE e.isActive = 1
            """,
            EmployeeDTO.class
        ).getResultList();
    }
    
	@Override
	public EmployeeDivision getParticularDivisionDetails(Long divisionId) {
		return entityManager.find(EmployeeDivision.class, divisionId);
	}

	@Override
	public List<EmployeeDesignationDTO> getEmployeeDesignationList() {
		return entityManager.createQuery(
	            """
	            SELECT new com.bookings.vcbs.master.dto.EmployeeDesignationDTO(
	                d.desigId, d.desigCode, d.designation
	            )
	            FROM EmployeeDesignation d
	            
	            """,
	            EmployeeDesignationDTO.class
	        ).getResultList();
	}
	
	@Override
	public List<EmployeeDTO> findAllEmployees() {
	    return entityManager.createQuery(
	        """
	        SELECT new com.example.dto.EmployeeDTO(
	            e.empId, e.empNo, e.empName, e.desigId, d.designationName, 
	            e.extensionNo, e.divisionId, div.divisionName, e.email, e.isActive
	        )
	        FROM Employee e
	        LEFT JOIN EmployeeDesignation d ON e.desigId = d.desigId
	        LEFT JOIN EmployeeDivision div ON e.divisionId = div.divisionId
	        WHERE e.isActive = 1
	        """, EmployeeDTO.class
	    ).getResultList();
	}

	@Override
	public Long deleteEmployee(Long empId, String userName) {
	    Employee emp = entityManager.find(Employee.class, empId);
	    if (emp != null) {
	        emp.setIsActive(0);
	        emp.setModifiedBy(userName);
	        emp.setModifiedDate(LocalDateTime.now());
	        entityManager.merge(emp);
	        return emp.getEmpId();
	    }
	    return 0L;
	}

	@Override
	public Employee findEmployeeById(Long empId) {
		return entityManager.find(Employee.class, empId);
	}

	@Override
	public Long saveEmployee(Employee employeeEntity) {
		
		Employee employee = entityManager.merge(employeeEntity);
        
        return employee.getEmpId();
	}
	
	@Override
	public List<LoginDTO> findAllLogins() {
	    return entityManager.createQuery(
	        """
	    	SELECT new com.bookings.vcbs.master.dto.LoginDTO(l.loginId, l.username, l.roleId, rs.roleName, l.empId, 
	    	(CASE 
                WHEN ed.designation IS NULL OR ed.designation = '' THEN e.empName 
                ELSE CONCAT(e.empName, ', ', ed.designation) 
            END) AS empDetails, l.isActive) 
	        FROM Login l 
	        LEFT JOIN Employee e ON e.empId = l.empId 
	        LEFT JOIN EmployeeDesignation ed ON ed.desigId = e.desigId
	        LEFT JOIN RoleSecurity rs ON rs.roleId = l.roleId
	        WHERE l.isActive = 1
	        
	        
	    	""", 
	        LoginDTO.class).getResultList();
	}

	@Override
	public Login findLoginById(Long id) {
	    return entityManager.find(Login.class, id);
	}

	@Override
	public Long saveLogin(Login login) {
	    Login saved = entityManager.merge(login);
	    return saved.getLoginId();
	}

	@Override
	public Long deleteLogin(Long loginId, String userName) {
	    Login login = entityManager.find(Login.class, loginId);
	    if (login != null) {
	        login.setIsActive(0);
	        login.setModifiedBy(userName);
	        login.setModifiedDate(LocalDateTime.now());
	        entityManager.merge(login);
	        return login.getLoginId();
	    }
	    return 0L;
	}

	public List<RoleSecurityDTO> getRoleSecurityList() {
	    return entityManager.createQuery(
	        "SELECT new com.bookings.vcbs.master.dto.RoleSecurityDTO(r.roleId, r.roleName) " +
	        "FROM RoleSecurity r", 
	        RoleSecurityDTO.class
	    ).getResultList();
	}

	@Override
    public boolean existsByUsername(String username) {
        String hql = "SELECT count(l) FROM Login l WHERE l.username = :username AND l.isActive = 1";
        Long count = (Long) entityManager.createQuery(hql)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }
	
	@Override
	public boolean existsByDivisionCode(String divisionCode) {
		String hql = "SELECT count(d) FROM EmployeeDivision d WHERE d.divisionCode = :divisionCode AND d.isActive = 1";
		Long count = (Long) entityManager.createQuery(hql)
				.setParameter("divisionCode", divisionCode)
				.getSingleResult();
		return count > 0;
	}
	
	@Override
	public boolean existsByEmpNo(String empNo) {
		String hql = "SELECT count(e) FROM Employee e WHERE e.empNo = :empNo AND e.isActive = 1";
		Long count = (Long) entityManager.createQuery(hql)
				.setParameter("empNo", empNo)
				.getSingleResult();
		return count > 0;
	}

	@Override
	public List<MainModuleDTO> getMainModuleList(String roleName) {
	    String hql = """
	        SELECT DISTINCT new com.bookings.vcbs.master.dto.MainModuleDTO(
	            m.moduleId, m.moduleName, m.moduleIcon, m.isActive
	        )
	        FROM Module m
	        INNER JOIN ModuleDetails md ON md.moduleId = m.moduleId
	        INNER JOIN RoleSecurityAccess ra ON ra.moduleDetailsId = md.moduleDetailsId
	        INNER JOIN RoleSecurity rs ON rs.roleId = ra.roleId
	        WHERE m.isActive = 1 
	        AND rs.roleName = :roleName
	    """;

	    return entityManager.createQuery(hql, MainModuleDTO.class)
	            .setParameter("roleName", roleName)
	            .getResultList();
	}

	@Override
	public List<SubModuleDTO> getSubModuleList(String roleName) {
	    String hql = """
	        SELECT DISTINCT new com.bookings.vcbs.master.dto.SubModuleDTO(
	            md.moduleDetailsId, 
	            md.moduleId, 
	            md.moduleDetailsName, 
	            md.moduleDetailsUrl, 
	            md.isActive,
	            md.serialNo
	        )
	        FROM ModuleDetails md
	        INNER JOIN Module m ON m.moduleId = md.moduleId
	        INNER JOIN RoleSecurityAccess ra ON ra.moduleDetailsId = md.moduleDetailsId
	        INNER JOIN RoleSecurity rs ON rs.roleId = ra.roleId
	        WHERE m.isActive = 1 AND md.isActive = 1 AND ra.isActive = 1
	        AND rs.roleName = :roleName
	        ORDER BY md.serialNo ASC
	    """;

	    return entityManager.createQuery(hql, SubModuleDTO.class)
	            .setParameter("roleName", roleName)
	            .getResultList();
	}
	
	@Override
    @Transactional
    public void updateRoleAccess(Long roleId, Long moduleDetailsId, int isActive, String userName) {
        // Check if record exists
        String checkSql = "SELECT role_security_access_id FROM role_security_access WHERE role_id = :roleId AND module_details_id = :detId";
        Query checkQuery = entityManager.createNativeQuery(checkSql);
        checkQuery.setParameter("roleId", roleId);
        checkQuery.setParameter("detId", moduleDetailsId);
        
        List<Object> result = checkQuery.getResultList();
        
        if (!result.isEmpty()) {
            // Update existing record
            Long accessId = ((Number) result.get(0)).longValue();
            String updateSql = """
                UPDATE role_security_access 
                SET is_active = :isActive, 
                    modified_by = :user, 
                    modified_date = NOW() 
                WHERE role_security_access_id = :accessId
            """;
            Query updateQuery = entityManager.createNativeQuery(updateSql);
            updateQuery.setParameter("isActive", isActive);
            updateQuery.setParameter("user", userName);
            updateQuery.setParameter("accessId", accessId);
            updateQuery.executeUpdate();
        } else {
            // Insert new record
            if (isActive == 1) { // Only insert if we are turning it ON
                String insertSql = """
                    INSERT INTO role_security_access 
                    (role_id, module_details_id, is_active, created_by, created_date) 
                    VALUES (:roleId, :detId, 1, :user, NOW())
                """;
                Query insertQuery = entityManager.createNativeQuery(insertSql);
                insertQuery.setParameter("roleId", roleId);
                insertQuery.setParameter("detId", moduleDetailsId);
                insertQuery.setParameter("user", userName);
                insertQuery.executeUpdate();
            }
        }
    }

	@Override
	public List<RoleAccessDTO> getRoleAccessList(Long roleId, Long moduleId) {
	    // We use LEFT JOIN so that sub-modules without records in RoleSecurityAccess still show up
	    String hql = "SELECT new com.bookings.vcbs.master.dto.RoleAccessDTO(" +
	                 "md.moduleDetailsId, " +
	                 "md.moduleDetailsName, " +
	                 "(CASE WHEN ra.isActive = 1 THEN true ELSE false END), " +
	                 "ra.isDrona, " +
	                 "ra.isInternet) " +
	                 "FROM ModuleDetails md " +
	                 "LEFT JOIN RoleSecurityAccess ra ON md.moduleDetailsId = ra.moduleDetailsId " +
	                 "AND ra.roleId = :roleId " +
	                 "WHERE md.moduleId = :moduleId " +
	                 "ORDER BY md.moduleDetailsId ASC";

	    return entityManager.createQuery(hql, RoleAccessDTO.class)
	            .setParameter("roleId", roleId)
	            .setParameter("moduleId", moduleId)
	            .getResultList();
	}

	@Override
	public RoleSecurityAccess findByRoleIdAndModuleDetailsId(Long roleId, Long moduleDetailsId) {
	    try {
	        String hql = "SELECT r FROM RoleSecurityAccess r WHERE r.roleId = :roleId AND r.moduleDetailsId = :moduleDetailsId";
	        return entityManager.createQuery(hql, RoleSecurityAccess.class)
	                .setParameter("roleId", roleId)
	                .setParameter("moduleDetailsId", moduleDetailsId)
	                .getSingleResult();
	    } catch (NoResultException e) {
	        return null; 
	    }
	}

	@Override
	public Long saveRoomTypes(RoleSecurityAccess entity) {
	    if (entity.getRoleSecurityAccessId() == null) {
	        entityManager.persist(entity);
	    } else {
	        entityManager.merge(entity);
	    }
	    return 1L; // Return success
	}
	
}