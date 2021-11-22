package com.project.GavrielsProject.clr;

import com.project.GavrielsProject.beans.Company;
import com.project.GavrielsProject.beans.Customer;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.*;
import com.project.GavrielsProject.services.AdminService;
import com.project.GavrielsProject.services.LoginManager;
import com.project.GavrielsProject.utils.ArtUtils.Banners;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import static com.project.GavrielsProject.utils.ArtUtils.FontDesign.ConsoleColors.*;

//@Component
@RequiredArgsConstructor
@Order(2)
public class AdminTest implements CommandLineRunner {
    private final LoginManager loginManager;
    private final AdminService adminService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println(GREEN_BRIGHT + "\n\n\n" + Banners.ADMIN + RESET);

        try {

            System.out.println(GREEN_UNDERLINED + "try to login as admin with wrong params" + RESET);
            try {
                loginManager.login("admin@admin.com", "WRONG_PASSWORD", ClientType.Administrator);
                AdminActions();
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(GREEN_UNDERLINED + "login as admin" + RESET);
            try {
                loginManager.login("admin@admin.com", "admin", ClientType.Administrator);
                AdminActions();
            } catch (LoginException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("AH, Houston, we've had a problem!");
        }
    }


    public void AdminActions() {

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

        Company companyUsedName = Company.builder()
                .id(4)
                .name("company1")
                .email("company4@gmail.com")
                .password("company41234")
                .build();
        Company companyUsedEmail = Company.builder()
                .id(5)
                .name("company5")
                .email("company1@gmail.com")
                .password("company51234")
                .build();

        Company company1Update = Company.builder()
                .id(1)
                .name("try different name")
                .email("try different email")
                .password("company11234")
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
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(adminService.getAllCompanies());

        //try to add company with used name
        System.out.println(GREEN_UNDERLINED + "try to add a company with same name (\"company1\")" + RESET);
        try {
            adminService.addCompany(companyUsedName);
        } catch (AddException e) {
            System.out.println(e.getMessage());
        }
        //try to add company with used email
        System.out.println(GREEN_UNDERLINED + "try to add a company with same email (\"company1@gmail.com\")" + RESET);
        try {
            adminService.addCompany(companyUsedEmail);
        } catch (AddException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(adminService.getAllCompanies());

        System.out.println(GREEN_UNDERLINED + "update a company email/password (of company1)" + RESET);
        try {
            adminService.updateCompany(company1Update);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(adminService.getAllCompanies());

        System.out.println(GREEN_UNDERLINED + "delete company1" + RESET);
        try {
            adminService.deleteCompany(1);
        } catch (DeleteException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(GREEN_UNDERLINED + "get all companies details" + RESET);
        System.out.println(adminService.getAllCompanies());

        System.out.println(GREEN_UNDERLINED + "get one company details (company2)" + RESET);
        System.out.println(adminService.getOneCompany(2));


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

        Customer customer1goodUpdate = Customer.builder()
                .id(1)
                .firstName("customer1UPDATED")
                .lastName("1family")
                .email("customer1@gmail.com")
                .password("customer11234")
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
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(adminService.getAllCustomers());

        System.out.println(GREEN_UNDERLINED + "update customer first-name/last-name/email/password (of customer1)" + RESET);
        try {
            adminService.updateCustomer(customer1goodUpdate);
        } catch (UpdateException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(BLUE + "Verification:" + RESET);
        System.out.println(adminService.getAllCustomers());

        System.out.println(GREEN_UNDERLINED + "delete customer1" + RESET);
        try {
            adminService.deleteCustomer(1);
        } catch (DeleteException e) {
            System.out.println(e.getMessage());
        }
        //verify
        System.out.println(GREEN_UNDERLINED + "get all customers details" + RESET);
        System.out.println(adminService.getAllCustomers());

        System.out.println(GREEN_UNDERLINED + "get one customer details (customer2)" + RESET);
        System.out.println(adminService.getOneCustomer(2));

    }


}



