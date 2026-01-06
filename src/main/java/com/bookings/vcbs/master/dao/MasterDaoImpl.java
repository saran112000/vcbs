package com.bookings.vcbs.master.dao;

import com.bookings.vcbs.master.dto.EmployeeDTO;
import com.bookings.vcbs.master.dto.EmployeeDivisionDTO;
import com.bookings.vcbs.master.dto.LoginDetails;
import com.bookings.vcbs.master.modal.Employee;
import com.bookings.vcbs.master.modal.EmployeeDivision;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
        
    	entityManager.merge(division);
        
        return division.getDivisionId();
    }

    @Override
    public void update(EmployeeDivision division) {
        entityManager.merge(division);
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
                e.empId, e.labcode, e.empNo, e.empName, e.desigId, ed.designation, e.extensionNo, e.divisionId,
                d.divisionCode, d.divisionName, e.email
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
    public Employee getEmployeeById(Long empId) {
        return entityManager.find(Employee.class, empId);
    }

    @Override
    public void saveEmployee(Employee employee) {
        entityManager.persist(employee);
    }

    @Override
    public void updateEmployee(Employee employee) {
        entityManager.merge(employee);
    }

    @Override
    public void deleteEmployee(Long empId) {
        Employee emp = getEmployeeById(empId);
        if (emp != null) {
            entityManager.remove(emp);
        }
    }

	@Override
	public EmployeeDivision getParticularDivisionDetails(Long divisionId) {
		return entityManager.find(EmployeeDivision.class, divisionId);
	}
}
