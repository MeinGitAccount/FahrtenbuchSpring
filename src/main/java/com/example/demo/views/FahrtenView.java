package com.example.demo.views;

import com.example.demo.enums.Status;
import com.example.demo.model.Fahrt;
import com.example.demo.model.Kategorie;
import com.example.demo.service.FahrtService;
import com.example.demo.service.KategorieService;
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

@Component
@View
public class FahrtenView {

    @Autowired
    FahrtService fahrtService;

    @Autowired
    KategorieService kategorieService;

    @Getter
    private Status[] statusOptions = Status.values();

    @Getter
    @Setter
    private Status selectedStatus = null;

    @Getter
    @Setter
    private List<Fahrt> fahrten;

    @Getter
    @Setter
    private List<Kategorie> kategorien;

    @Getter
    @Setter
    private Fahrt newFahrt;

    @PostConstruct
    private void initAll() {
        initKategorien();
        initFahrten();
    }
    public void initKategorien() {
        kategorien = kategorieService.findAll();
    }

    public void initFahrten() {
        fahrten = fahrtService.findAll();
        newFahrt = new Fahrt();
    }

    public void onRowEdit(RowEditEvent<Fahrt> event) {
        fahrtService.save(event.getObject());
        FacesMessage msg = new FacesMessage("Edited", "Fahrt " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initFahrten();
    }

    public void onRowCancel(RowEditEvent<Fahrt> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "Fahrt " + event.getObject().getId());
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

    public void addNewKategorie() {
        Kategorie newKat = new Kategorie();
        kategorieService.save(newKat);
        initKategorien();
    }

    public void deleteKategorie(Kategorie kat) {
        kategorieService.delete(kat);
        initKategorien();
    }
}
