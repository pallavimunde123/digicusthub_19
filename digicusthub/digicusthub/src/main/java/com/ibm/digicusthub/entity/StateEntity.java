package com.ibm.digicusthub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="state_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String stateName;

    @ManyToOne
    @JoinColumn(name="country_id",referencedColumnName = "id")
    private CountryEntity country;

    @OneToMany(mappedBy = "state")
    private List<CityEntity> cities = new ArrayList<>();

}
