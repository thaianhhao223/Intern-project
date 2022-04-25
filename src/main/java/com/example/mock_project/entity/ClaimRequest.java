package com.example.mock_project.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "mockproject",type = "claimrequest")
public class ClaimRequest implements Serializable {
    @Id
//    @GeneratedValue(generator="genarator_claimrequest_id")
//    @GenericGenerator(name="genarator_claimrequest_id", strategy = "com.example.mock_project.ganarator.GenaratorForClaimRequestId")
    @Column(name = "claim_request_code")
    private String id;

    @ManyToOne
    private Customer customer;

    @OneToOne
    private Contract contract;

    @ElementCollection(fetch = FetchType.EAGER, targetClass = String.class)
    private List<String> listUrlImage;

    @Column(columnDefinition = "boolean default false")
    private boolean hasAnalyzed;

    @Column(columnDefinition = "boolean default false")
    private boolean validReceipt;

    @Column(columnDefinition = "boolean default false")
    private boolean hasApprove;

    @Column(columnDefinition = "boolean default false")
    private boolean approve;

    @Column(columnDefinition = "boolean default false")
    private boolean hasPayment;

    @Column(columnDefinition = "boolean default false")
    private boolean payment;

    private String hospitalId;

    private String accidentId;

    private String name;

    @Column(columnDefinition = "float default 0")
    private double receiptAmount;

    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateOfReceipt;
}
