package com.project.GavrielsProject.clr;

import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.LoginException;
import com.project.GavrielsProject.exceptions.PurchaseException;
import com.project.GavrielsProject.repositories.CouponRepo;
import com.project.GavrielsProject.services.CustomerService;
import com.project.GavrielsProject.services.LoginManager;
import com.project.GavrielsProject.utils.ArtUtils.Banners;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.project.GavrielsProject.utils.ArtUtils.FontDesign.ConsoleColors.*;

import java.sql.Date;

//@Component
@RequiredArgsConstructor
@Order(4)
public class CustomerTest implements CommandLineRunner {
    private final LoginManager loginManager;
    private final CustomerService customerService;
    private final CouponRepo couponRepo;


    @Override
    public void run(String... args) throws Exception {

        System.out.println(GREEN_BRIGHT + "\n\n\n" + Banners.CUSTOMER + RESET);

        try {

            System.out.println(GREEN_UNDERLINED + "try to login as customer2 with wrong param" + RESET);
            try {
                loginManager.login("customer2@gmail.com", "WRONG_PASSWORD", ClientType.Customer);
                customerActions();
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(GREEN_UNDERLINED + " login as customer2" + RESET);
            try {
                loginManager.login("customer2@gmail.com", "customer21234", ClientType.Customer);
                customerActions();
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }


            System.out.println(GREEN_BRIGHT + "\nEverything works great. Mum is HAPPY again!!!\n" + GREEN_BRIGHT + Banners.Mum + RESET);
            System.out.println(".\n.\n" + GREEN_BRIGHT + "still connected to server ... \n" + RESET);

        } catch (Exception e) {
            System.out.println("Oops! A GENERAL ERROR OCCURRED. Mum is NOT HAPPY this time :(");
        }
    }

    public void customerActions() {

        System.out.println(GREEN_UNDERLINED + "purchase coupons num 2,3,4" + RESET);
        try {
            customerService.purchaseCoupon(2,2);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }
        try {
            customerService.purchaseCoupon(2,3);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }
        try {
            customerService.purchaseCoupon(2,4);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(customerService.getCustomerDetails(2));


        //coupon which already got purchased:
        System.out.println(GREEN_UNDERLINED + "try to purchase coupon num 4 again" + RESET);
        try {
            customerService.purchaseCoupon(2,5);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }

        //an expired coupon:
        System.out.println(GREEN_UNDERLINED + "try to purchase an expired coupon (coupon 6)" + RESET);
        try {
            customerService.purchaseCoupon(2,6);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }

        //coupon with not amount left:
        System.out.println(GREEN_UNDERLINED + "try to purchase an empty coupon (coupon 5)" + RESET);
        try {
            customerService.purchaseCoupon(2,5);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }

        //coupon not exist in system:
        System.out.println(GREEN_UNDERLINED + "try to purchase a coupon which is not exist in the system (coupon 5)" + RESET);
        try {
            customerService.purchaseCoupon(2,10);
        } catch (PurchaseException e) {
            System.out.println(e.getMessage());
        }

        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(customerService.getCustomerDetails(2));

        System.out.println(GREEN_UNDERLINED + "get all coupons purchased by the customer" + RESET);
        System.out.println(customerService.getCustomerCoupons(2));

        System.out.println(GREEN_UNDERLINED + "get all coupons purchased by the customer of specific category (Electricity)" + RESET);
        System.out.println(customerService.getCustomerCoupons(2,Category.Electricity));

        System.out.println(GREEN_UNDERLINED + "get all coupons purchased by the customer up to specific price (150.50)" + RESET);
        System.out.println(customerService.getCustomerCoupons(2,150.50));

        System.out.println(GREEN_UNDERLINED + "get all the details of the customer" + RESET);
        System.out.println(customerService.getCustomerDetails(2));


    }


}
