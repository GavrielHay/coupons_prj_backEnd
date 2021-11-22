package com.project.GavrielsProject.services;

import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.beans.Customer;
import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.exceptions.PurchaseException;
import com.project.GavrielsProject.repositories.CouponRepo;
import com.project.GavrielsProject.repositories.CustomerRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class CustomerService {
    /**
     * Access to the Repositories Queries consumed by the customer client
     */
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    CouponRepo couponRepo;
    /**
     * id of the logged in customer
     */
    private int customerID;

    /**
     * Data authentication for Customer login try
     *
     * @param email    Email to verify
     * @param password password to verify
     * @return boolean: true = params match , false = params didn't match
     */
    public boolean customerLogin(String email, String password) {
        if (customerRepo.existsByEmailAndPassword(email, password)) {
            this.customerID = customerRepo.findByEmail(email).getId();
            return true;
        }
        return false;
    }


    /**
     * adding a purchase of a coupon for the logged in customer
     *
     * @param customerID - The connected customer who performs the operation
     * @param couponID  - id of the coupon for purchase
     * @throws PurchaseException will be thrown when the customer's ID is already associated with the ID of the coupon, when coupon is not exist in DB, when amount less then 1 or when endDate has expired.
     */
    public void purchaseCoupon(int customerID, int couponID) throws PurchaseException {
        Customer customerBuyer = customerRepo.findById(customerID);
        if (!customerBuyer.getCoupons().stream().map(Coupon::getId).collect(Collectors.toList()).contains(couponID)
                && couponRepo.existsById(couponID)
                && couponRepo.findById(couponID).getAmount() > 0
                && couponRepo.existsByIdAndEndDateAfter(couponID, Date.valueOf(LocalDate.now()))
        ) {
            Coupon couponToBuy = couponRepo.findById(couponID);
            customerBuyer.getCoupons().add(couponToBuy);
            customerRepo.saveAndFlush(customerBuyer);
            //can verify if purchase is done, so only if "true" sum the Amount. but overload on the server ->
            //if (customerRepo.findById(customerID).getCoupons().stream().map(Coupon::getId).collect(Collectors.toList()).contains(coupon.getId())) {
            couponRepo.subCouponAmountBy1(couponID);
            System.out.println("Congratulations! the coupon \"" + couponToBuy.getTitle() + "\" successfully purchased.");
            //}
        } else {
            throw new PurchaseException("Sorry, the coupon cannot be purchased. Please check if you have already purchased it. Otherwise it may be expired, emptied, or no longer exist.");
        }
    }


    /**
     * getting a list of all coupons belongs to the logged in customer from DB
     *
     * @param customerID - The connected customer who performs the operation
     * @return list of the customer's coupons
     */
    public List<Coupon> getCustomerCoupons(int customerID) {
        return couponRepo.findByCustomers(customerRepo.findById(customerID));
    }

    /**
     * getting a list of all coupons belongs to the logged in customer from DB, Filtered by Category
     *
     * @param customerID - The connected customer who performs the operation
     * @param category Category for filter
     * @return list of the customer's coupons by specific Category
     */
    public List<Coupon> getCustomerCoupons(int customerID, Category category) {
        return couponRepo.findByCustomersAndCategory(customerRepo.findById(customerID), category);
    }

    /**
     * getting a list of all coupons belongs to the logged in customer from DB, only where coupons price up to a chosen number
     *
     * @param customerID - The connected customer who performs the operation
     * @param maxPrice a chosen number for filtering the higher prices value
     * @return list of the customer's coupons up to a chosen number
     */
    public List<Coupon> getCustomerCoupons(int customerID, double maxPrice) {
        return couponRepo.findByCustomersAndPriceLessThanEqual(customerRepo.findById(customerID), maxPrice);
    }

    /**
     * getting the logged in customer params from DB (by the logged in customer ID)
     *
     * @param customerID - The connected customer who performs the operation
     * @return single customer which logged in
     */
    public Customer getCustomerDetails(int customerID) {
        return customerRepo.findById(customerID);
    }


}
