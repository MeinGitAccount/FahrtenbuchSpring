package com.example.demo.service;

import com.example.demo.model.Kategorie;
import org.springframework.stereotype.Service;

import java.util.List;


public interface KategorieService {
    void delete(Kategorie k);
    void save(Kategorie k);

    List<Kategorie> findAll();
}
