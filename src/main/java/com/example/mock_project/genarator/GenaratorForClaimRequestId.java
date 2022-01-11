package com.example.mock_project.genarator;

import com.example.mock_project.service.RedissonService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class GenaratorForClaimRequestId implements IdentifierGenerator {

    @Autowired
    private RedissonService redissonService;
    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {

        return null;
    }
}
