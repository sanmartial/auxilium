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
@Table(name = "diagnosis")
@Entity
public class Diagnosis {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JsonIgnore
    private String patient_id;
    private String diagnosis_name;
    private String description;
    private LocalDate date;

}
