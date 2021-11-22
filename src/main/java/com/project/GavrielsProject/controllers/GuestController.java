package com.project.GavrielsProject.controllers;

import com.project.GavrielsProject.beans.UserDetails;
import com.project.GavrielsProject.enums.ClientType;
import com.project.GavrielsProject.exceptions.LoginException;
import com.project.GavrielsProject.repositories.CouponRepo;
import com.project.GavrielsProject.services.CompanyService;
import com.project.GavrielsProject.services.CustomerService;
import com.project.GavrielsProject.services.LoginManager;
import com.project.GavrielsProject.utils.JWTutil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("guest")
@CrossOrigin(origins = "localhost:3000", allowedHeaders = "*")
@RequiredArgsConstructor
public class GuestController extends ClientController {

    private final LoginManager loginManager;
    private final CouponRepo couponRepo;
    private final CompanyService companyService;
    private final CustomerService customerService;
    @Autowired
    private JWTutil jwtUtil;

    /**
     * login
     * this method verifies the user information of the login user, and generates a token with the user information if approved. using a POST request
     *
     * @param userDetails  information of the user try to login
     * @return http header with new token
     * @throws LoginException  If the login attempt fails
     */
    @PostMapping("login")
    private ResponseEntity<?> login(@RequestBody UserDetails userDetails) throws LoginException {
        try {
            if (loginManager.login(userDetails.getEmail(), userDetails.getPassword(), userDetails.getClientType())) {
                switch (userDetails.getClientType()){
                    case Administrator:
                        userDetails.setUserID(0);
                        break;
                    case Company:
                        userDetails.setUserID(companyService.getCompanyID());
                        break;
                    case Customer:
                        userDetails.setUserID(customerService.getCustomerID());
                        break;
                    default:
                        throw new LoginException("login error");
                }

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.set("Authorization", jwtUtil.generateToken(userDetails));
                return new ResponseEntity<>(httpHeaders, HttpStatus.ACCEPTED);
                //return new ResponseEntity<>(jwtUtil.generateToken(userDetails), HttpStatus.ACCEPTED);
            }
        } catch (LoginException e) {
            throw new LoginException(e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    //create

    /**
     * get all coupons
     * this method obtains all coupons from the system, using a GET request
     *
     * @return http body that contains all the coupons
     */
    //return
    @GetMapping("all_coupons")
    public ResponseEntity<?> getAllCoupons() {
        return new ResponseEntity<>(couponRepo.findAll(), HttpStatus.OK);
    }

    /**
     * get one coupon
     * this method obtains single coupon from the system by it's ID, using a GET request
     *
     * @param id the ID of the wished coupon
     * @return  http body  that contains wished single coupon
     */
    @GetMapping("one_coupon/{id}")
    public ResponseEntity<?> getOneCoupon(@PathVariable int id) {
        return new ResponseEntity<>(couponRepo.findById(id), HttpStatus.OK);
    }


}
