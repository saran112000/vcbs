package com.bookings.vcbs.master.dao;

import com.bookings.vcbs.master.dto.LoginDetails;

import java.util.List;

public interface MasterDao {
    List<Object[]> getUserDetails(String userName, String labcode);
}
