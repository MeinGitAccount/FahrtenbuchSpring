package com.example.demo.service;

import com.example.demo.model.Fahrt;
import jakarta.transaction.Transactional;

import java.util.List;

public interface FahrtService {

    @Transactional
    Fahrt save(Fahrt fahrt);

    public void delete(Fahrt fahrt);

    List<Fahrt> findAll();
}
