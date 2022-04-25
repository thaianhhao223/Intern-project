package com.example.mock_project.genarator;

import com.example.mock_project.service.RedissonService;
import io.reactivex.Completable;
import org.hibernate.HibernateException;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Generated;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;


public class GenaratorForClaimRequestId implements IdentifierGenerator {

    @Autowired
    private RedissonService redissonService;
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {

        String id;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        id = "YCBT_"+year;
        Long countClaimrequest = redissonService.getCount(id);
        id += "_"+String.format("%06d", countClaimrequest);
        return id;
    }
}
