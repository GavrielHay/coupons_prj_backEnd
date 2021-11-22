package com.project.GavrielsProject.services;

import com.project.GavrielsProject.beans.Company;
import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.enums.Category;
import com.project.GavrielsProject.exceptions.AddException;
import com.project.GavrielsProject.exceptions.DeleteException;
import com.project.GavrielsProject.exceptions.UpdateException;
import com.project.GavrielsProject.repositories.CompanyRepo;
import com.project.GavrielsProject.repositories.CouponRepo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


@Data
@Service
@RequiredArgsConstructor
public class CompanyService {
    /**
     * Access to the Repositories Queries consumed by the company client
     */
    @Autowired
    CompanyRepo companyRepo;
    @Autowired
    CouponRepo couponRepo;
    /**
     * id of the logged in company
     */
    private int companyID;

    /**
     * Data authentication for Company login try
     *
     * @param email    Email to verify
     * @param password password to verify
     * @return true = params match , false = params didn't match
     */
    public boolean companyLogin(String email, String password) {
        if (companyRepo.existsByEmailAndPassword(email, password)) {
            this.companyID = companyRepo.findByEmail(email).getId();
            return true;
        }
        return false;
    }

    /**
     * adding a coupon belong to the logged in company in the DB
     *
     * @param companyID - The connected company which performs the operation
     * @param coupon the coupon for add
     * @throws AddException will be thrown when coupon is already exist in DB, or try to add a coupon with an ID of another company.
     */
//can avoid the SQL Exception and error from DB server by using the other IF term, but it's  overload the server so prefer this way.
    public void addCoupon(int companyID, Coupon coupon) throws AddException {
        if (coupon.getCompanyID() == companyID) {
            if (coupon.getStartDate().before(coupon.getEndDate()) && coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
                try {
                    System.out.println("Adding coupon \"" + coupon.getTitle() + "\"...");
                    couponRepo.save(coupon);
                    System.out.println("The coupon \"" + coupon.getTitle() + "\" was added.");
                } catch (DataIntegrityViolationException e) {
                    throw new AddException("Can't add coupon, please check if title already in use.");
                }
            } else {
                throw new AddException("Can't add coupon. Make sure you enter proper dates");
            }
        } else {
            throw new AddException("Can't add coupon. you are not allowed to use another company ID");

        }
    }


    /**
     * updating a coupon belong to the logged in company in the DB
     *
     * @param companyID - The connected company which performs the operation
     * @param coupon the updated coupon for inject
     * @throws UpdateException will be thrown when coupon belong to another company, not exist or try to put a used title.
     */
    public void updateCoupon(int companyID, Coupon coupon) throws UpdateException {
        if (coupon.getCompanyID() == companyID) {
            if (coupon.getStartDate().before(coupon.getEndDate()) && coupon.getEndDate().after(Date.valueOf(LocalDate.now()))) {
                if (!couponRepo.existsById(coupon.getId())) {
                    throw new UpdateException("Sorry, can't find coupon.");
                }
                try {
                    Coupon couponUpdate = couponRepo.findById(coupon.getId());
                    couponUpdate.setCategory(coupon.getCategory());
                    couponUpdate.setTitle(coupon.getTitle());
                    couponUpdate.setDescription(coupon.getDescription());
                    couponUpdate.setStartDate(coupon.getStartDate());
                    couponUpdate.setEndDate(coupon.getEndDate());
                    couponUpdate.setAmount(coupon.getAmount());
                    couponUpdate.setPrice(coupon.getPrice());
                    couponUpdate.setImage(coupon.getImage());
                    couponRepo.saveAndFlush(couponUpdate);
                    System.out.println("coupon '" + coupon.getTitle() + "' was updated.");
                } catch (DataIntegrityViolationException e) {
                    throw new UpdateException("Can't update coupon, this title is already in use. Please try another one.");
                }
            } else {
                throw new UpdateException("Can't update coupon. Make sure you enter proper dates");
            }
        } else {
            throw new UpdateException("You are not allowed to edit a coupons of another company.");
        }
    }

    /**
     * deleting a coupon belong to the logged in company in the DB
     *
     * @param companyID - The connected company which performs the operation
     * @param couponID the id of the coupon in the DB selected to delete
     * @throws DeleteException will be thrown when coupon belong to another company or not exist.
     */
    public void deleteCoupon(int companyID, int couponID) throws DeleteException {
        if (!couponRepo.existsById(couponID)) {
            throw new DeleteException("Sorry, can't find coupon.");
        }
        if (couponRepo.findById(couponID).getCompanyID() == companyID) {
            //couponRepo.deleteById(couponID);
            couponRepo.deleteCouponById(couponID);
        } else {
            System.out.println("You are not allowed to edit a coupons of another company.");
        }
    }

    /**
     * getting a list of all coupons belongs to the logged in company from DB
     *
     * @param companyID - The connected company which performs the operation
     * @return list of the company's coupons
     */
    public List<Coupon> getCompanyCoupons(int companyID) {
        return couponRepo.findByCompanyID(companyID);
    }

    /**
     * getting a list of all coupons belongs to the logged in company from DB, Filtered by Category
     *
     * @param companyID - The connected company which performs the operation
     * @param category Category for filter
     * @return list of the company's coupons by specific Category
     */
    public List<Coupon> getCompanyCoupons(int companyID, Category category) {
        return couponRepo.findByCompanyIDAndCategory(companyID, category);
    }

    /**
     * getting a list of all coupons belongs to the logged in company from DB, only where coupons price up to a chosen number
     *
     * @param companyID - The connected company which performs the operation
     * @param maxPrice a chosen number for filtering the higher prices value
     * @return list of the company's coupons up to a chosen number
     */
    public List<Coupon> getCompanyCoupons(int companyID, double maxPrice) {
        return couponRepo.findByCompanyIDAndPriceLessThanEqual(companyID, maxPrice);
    }

    /**
     * getting the logged in company params from DB (by the logged in company ID)
     *
     * @param companyID - The connected company which performs the operation
     * @return single company which logged in
     */
    public Company getCompanyDetails(int companyID) {
        return companyRepo.findById(companyID);
    }


}
