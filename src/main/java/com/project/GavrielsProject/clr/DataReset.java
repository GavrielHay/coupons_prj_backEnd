package com.project.GavrielsProject.clr;


import com.project.GavrielsProject.beans.Company;
import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.beans.Customer;
import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.AddException;
import com.project.GavrielsProject.exceptions.LoginException;
import com.project.GavrielsProject.exceptions.PurchaseException;
import com.project.GavrielsProject.services.AdminService;
import com.project.GavrielsProject.services.CompanyService;
import com.project.GavrielsProject.services.CustomerService;
import com.project.GavrielsProject.services.LoginManager;
import com.project.GavrielsProject.utils.ArtUtils.Banners;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.sql.Date;

import static com.project.GavrielsProject.utils.ArtUtils.FontDesign.ConsoleColors.*;

@Component
@RequiredArgsConstructor
@Order(1)
public class DataReset implements CommandLineRunner {
    private final LoginManager loginManager;
    private final AdminService adminService;
    private final CompanyService companyService;
    private final CustomerService customerService;


    @Override
    public void run(String... args) throws Exception {
        System.out.println(Banners.localhost);
        System.out.println(".\n.\n.\n" + CYAN_BRIGHT + " CONSTRUCTS INITIAL DATA.............. \n" + RESET);

        try {

            System.out.println(GREEN_UNDERLINED + "login as admin" + RESET);
            try {
                loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

            Company company1 = Company.builder()
                    .id(1)
                    .name("company1")
                    .email("company1@gmail.com")
                    .password("company11234")
                    .build();

            Company company2 = Company.builder()
                    .id(2)
                    .name("company2")
                    .email("company2@gmail.com")
                    .password("company21234")
                    .build();

            Company company3 = Company.builder()
                    .id(3)
                    .name("company3")
                    .email("company3@gmail.com")
                    .password("company31234")
                    .build();

            System.out.println(GREEN_UNDERLINED + "adding 3 companies" + RESET);
            try {
                adminService.addCompany(company1);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                adminService.addCompany(company2);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                adminService.addCompany(company3);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }


            Customer customer1 = Customer.builder()
                    .id(1)
                    .firstName("customer1")
                    .lastName("1family")
                    .email("customer1@gmail.com")
                    .password("customer11234")
                    .build();

            Customer customer2 = Customer.builder()
                    .id(2)
                    .firstName("customer2")
                    .lastName("2family")
                    .email("customer2@gmail.com")
                    .password("customer21234")
                    .build();

            Customer customer3 = Customer.builder()
                    .id(3)
                    .firstName("customer3")
                    .lastName("3family")
                    .email("customer3@gmail.com")
                    .password("customer31234")
                    .build();



            System.out.println(GREEN_UNDERLINED + "adding 3 customers" + RESET);
            try {
                adminService.addCustomer(customer1);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                adminService.addCustomer(customer2);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                adminService.addCustomer(customer3);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }


            System.out.println(GREEN_UNDERLINED + " login as company2" + RESET);
            try {
                loginManager.login("company2@gmail.com", "company21234", ClientType.Company);
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }


            Coupon coupon1 = Coupon.builder()
                    .id(1)
                    .companyID(2)
                    .category(Category.Food)
                    .title("Humus Eliyahu")
                    .description("Humus Kosher yammy yammy")
                    .startDate(Date.valueOf("2022-01-01"))
                    .endDate(Date.valueOf("2023-01-01"))
                    .amount(50)
                    .price(15.00)
                    .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/hummus1.jpg")
                    .build();

            Coupon coupon2 = Coupon.builder()
                    .id(2)
                    .companyID(2)
                    .category(Category.Electricity)
                    .title("Mahsaney Electronica")
                    .description("Irons and Toster meshulashim")
                    .startDate(Date.valueOf("2022-01-01"))
                    .endDate(Date.valueOf("2023-01-01"))
                    .amount(100)
                    .price(200.00)
                    .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/electric.jpg")
                    .build();

            Coupon coupon3 = Coupon.builder()
                    .id(3)
                    .companyID(2)
                    .category(Category.Electricity)
                    .title("KSP")
                    .description("Computers and staff")
                    .startDate(Date.valueOf("2015-01-01"))
                    .endDate(Date.valueOf("2023-01-01"))
                    .amount(10)
                    .price(150.00)
                    .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/ksp1.jpg")
                    .build();

            Coupon coupon4 = Coupon.builder()
                    .id(4)
                    .companyID(2)
                    .category(Category.Fashion)
                    .title("avoda ZARA")
                    .description("Made in china")
                    .startDate(Date.valueOf("2018-01-01"))
                    .endDate(Date.valueOf("2023-01-01"))
                    .amount(30)
                    .price(100.00)
                    .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/zara1.jpg")
                    .build();

            Coupon coupon5 = Coupon.builder()
                    .id(5)
                    .companyID(2)
                    .category(Category.Restaurant)
                    .title("Shipoodim")
                    .description("Egel Keves Pargyot")
                    .startDate(Date.valueOf("2022-01-01"))
                    .endDate(Date.valueOf("2023-01-01"))
                    .amount(0)
                    .price(100.00)
                    .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/shipoodim1.jpg")
                    .build();

            Coupon coupon6 = Coupon.builder()
                    .id(6)
                    .companyID(2)
                    .category(Category.Vacation)
                    .title("Pessah BeMitzraim")
                    .description("Vacation in Egypt for all Passover days including hotel")
                    .startDate(Date.valueOf("2019-01-01"))
                    .endDate(Date.valueOf("2023-01-01"))
                    .amount(100)
                    .price(100.00)
                    .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/egypt1.jpg")
                    .build();

            System.out.println(GREEN_UNDERLINED + "company 2 add 6 coupons" + RESET);
            try {
                companyService.addCoupon(2,coupon1);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                companyService.addCoupon(2,coupon2);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                companyService.addCoupon(2,coupon3);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                companyService.addCoupon(2,coupon4);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                companyService.addCoupon(2,coupon5);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }
            try {
                companyService.addCoupon(2,coupon6);
            } catch (AddException e) {
                System.out.println(e.getMessage());
            }


            System.out.println(GREEN_UNDERLINED + " login as customer2" + RESET);
            try {
                loginManager.login("customer2@gmail.com", "customer21234", ClientType.Customer);
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

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

            System.out.println(".\n.\n" + GREEN_BRIGHT + "Server and DB are ready to go! ... \n" + RESET);


        } catch (Exception e) {
            System.out.println("AH, Houston, we've had a problem!");
        }
    }
}