package com.example.demo.model;

import com.example.demo.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@NamedEntityGraph(
        name = "Fahrt.katlist",
        attributeNodes=@NamedAttributeNode("categories")
)
public class Fahrt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Integer id;

    @Column(length = 15)
    @Getter
    @Setter
    private String carPlate;

    @Getter
    @Setter
    private Date date;

    @Getter
    @Setter
    private Date depTime;

    @Getter
    @Setter
    private Date arrTime;

    @Getter
    @Setter
    private Double riddenKM;

    @Getter
    @Setter
    private Duration timeStood = Duration.ZERO;

    @Getter
    @Setter
    @OneToMany(targetEntity = Kategorie.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Kategorie> categories = new ArrayList<>();

    public long getTsHours() {
        return this.timeStood.toHours();
    }

    public void setTsHours(long hours) {
        timeStood = timeStood.minusHours(timeStood.toHours());
        this.timeStood = timeStood.plusHours(hours);
    }

    public long getTsMinutes() {
        return this.timeStood.toMinutesPart();
    }

    public void setTsMinutes(long minutes) {
        timeStood = timeStood.minusMinutes(timeStood.toMinutesPart());
        this.timeStood = timeStood.plusMinutes(minutes);
    }

    public Status getStatus() {
        int compVal = date.toInstant().compareTo(Instant.now().truncatedTo(ChronoUnit.DAYS));
        if(compVal > 0) return Status.ZUKUENFTIG;
        else if (compVal < 0) return Status.ABSOLVIERT;
        return Status.AUF_FAHRT;
    }

}