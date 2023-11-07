package com.example.demo.views;

import com.example.demo.enums.Status;
import com.example.demo.enums.Wiederholung;
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
    private final Status[] statusOptions = Status.values();

    @Getter
    @Setter
    private Wiederholung repetition;

    @Getter
    @Setter
    private int numberOfRepetitions;

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
    public void initAll() {
        initKategorien();
        initFahrten();
    }
    public void initFahrten() {
        fahrten = fahrtService.findAll();
        newFahrt = new Fahrt();

    }
    public void initKategorien() {
        kategorien = kategorieService.findAll();
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
        if(newFahrt.getNumberOfRepetitions() > 1 && newFahrt.getRepetition() != Wiederholung.NICHT_DEFINIERT) {
            Fahrt fahrt = new Fahrt();
            fahrt = setAdditionalFahrt(fahrt);
            if(fahrt.getRepetition() == Wiederholung.WOECHENTLICH) repetitionWeekly(fahrt);
            else if(fahrt.getRepetition() == Wiederholung.MONATLICH) repetitionMonthly(fahrt);
            else if(fahrt.getRepetition() == Wiederholung.JAEHRLICH) repetitionYearly(fahrt);
        }
        initFahrten();
    }

    public Fahrt setAdditionalFahrt(Fahrt fahrt){
        fahrt.setCarPlate(newFahrt.getCarPlate());
        fahrt.setDate(newFahrt.getDate());
        fahrt.setDepTime(newFahrt.getDepTime());
        fahrt.setArrTime(newFahrt.getArrTime());
        fahrt.setRiddenKM(newFahrt.getRiddenKM());
        fahrt.setTimeStood(newFahrt.getTimeStood());
        //fahrt.setCategories(newFahrt.getCategories());
        fahrt.setRepetition(newFahrt.getRepetition());
        fahrt.setNumberOfRepetitions(newFahrt.getNumberOfRepetitions());
        return fahrt;
    }

    public void repetitionWeekly(Fahrt fahrt){
        newFahrt = fahrt;
        newFahrt.setDate(fahrt.getDate().plusDays(7));
        newFahrt.setNumberOfRepetitions(fahrt.getNumberOfRepetitions() - 1);
        saveNewFahrt();
    }

    public void repetitionMonthly(Fahrt fahrt){                 //Monthly is equivalent to 4 weeks, because otherwise it would not be the same weekday
        newFahrt = fahrt;
        newFahrt.setDate(fahrt.getDate().plusWeeks(4));
        newFahrt.setNumberOfRepetitions(fahrt.getNumberOfRepetitions() - 1);
        saveNewFahrt();
    }

    public void repetitionYearly(Fahrt fahrt){
        newFahrt = fahrt;
        newFahrt.setDate(fahrt.getDate().plusYears(1));
        newFahrt.setNumberOfRepetitions(fahrt.getNumberOfRepetitions() - 1);
        saveNewFahrt();
    }

    public void deleteFahrt(Fahrt fahrt) {
        fahrtService.delete(fahrt);
        initFahrten();
    }
    public void onRowEditKat(RowEditEvent<Kategorie> event) {
        kategorieService.save(event.getObject());
        FacesMessage msg = new FacesMessage("Edited", "Kategorie " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        initKategorien();
    }
    public void onRowCancelKat(RowEditEvent<Kategorie> event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", "Kategorie " + event.getObject().getId());
        FacesContext.getCurrentInstance().addMessage(null, msg);
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
