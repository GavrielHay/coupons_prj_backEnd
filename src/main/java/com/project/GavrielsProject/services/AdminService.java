package com.project.GavrielsProject.services;

import com.project.GavrielsProject.beans.Company;
import com.project.GavrielsProject.beans.Customer;
import com.project.GavrielsProject.exceptions.AddException;
import com.project.GavrielsProject.exceptions.DeleteException;
import com.project.GavrielsProject.exceptions.UpdateException;
import com.project.GavrielsProject.repositories.CompanyRepo;
import com.project.GavrielsProject.repositories.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    /**
     * Access to the Repositories Queries consumed by the administrator
     */
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    CustomerRepo customerRepo;

    /**
     * hard coded ADMIN final details for login
     */
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_EMAIL = "admin@admin.com";

    /**
     * Data authentication for admin login try
     *
     * @param email    Email to verify
     * @param password password to verify
     * @return true = params match , false = params didn't match
     */
    public boolean adminLogin(String email, String password) {
        return email.equals(ADMIN_EMAIL) && password.equals(ADMIN_PASSWORD);
    }

    /**
     * adding a company to DB
     *
     * @param company the company for add
     * @throws AddException will be thrown when company is already exist in DB
     */
    public void addCompany(Company company) throws AddException {
        try {
            companyRepo.save(company);
            System.out.println("company '" + company.getName() + "' was added.");
        } catch (DataIntegrityViolationException e) {
            throw new AddException("Sorry,The company \"" + company.getName() + "\" couldn't be added. Please check if name or email are already in use.");
        }
    }

    /**
     * updating a company in the DB
     *
     * @param company the updated company for inject
     * @throws UpdateException will be thrown if company is doesn't exist
     */
    public void updateCompany(Company company) throws UpdateException {
        if (!companyRepo.existsById(company.getId())) {
            throw new UpdateException("Sorry, can't find company.");
        }
        /*
        if (!companyRepo.findById(company.getId()).getEmail().equals(company.getEmail())){
            if(companyRepo.existsByEmail(company.getEmail())){
                throw new UpdateException("Sorry, this Email is already in use.");
            }
        }
         */
        try {
            Company companyUpdate = companyRepo.findById(company.getId());
            companyUpdate.setName(company.getName());
            companyUpdate.setEmail(company.getEmail());
            companyUpdate.setPassword(company.getPassword());
            companyUpdate.setCoupons(company.getCoupons());//in my opinion should remove this line. but not on demand
            companyRepo.saveAndFlush(companyUpdate);
            System.out.println("company '" + company.getName() + "' was updated.");
            if (!companyRepo.findById(company.getId()).getName().equals(company.getName())) {
                System.out.println("notice that the company's name couldn't be updated");
            }
        } catch (DataIntegrityViolationException e) {
            throw new UpdateException("Sorry, company couldn't be updated. Please check if this Email is already in use.");
        }
    }

    /**
     * deleting a company from DB
     *
     * @param companyID id of the company in the DB
     * @throws DeleteException will be thrown if company is doesn't exist
     */
    public void deleteCompany(int companyID) throws DeleteException {
        if (!companyRepo.existsById(companyID)) {
            throw new DeleteException("Sorry, can't find company.");
        }
        companyRepo.delete(companyRepo.findById(companyID));
        System.out.println("company in id '" + companyID + "' was deleted.");
    }

    /**
     * getting the list of all companies with their data from DB
     *
     * @return list of all companies
     */
    public List<Company> getAllCompanies() {
        return companyRepo.findAll();
    }

    /**
     * getting a single company with its data from DB
     *
     * @param companyID the id of the company in the DB
     * @return a single company
     */
    public Company getOneCompany(int companyID) {
        return companyRepo.findById(companyID);
    }

    /**
     * adding a customer to DB
     *
     * @param customer the customer for add
     * @throws AddException will be thrown when customer is already exist in DB
     */
    public void addCustomer(Customer customer) throws AddException {
        try {
            customerRepo.save(customer);
            System.out.println("customer '" + customer.getFirstName() + " " + customer.getLastName() + "' was added.");
        } catch (DataIntegrityViolationException e) {
            throw new AddException("'Sorry,The customer '" + customer.getFirstName() + " " + customer.getLastName() + "' couldn't be added. Please check if email is already in use.");
        }
    }

    /**
     * updating a customer in the DB
     *
     * @param customer the updated customer for inject
     * @throws UpdateException will be thrown if customer is doesn't exist
     */
    public void updateCustomer(Customer customer) throws UpdateException {
        if (!customerRepo.existsById(customer.getId())) {
            throw new UpdateException("Sorry, can't find customer.");
        }
        try {
            Customer customerUpdate = customerRepo.findById(customer.getId());
            customerUpdate.setFirstName(customer.getFirstName());
            customerUpdate.setLastName(customer.getLastName());
            customerUpdate.setEmail(customer.getEmail());
            customerUpdate.setPassword(customer.getPassword());
            customerUpdate.setCoupons(customer.getCoupons());
            customerRepo.saveAndFlush(customerUpdate);
            System.out.println("customer '" + customer.getFirstName() + " " + customer.getLastName() + "' was updated.");
        } catch (DataIntegrityViolationException e) {
            throw new UpdateException("Sorry, customer couldn't be updated. Please check if this Email is already in use.");
        }
    }

    /**
     * deleting a customer from DB
     *
     * @param customerID the id of the customer in the DB
     * @throws DeleteException will be thrown if customer is doesn't exist
     */
    public void deleteCustomer(int customerID) throws DeleteException {
        if (!customerRepo.existsById(customerID)) {
            throw new DeleteException("Sorry, can't find customer.");
        }
        customerRepo.delete(customerRepo.findById(customerID));
        System.out.println("customer in id '" + customerID + "' was deleted.");
    }

    /**
     * getting the list of all customers with their data from DB
     *
     * @return list of all customers
     */
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * getting a customer with its data from DB
     *
     * @param customerID the id of the customer in the DB
     * @return a single customer
     */
    public Customer getOneCustomer(int customerID) {
        return customerRepo.findById(customerID);
    }


}
