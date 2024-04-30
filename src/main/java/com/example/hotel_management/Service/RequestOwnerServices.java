package com.example.hotel_management.Service;

import com.example.hotel_management.Model.RequestOwner;

import java.util.List;

public interface RequestOwnerServices {
    public List<RequestOwner> findAll();

    public List<RequestOwner> findByRequestId(int requestId);

    public List<RequestOwner> findByUsername(String username);

    public RequestOwner save(RequestOwner requestOwner);

    public void delete(RequestOwner requestOwner);
}
