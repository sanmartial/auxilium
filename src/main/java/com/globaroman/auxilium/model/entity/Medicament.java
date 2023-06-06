package com.globaroman.auxilium.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "medicament")
@Entity
public class Medicament {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JsonIgnore
    private String patient_id;
    @JsonIgnore
    private Long diagnosis_id;
    private String drug_name;
    private String dosage;
    private LocalDate start_date;



}
