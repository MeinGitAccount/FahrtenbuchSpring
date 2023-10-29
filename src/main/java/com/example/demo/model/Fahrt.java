package com.example.demo.model;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@NamedEntityGraph(
        name = "categories",
        attributeNodes=@NamedAttributeNode("categories")
)
public class Fahrt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Integer id;

    @Column(length = 15)
    @Getter
    @Setter
    private String carPlate;

    @Getter
    @Setter
    private LocalDate date;

    @Getter
    @Setter
    private LocalTime depTime;

    @Getter
    @Setter
    private LocalTime arrTime;

    @Getter
    @Setter
    private Integer riddenKM;

    @Getter
    @Setter
    private LocalTime timeStood = LocalTime.MIN;

    @Getter
    @Setter
    @ManyToMany(targetEntity = Kategorie.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "Fahrt_Kategorie",
            joinColumns = @JoinColumn(name = "fahrt_id"),
            inverseJoinColumns = @JoinColumn(name = "kategorie_id")
    )
    private Set<Kategorie> categories = new HashSet<>();

    public String getCategoriesAsString() {
        return categories.stream().map(Object::toString).collect(Collectors.joining(","));
    }
    public Status getStatus() {
        if(this.date == null || this.depTime == null || this.arrTime == null) return Status.NICHT_DEFINIERT;
        int dateComp = date.compareTo(LocalDate.now());
        if(dateComp > 0 || dateComp == 0 && depTime.compareTo(LocalTime.now()) > 0) return Status.ZUKUENFTIG;                           //zukuenftig falls Datum hoeher als heutiges Datum, oder heutiges Datum und Abfahrtszeit liegt in der Zukunft
        if(dateComp == 0 && depTime.compareTo(LocalTime.now()) <= 0 && arrTime.compareTo(LocalTime.now()) > 0) return Status.AUF_FAHRT; //Auf Fahrt falls heutiges Datum, Abfahrtszeit kleiner gleich jetziger Zeit und Ankunftszeit liegt in der Zukunft
        else if (dateComp < 0 || dateComp == 0 && arrTime.compareTo(LocalTime.now()) < 0) return Status.ABSOLVIERT;                     //absolviert falls Datum in der Vergangenheit, oder heutiges Datum und Ankunftszeit liegt in der Vergangenheit

        return Status.NICHT_DEFINIERT;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return Objects.equals(id, ((Fahrt) o).getId());
    }

    @Override
    public int hashCode() {
        return id;
    }
}