package com.example.demo.views;

import com.example.demo.model.Fahrt;
import com.example.demo.repo.FahrtRepository;
import com.example.demo.service.FahrtService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.annotation.View;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
@View
public class FahrtenView {

    @Autowired
    FahrtService fahrtService;

    @Getter
    @Setter
    private List<Fahrt> fahrten;

    @Getter
    @Setter
    private Fahrt newFahrt;

    @PostConstruct
    public void initFahrten() {
        fahrten = fahrtService.findAll();
        newFahrt = new Fahrt();
    }

    public void onRowEdit(RowEditEvent<Fahrt> event) {
        fahrtService.save(event.getObject());
        FacesMessage msg = new FacesMessage("Product Edited", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initFahrten();
    }

    public void onRowCancel(RowEditEvent<Fahrt> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", String.valueOf(event.getObject().getId()));
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveNewFahrt() {
        fahrtService.save(newFahrt);
        initFahrten();
    }

    public void deleteFahrt(Fahrt fahrt) {
        fahrtService.delete(fahrt);
        initFahrten();
    }
}
