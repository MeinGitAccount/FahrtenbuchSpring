package com.example.demo.repo;

import com.example.demo.model.Fahrt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FahrtRepository extends JpaRepository<Fahrt, Integer> {
}