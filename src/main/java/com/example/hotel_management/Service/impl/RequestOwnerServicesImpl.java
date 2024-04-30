package com.example.hotel_management.Service.impl;

import com.example.hotel_management.Model.RequestOwner;
import com.example.hotel_management.Repository.RequestOwnerRepository;
import com.example.hotel_management.Service.RequestOwnerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestOwnerServicesImpl implements RequestOwnerServices {
    private final RequestOwnerRepository requestOwnerRepository;

    @Autowired
    public RequestOwnerServicesImpl(RequestOwnerRepository requestOwnerRepository) {
        this.requestOwnerRepository = requestOwnerRepository;
    }

    public List<RequestOwner> findAll() {
        return requestOwnerRepository.findAll();
    }

    public List<RequestOwner> findByRequestId(int requestId) {
        return requestOwnerRepository.findByRequestId(requestId);
    }

    public List<RequestOwner> findByUsername(String username) {
        return requestOwnerRepository.findByUsername(username);
    }

    public RequestOwner save(RequestOwner requestOwner) {
        return requestOwnerRepository.save(requestOwner);
    }

    public void delete(RequestOwner requestOwner) {
        requestOwnerRepository.delete(requestOwner);
    }
}
