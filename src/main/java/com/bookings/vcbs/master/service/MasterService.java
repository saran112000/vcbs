package com.bookings.vcbs.master.service;

import com.bookings.vcbs.master.dto.LoginDetails;

public interface MasterService {
    LoginDetails getUserDetails(String userName, String labcode);
}
