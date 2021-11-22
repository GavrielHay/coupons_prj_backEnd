package com.project.GavrielsProject.controllers;

import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.PurchaseException;
import com.project.GavrielsProject.services.CustomerService;
import com.project.GavrielsProject.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@CrossOrigin(origins = "localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CustomerController extends ClientController {
    private final CustomerService customerService;
    private final JWTutil jwtUtil;

    /**
     * get customer coupons
     * this method getting a list of all coupons belongs to the logged in customer from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @return  http header with new token + http body that contains  list of the customer's coupons
     */
    //return
    @PostMapping("get_coupons/all")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Customer)) {
            int customerID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(customerService.getCustomerCoupons(customerID), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     *  get customer coupons (by category)
     *  this method getting a list of all coupons belongs to the logged in customer from DB, Filtered by Category. using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param category  Category for filter
     * @return  http header with new token + http body that contains list of the customer's coupons by specific Category
     */
    @PostMapping("get_coupons/by_category/{category}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Customer)) {
            int customerID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(customerService.getCustomerCoupons(customerID, category), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get customer coupons (by maxPrice)
     * this method getting a list of all coupons belongs to the logged in customer from DB, only where coupons price up to a chosen number. using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param maxPrice a chosen number for filtering the higher prices value
     * @return  http header with new token + http body that contains list of the customer's coupons up to a chosen number
     */
    @PostMapping("get_coupons/upto_price/{maxPrice}")
    public ResponseEntity<?> getCustomerCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Customer)) {
            int customerID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(customerService.getCustomerCoupons(customerID, maxPrice), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get customer details
     * this method getting the logged in customer params from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @return  http header with new token + http body that contains data of single customer which logged in
     */
    @PostMapping("details")
    public ResponseEntity<?> getCustomerDetails(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Customer)) {
            int customerID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(customerService.getCustomerDetails(customerID), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * purchase coupon
     * this method  adding a purchase of a coupon for the logged in customer, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param id - id of the coupon for purchase
     * @return  http header with new token
     * @throws PurchaseException will be thrown when the customer's ID is already associated with the ID of the coupon, when coupon is not exist in DB, when amount less then 1 or when endDate has expired.
     */
    //update
    @PostMapping("buy_coupon/{id}")
    public ResponseEntity<?> purchaseCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws PurchaseException {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Customer)) {
            try {
                int customerID = Integer.parseInt(jwtUtil.extractUserID(token));
                customerService.purchaseCoupon(customerID, id);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.OK);
            } catch (PurchaseException e) {
                throw new PurchaseException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }



}
