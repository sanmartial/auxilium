package com.globaroman.auxilium.model.entity.cabinet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "medical_certificate")
@Entity
public class MedicalCertificate {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @JsonIgnore
    private String patient_id;
    @JsonIgnore
    private Long diagnosis_id;
    private String description;
    private LocalDate dateIssue;
    private String pathScan;
}
