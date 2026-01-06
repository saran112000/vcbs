package com.bookings.vcbs.utils;

import org.springframework.beans.BeanUtils;

public class DtoEntityMapper {

    private DtoEntityMapper() {
        // Utility class
    }

    public static <D, E> E map(D dto, E entity) {
        if (dto == null || entity == null) {
            return entity;
        }

        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
