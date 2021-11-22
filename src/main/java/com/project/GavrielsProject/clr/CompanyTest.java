package com.project.GavrielsProject.clr;

import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.AddException;
import com.project.GavrielsProject.exceptions.DeleteException;
import com.project.GavrielsProject.exceptions.LoginException;
import com.project.GavrielsProject.exceptions.UpdateException;
import com.project.GavrielsProject.services.CompanyService;
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
@Order(3)
public class CompanyTest implements CommandLineRunner {
    private final LoginManager loginManager;
    private final CompanyService companyService;


    @Override
    public void run(String... args) throws Exception {

        System.out.println(GREEN_BRIGHT + "\n\n\n" + Banners.COMPANY + RESET);

        try {

            System.out.println(GREEN_UNDERLINED + "try to login as company2 with wrong param" + RESET);
            try {
                loginManager.login("company2@gmail.com", "WRONG_PASSWORD", ClientType.Company);
                companyActions();
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(GREEN_UNDERLINED + " login as company2" + RESET);
            try {
                loginManager.login("company2@gmail.com", "company21234", ClientType.Company);
                companyActions();
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(GREEN_UNDERLINED + " login to company3 to add a coupon with same title as a coupon of another company" + RESET);
            try {
                loginManager.login("company3@gmail.com", "company31234", ClientType.Company);
                Coupon coupon3 = Coupon.builder()
                        .id(3)
                        .companyID(3)
                        .category(Category.Electricity)
                        .title("KSP")
                        .description("Computers and staff")
                        .startDate(Date.valueOf("2015-01-01"))
                        .endDate(Date.valueOf("2022-01-01"))
                        .amount(80)
                        .price(140.00)
                        .image("https://eu.backendlessappcontent.com/66D3163A-675F-EEDD-FF1B-2EC2DE0B7600/9060D9F1-741C-4D35-8D20-AB9D5A232A93/files/coupons_prj_img/ksp.jpg")
                        .build();

                System.out.println(GREEN_UNDERLINED + "Try to add another coupon with the title: \"KSP\"" + RESET);
                try {
                    companyService.addCoupon(3, coupon3);
                } catch (AddException e) {
                    System.out.println(e.getMessage());
                }
                //verify
                System.out.println(BLUE + "Verification:" + RESET);
                System.out.println(companyService.getCompanyCoupons(3));
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }



        } catch (Exception e) {
            System.out.println("AH, Houston, we've had a problem!");
        }
    }

    public void companyActions() {
        System.out.println("start actions");

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
                .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/hummus.jpg")
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
                .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/ksp.jpg")
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
                .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/zara.jpg")
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
                .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/shipoodim.jpg")
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
                .image("https://backendlessappcontent.com/714949D1-C002-AF07-FF15-EF82D744B200/ABC8004A-3104-4D43-8B11-A8C3A9493F71/files/coupons_imgs/egypt.jpg")
                .build();


        Coupon couponUsedTitle = Coupon.builder()
                .id(7)
                .companyID(2)
                .category(Category.Food)
                .title("Humus Eliyahu")
                .description("A special Israeli loved Humus")
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2025-01-01"))
                .amount(1000)
                .price(1500.00)
                .image("https://eu.backendlessappcontent.com/66D3163A-675F-EEDD-FF1B-2EC2DE0B7600/9060D9F1-741C-4D35-8D20-AB9D5A232A93/files/coupons_prj_img/hummus.jpg")
                .build();

        Coupon coupon1Update = Coupon.builder()
                .id(1)
                .companyID(2)
                .category(Category.Food)
                .title("Humus Shel Thina")
                .description("Humus Kosher yammy yammy")
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2023-01-01"))
                .amount(50)
                .price(15.00)
                .image("https://eu.backendlessappcontent.com/66D3163A-675F-EEDD-FF1B-2EC2DE0B7600/9060D9F1-741C-4D35-8D20-AB9D5A232A93/files/coupons_prj_img/hummus.jpg")
                .build();

        Coupon coupon2Update = Coupon.builder()
                .id(2)
                .companyID(2)
                .category(Category.Food)
                .title("Pessah BeMitzraim")
                .description("Humus Kosher yammy yammy")
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2023-01-01"))
                .amount(50)
                .price(15.00)
                .image("https://eu.backendlessappcontent.com/66D3163A-675F-EEDD-FF1B-2EC2DE0B7600/9060D9F1-741C-4D35-8D20-AB9D5A232A93/files/coupons_prj_img/egypt.jpg")
                .build();

        Coupon coupon1UpdateCompanyID = Coupon.builder()
                .id(1)
                .companyID(3)
                .category(Category.Food)
                .title("Humus Shel Thina")
                .description("Humus Kosher yammy yammy")
                .startDate(Date.valueOf("2022-01-01"))
                .endDate(Date.valueOf("2023-01-01"))
                .amount(50)
                .price(15.00)
                .image("https://eu.backendlessappcontent.com/66D3163A-675F-EEDD-FF1B-2EC2DE0B7600/9060D9F1-741C-4D35-8D20-AB9D5A232A93/files/coupons_prj_img/hummus.jpg")
                .build();

        System.out.println(GREEN_UNDERLINED + "add 6 good coupons to company2" + RESET);
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

        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(companyService.getCompanyCoupons(2));

        //try to add coupon with same title.
        System.out.println(GREEN_UNDERLINED + "try to add a coupon with same title" + RESET);
        try {
            companyService.addCoupon(2,couponUsedTitle);
        } catch (AddException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(companyService.getCompanyCoupons(2));


        //updating coupon
        System.out.println(GREEN_UNDERLINED + "updating coupon1" + RESET);
        try {
            companyService.updateCoupon(2,coupon1Update);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }
        //try to update coupon2 with already exist title
        System.out.println(GREEN_UNDERLINED + "try to update coupon2 with already exist title (\"Pessah BeMitzraim\")" + RESET);
        try {
            companyService.updateCoupon(2,coupon2Update);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(companyService.getCompanyCoupons(2));


        //try to update coupon of another company
        System.out.println(GREEN_UNDERLINED + "try to update the company ID (of coupon1) from 2 to 3" + RESET);
        try {
            companyService.updateCoupon(2, coupon1UpdateCompanyID);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(companyService.getCompanyCoupons(2));


        //deleting coupon
        System.out.println(GREEN_UNDERLINED + "delete coupon1" + RESET);
        try {
            companyService.deleteCoupon(2,1);
        } catch (DeleteException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(GREEN_UNDERLINED + "get the details of company2 coupons" + RESET);
        System.out.println(companyService.getCompanyCoupons(2));

        System.out.println(GREEN_UNDERLINED + "get all company2 coupons of specific category (Electricity)" + RESET);
        System.out.println(companyService.getCompanyCoupons(2, Category.Electricity));

        System.out.println(GREEN_UNDERLINED + "get all company2 coupons up to specific price (150.50)" + RESET);
        System.out.println(companyService.getCompanyCoupons(2,150.50));

        System.out.println(GREEN_UNDERLINED + "get all the details of the company" + RESET);
        System.out.println(companyService.getCompanyDetails(2));


    }
}
