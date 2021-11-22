package com.project.GavrielsProject.controllers;

import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.AddException;
import com.project.GavrielsProject.exceptions.DeleteException;
import com.project.GavrielsProject.exceptions.UpdateException;
import com.project.GavrielsProject.services.CompanyService;
import com.project.GavrielsProject.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("company")
@CrossOrigin(origins = "localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class CompanyController extends ClientController {
    private final CompanyService companyService;
    private final JWTutil jwtUtil;

    //create

    /**
     * add coupon
     * this method adding a coupon belong to the logged in company in the DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param coupon the coupon for add
     * @return http header with new token
     * @throws AddException will be thrown when coupon is already exist in DB, or try to add a coupon with an ID of another company.
     */
    @PostMapping("add_coupon")
    public ResponseEntity<?> addCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws AddException {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            try {
                int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
                companyService.addCoupon(companyID, coupon);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.CREATED);
            } catch (AddException e) {
                throw new AddException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //return

    /**
     * get company coupons
     * this method getting a list of all coupons belongs to the logged in company from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @return http header with new token + http body that contains list of the company's coupons
     */
    @PostMapping("get_coupons/all")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(companyService.getCompanyCoupons(companyID), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get company coupons (by category)
     * this method getting a list of all coupons belongs to the logged in company from DB, Filtered by Category. using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param category Category for filter
     * @return http header with new token +  http body that contains list of the company's coupons by specific Category
     */
    @PostMapping("get_coupons/by_category/{category}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable Category category) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(companyService.getCompanyCoupons(companyID,category), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get company coupons  (by maxPrice)
     * this method getting a list of all coupons belongs to the logged in company from DB, only where coupons price up to a chosen number. using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param maxPrice a chosen number for filtering the higher prices value
     * @return http header with new token + http body that contains  list of the company's coupons up to a chosen number
     */
    @PostMapping("get_coupons/upto_price/{maxPrice}")
    public ResponseEntity<?> getCompanyCoupons(@RequestHeader(name = "Authorization") String token, @PathVariable double maxPrice) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(companyService.getCompanyCoupons(companyID, maxPrice), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    /**
     * get company details
     * this method getting the logged in company params from DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @return http header with new token + http body that contains data of single company which logged in
     */
    @PostMapping("details")
    public ResponseEntity<?> getCompanyDetails(@RequestHeader(name = "Authorization") String token) {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
            return new ResponseEntity<>(companyService.getCompanyDetails(companyID), newTokenInHeaders(token), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    //update

    /**
     * update coupon
     * this method updating a coupon belong to the logged in company in the DB, using a POST request
     *
     * @param token A token obtained from the user for checking authorization
     * @param coupon  the updated coupon for inject
     * @return http header with new token
     * @throws UpdateException will be thrown when coupon belong to another company, not exist or try to put a used title.
     */
    @PostMapping("update_coupon")
    public ResponseEntity<?> updateCoupon(@RequestHeader(name = "Authorization") String token, @RequestBody Coupon coupon) throws UpdateException {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            try {
                int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
                companyService.updateCoupon(companyID, coupon);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.ACCEPTED);
            } catch (UpdateException e) {
                throw new UpdateException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //delete

    /**
     * delete coupon
     * this method deleting a coupon belong to the logged in company in the DB, using a DELETE request
     *
     * @param token A token obtained from the user for checking authorization
     * @param id  the id of the coupon in the DB selected to delete
     * @return http header with new token
     * @throws DeleteException will be thrown when coupon belong to another company or not exist.
     */
    @DeleteMapping("delete_coupon/{id}")
    public ResponseEntity<?> deleteCoupon(@RequestHeader(name = "Authorization") String token, @PathVariable int id) throws DeleteException {
        if (jwtUtil.validateToken(token) && jwtUtil.clientTypeCheck(token, ClientType.Company)) {
            try {
                int companyID = Integer.parseInt(jwtUtil.extractUserID(token));
                companyService.deleteCoupon(companyID, id);
                return new ResponseEntity<>(newTokenInHeaders(token), HttpStatus.OK);
            } catch (DeleteException e) {
                throw new DeleteException(e.getMessage());
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
