package com.project.GavrielsProject.beans;


import lombok.*;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Scope("prototype")
@Entity
@Table(name = "companies")
public class Company {
    /**
     * id       the company's id (defined as unique parameter by the SERVER and will be reset automatically in the DB (AI) )
     * name     the company's name
     * email    the company's email (used to login in the company service, will be defined as unique by the SERVER))
     * password the company's password  (used to login in the company service)
     * coupons  list of coupons belonging to the company. One to many relationship - many coupons belong to one company.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(unique = true, updatable = false, length = 40)
    private String name;
    @Column(unique = true, length = 40)
    private String email;
    @Column(length = 40)
    private String password;
    @Singular
    @OneToMany(cascade =
            CascadeType.ALL,
            fetch = FetchType.EAGER,
            mappedBy = "companyID"
    )
    private List<Coupon> coupons;


}
