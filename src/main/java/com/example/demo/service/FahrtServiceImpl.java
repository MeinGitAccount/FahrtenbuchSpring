package com.example.demo.service;

import com.example.demo.model.Fahrt;
import com.example.demo.repo.FahrtRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FahrtServiceImpl implements FahrtService {

    @Autowired
    FahrtRepository repository;

    @Override
    public Fahrt save(Fahrt fahrt) {
        return repository.saveAndFlush(fahrt);
    }

    @Override
    public void delete(Fahrt fahrt) {
        repository.delete(fahrt);
    }

    @Override
    public List<Fahrt> findAll() {
        return repository.findAll();

    }
}
