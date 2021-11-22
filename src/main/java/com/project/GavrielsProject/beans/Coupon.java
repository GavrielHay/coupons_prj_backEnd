package com.project.GavrielsProject.beans;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.GavrielsProject.enums.Category;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.context.annotation.Scope;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Scope("prototype")
@Entity
@Table(
        name = "coupons",
        uniqueConstraints = {
                @UniqueConstraint(name = "CompanyIDAndTitle", columnNames = {"companyID", "title"})
        })


public class Coupon {
    /**
     * id          the coupon's id (defined as unique parameter by the SERVER and will be reset automatically in the DB (AI))
     * companyID   the id of the company who created the coupon. each coupon belong to a specific company defined by "join column"
     * category    the coupon's category (enum)
     * title       the coupon's title - Each company must set different titles for its coupons. It is possible that two companies will have the same title (defined by the SERVER using @UniqueConstraint annotation)
     * description the coupon's description
     * startDate   the time when the coupon has got created
     * endDate     the time when the coupon will be expired and automatically deleted by a dedicated thread.
     * amount      the amount of coupons left for purchase (when empty - cant buy)
     * price       the cost price of the coupon for purchase
     * image       a link for the image of the coupon
     * customers   list of customers that purchased a single coupon.  many to many relationship - customer can buy many coupons, and a coupon can be purchased by many customers.
     *
     * @ToString.Exclude defined on customers list to prevent recursion
     * @JsonIgnore defined on customers list to prevent recursion
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private int id;
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    private int companyID;
    @Enumerated(EnumType.ORDINAL)
    private Category category;
    @Column(length = 40)
    private String title;
    @Column(length = 100)
    private String description;
    private Date startDate;
    private Date endDate;
    private int amount;
    private double price;
    private String image;
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "coupons", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Customer> customers;
}
