package com.example.demo.repo;

import com.example.demo.model.Fahrt;
import com.example.demo.model.Kategorie;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;

public interface FahrtRepository extends JpaRepository<Fahrt, Integer> {

    @EntityGraph(value = "Fahrt.katlist")
    @NonNull
    public List<Fahrt> findAll();
}