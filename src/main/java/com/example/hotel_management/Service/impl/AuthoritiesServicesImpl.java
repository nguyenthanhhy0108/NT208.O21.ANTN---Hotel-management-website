package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.Authorities;
import com.example.hotel_management.Repository.AuthoritiesRepository;
import com.example.hotel_management.Service.AuthoritiesServices;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
//Implement defined interface
@Service
public class AuthoritiesServicesImpl implements AuthoritiesServices {
    //Define some attribute
    private final AuthoritiesRepository AuthoritiesRepository;
    private final EntityManager entityManager;

    /**
     * Dependency Injection
     * @param authoritiesRepository: AuthoritiesRepository object
     */
    @Autowired
    public AuthoritiesServicesImpl(AuthoritiesRepository authoritiesRepository,
                                   EntityManager entityManager) {
        AuthoritiesRepository = authoritiesRepository;
        this.entityManager = entityManager;
    }

    /**
     * Implement AuthoritiesServices Interface
     * @param Username: The username
     * @return
     * An authorities list
     */
    @Override
    public List<Authorities> findByUsername(String Username) {
        /*
        Call Repository layer and return  an authorities list
         */
        return AuthoritiesRepository.findByUsername(Username);
    }

    /**
     * Implement AuthoritiesServices Interface
     * @param authorities: The authorities
     * @return
     * An authorities which is saved
     */
    @Override
    public Authorities save(Authorities authorities) {
        /*
        Call Repository layer and return  an authorities object
         */
        return AuthoritiesRepository.save(authorities);
    }

    @Transactional
    @Override
    public void add(Authorities authorities) {
        this.entityManager.persist(authorities);
    }
}
