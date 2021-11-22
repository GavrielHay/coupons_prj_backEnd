package com.project.GavrielsProject.beans;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Scope("prototype")
@Entity
@Table(name = "customers")
public class Customer {

    /**
     * id        the customer's id (defined as unique parameter by the SERVER and will be reset automatically in the DB (AI) )
     * firstName the customer's first name
     * lastName  the customer's last name
     * email     the customer's email (used to login in the customer service, will be defined as unique by the SERVER)
     * password  the customer's password (used to login in the customer service)
     * coupons   list of coupons purchased by the customer. many to many relationship - customer can buy many coupons, and a coupon can be purchased by many customers.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @Column(length = 40)
    private String firstName;
    @Column(length = 40)
    private String lastName;
    @Column(unique = true, length = 40)
    private String email;
    @Column(length = 40)
    private String password;
    @Singular
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_coupon",
            joinColumns = @JoinColumn(name = "customer_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Coupon> coupons;

}
