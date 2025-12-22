package com.bookings.vcbs.master.dao;

import com.bookings.vcbs.master.dto.LoginDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MasterDaoImpl implements MasterDao {

    @PersistenceContext
    EntityManager manager;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getUserDetails(String userName, String labcode) {
        String sql = """
            SELECT l.login_id, l.user_name, l.role_id, r.role_name, 
                   l.emp_id, e.emp_no, e.emp_name, e.extension_no, 
                   e.email, e.desig_id, ed.designation, e.group_id, 
                   eg.group_code, eg.group_name, eg.group_head_id 
            FROM login l 
            INNER JOIN role_security r ON r.role_id = l.role_id 
            INNER JOIN employee e ON e.emp_id = l.emp_id AND e.is_active = '1' 
            LEFT JOIN employee_desig ed ON ed.desig_id = e.desig_id 
            INNER JOIN employee_group eg ON eg.group_id = e.group_id AND eg.is_active = '1' 
            WHERE l.is_active = '1' AND l.user_name = :userName
            """;

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("userName", userName);

        return query.getResultList();
    }
}
