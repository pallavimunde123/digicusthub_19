package com.ibm.digicusthub.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="city_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String cityName;

    @ManyToOne
    @JoinColumn(name="state_id",referencedColumnName = "id")
    private StateEntity state;

}
