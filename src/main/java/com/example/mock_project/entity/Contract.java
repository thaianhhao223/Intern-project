package com.example.mock_project.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "contract")
public class Contract implements Serializable {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(name = "contract_code")
    private String id;

    // Customer
    @ManyToOne
    private Customer customer;
    @Column(name = "valid_from")
    private Date validFrom;
    @Column(name = "valid_to")
    private Date validTo;
    @Column(name = "maximum_amout")
    private double maximumAmount;
    @Column(name = "remaining_amout")
    private double remainingAmount;
    @Column(name = "acceptable_hospitals")
    private String acceptableHospitals;
    @Column(name = "acceptable_accidents")
    private String acceptableAccidents;
}
