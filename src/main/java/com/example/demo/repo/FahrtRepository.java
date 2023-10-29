package com.example.demo.repo;

import com.example.demo.model.Fahrt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FahrtRepository extends JpaRepository<Fahrt, Integer> {

    @Override
    @NonNull
    List<Fahrt> findAll();
}