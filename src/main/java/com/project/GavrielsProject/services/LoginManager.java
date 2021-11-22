package com.project.GavrielsProject.services;

import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.LoginException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class LoginManager {
    /**
     * Access to the clients services for authentication login check by client
     */
    @Autowired
    AdminService adminService;
    @Autowired
    CompanyService companyService;
    @Autowired
    CustomerService customerService;

    /**
     * compositing login authentication for each client type
     *
     * @param email      the entered email for verify
     * @param password   the entered password for verify
     * @param clientType the client type try to login
     * @return true =  login to client success , false = login to client failed
     * @throws LoginException If the information authentication failed
     */
    public boolean login(String email, String password, ClientType clientType) throws LoginException {
        switch (clientType) {
            case Administrator:
                System.out.println("Try to login as ADMIN...");
                if (adminService.adminLogin(email, password)) {
                    System.out.println("Administrator is logged in");
                    return true;
                }
                throw new LoginException("The details entered are incorrect. Please try again");

            case Company:
                System.out.println("Try to login as COMPANY...");
                if (companyService.companyLogin(email, password)) {
                    System.out.println("Company " + companyService.getCompanyID() + " is logged in");
                    return true;
                }
                throw new LoginException("The details entered are incorrect. Please try again");

            case Customer:
                System.out.println("Try to login as CUSTOMER...");
                if (customerService.customerLogin(email, password)) {
                    System.out.println("Customer " + customerService.getCustomerDetails(customerService.getCustomerID()).getFirstName() + " is logged in");
                    return true;
                }
                throw new LoginException("The details entered are incorrect. Please try again");

            default:
                return false;
        }
    }

}
