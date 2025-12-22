package com.bookings.vcbs.master.service;

import com.bookings.vcbs.master.dao.MasterDao;
import com.bookings.vcbs.master.dto.LoginDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterServiceImpl  implements MasterService{

    @Autowired
    private MasterDao masterDao;
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
                loginDetails.setGroupId(obj[11] != null ? Integer.parseInt(obj[11].toString()) : 0);
                loginDetails.setGroupCode(obj[12] != null ? obj[12].toString() : null);
                loginDetails.setGroupName(obj[13] != null ? obj[13].toString() : null);
                loginDetails.setGroupHeadId(obj[14] != null ? Long.parseLong(obj[14].toString()) : 0L);
            }
        }
        return loginDetails;
    }
}
