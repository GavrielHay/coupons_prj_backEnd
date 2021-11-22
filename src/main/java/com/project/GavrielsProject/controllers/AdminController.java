package com.project.GavrielsProject.controllers;

import com.project.GavrielsProject.beans.Company;
import com.project.GavrielsProject.beans.Customer;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.AddException;
import com.project.GavrielsProject.exceptions.DeleteException;
import com.project.GavrielsProject.exceptions.UpdateException;
import com.project.GavrielsProject.services.AdminService;
import com.project.GavrielsProject.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
@CrossOrigin(origins = "localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class AdminController extends ClientController {
    private final AdminService adminService;
    @Autowired
    private JWTutil jwtUtil;


    //create

    /**
     * add company
     * this method adding a company to DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param company  the company for add
     * @return http header with new token
     * @throws AddException  will be thrown when company is already exist in DB
     */
    @PostMapping("add_company")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws AddException {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            try {
                adminService.addCompany(company);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.CREATED);
            } catch (AddException e) {
                throw new AddException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * add customer
     * this method adding a customer to DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param customer the customer for add
     * @return http header with new token
     * @throws AddException will be thrown when customer is already exist in DB
     */
    @PostMapping("add_customer")
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> addCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws AddException {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            try {
                adminService.addCustomer(customer);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.CREATED);
            } catch (AddException e) {
                throw new AddException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    //return

    /**
     * get all companies
     * this method getting the list of all companies with their data from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @return http header with new token + http body that contains list of all companies
     */
    @PostMapping("get_company/all")
    public ResponseEntity<?> getAllCompanies(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            return new ResponseEntity<>(adminService.getAllCompanies(), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get one company
     * this method getting a single company with its data from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param id  the id of the company in the DB
     * @return http header with new token +  http body that contains the single company
     */
    @PostMapping("get_company/{id}")
    public ResponseEntity<?> getOneCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            return new ResponseEntity<>(adminService.getOneCompany(id), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get all customers
     * this method getting the list of all customers with their data from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @return http header with new token +  http body that contains  list of all customers
     */
    @PostMapping("get_customer/all")
    public ResponseEntity<?> getAllCustomers(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            return new ResponseEntity<>(adminService.getAllCustomers(), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get one customer
     * this method getting a customer with its data from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param id the id of the customer in the DB
     * @return http header with new token +  http body that contains the single customer
     */
    @PostMapping("get_customer/{id}")
    public ResponseEntity<?> getOneCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) {
        if (jwtUtil.validateToken(token)&& jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            return new ResponseEntity<>(adminService.getOneCustomer(id), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //update

    /**
     * update company
     * this method  updating a company in the DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param company  the updated company for inject
     * @return http header with new token
     * @throws UpdateException will be thrown if company is doesn't exist
     */
    @PostMapping("update_company")
    public ResponseEntity<?> updateCompany(@RequestHeader(name = "Authorization") String token, @RequestBody Company company) throws UpdateException {
        if (jwtUtil.validateToken(token)&& jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            try {
                adminService.updateCompany(company);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.ACCEPTED);
            } catch (UpdateException e) {
                throw new UpdateException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * update customer
     * this method  updating a customer in the DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param customer the updated customer for inject
     * @return http header with new token
     * @throws UpdateException will be thrown if customer is doesn't exist
     */
    @PostMapping("update_customer")
    public ResponseEntity<?> updateCustomer(@RequestHeader(name = "Authorization") String token, @RequestBody Customer customer) throws UpdateException {
        if (jwtUtil.validateToken(token)&& jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            try {
                adminService.updateCustomer(customer);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.ACCEPTED);
            } catch (UpdateException e) {
                throw new UpdateException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    //delete

    /**
     * delete company
     * this method deleting a company from DB, using a DELETE request
     *
     * @param token A token obtained from the user for checking authorization
     * @param id  id of the company in the DB
     * @return http header with new token
     * @throws DeleteException will be thrown if company is doesn't exist
     */
    @DeleteMapping("delete_company/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCompany(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws DeleteException {
        if (jwtUtil.validateToken(token)&& jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            try {
                adminService.deleteCompany(id);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.OK);
            } catch (DeleteException e) {
                throw new DeleteException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * delete customer
     * this method  deleting a customer from DB, using a DELETE request
     *
     * @param token A token obtained from the user for checking authorization
     * @param id  the id of the customer in the DB
     * @return http header with new token
     * @throws DeleteException  will be thrown if customer is doesn't exist
     */
    @DeleteMapping("delete_customer/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCustomer(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws DeleteException {
        if (jwtUtil.validateToken(token)&& jwtUtil.clientTypeCheck(token, ClientType.Administrator)) {
            try {
                adminService.deleteCustomer(id);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.OK);
            } catch (DeleteException e) {
                throw new DeleteException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
