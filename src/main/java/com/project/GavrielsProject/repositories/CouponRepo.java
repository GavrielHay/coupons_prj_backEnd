package com.project.GavrielsProject.repositories;

import com.project.GavrielsProject.beans.Coupon;
import com.project.GavrielsProject.beans.Customer;
import com.project.GavrielsProject.enums.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface CouponRepo extends JpaRepository<Coupon, Integer> {

    /**
     * Query which gain a list of all coupons belongs to a single company from DB
     *
     * @param companyID id of the company which the coupons belong to
     * @return list of company's coupons
     */
    List<Coupon> findByCompanyID(int companyID);

    /**
     * Query which gain a list of coupons belongs to a single company from DB, Filtered by Category
     *
     * @param companyID id of the company which the coupons belong to
     * @param category  Category for filter
     * @return list of company's coupons by specific Category
     */
    List<Coupon> findByCompanyIDAndCategory(int companyID, Category category);

    /**
     * Query which gain a list of coupons belongs to a single company from DB, only where coupons price up to a chosen number
     *
     * @param companyID id of the company which the coupons belong to
     * @param prise     a chosen number for filtering the higher prices value
     * @return list of company's coupons up to a chosen number
     */
    List<Coupon> findByCompanyIDAndPriceLessThanEqual(int companyID, double prise);


    /**
     * Query which gain a list of all coupons belongs to a single customer from DB
     *
     * @param customer the customer which the coupons belongs to
     * @return list of customer coupons
     */
    List<Coupon> findByCustomers(Customer customer);

    /**
     * Query which gain a list of coupons belongs to a single customer from DB, Filtered by Category
     *
     * @param customer the customer which the coupons belongs to
     * @param category Category for filter
     * @return list of customer coupons by specific Category
     */
    List<Coupon> findByCustomersAndCategory(Customer customer, Category category);

    /**
     * Query which gain a list of coupons belongs to a single customer from DB, only where coupons price up to a chosen number
     *
     * @param customer the customer which the coupons belongs to
     * @param prise    a chosen number for filtering the higher prices value
     * @return list of customer coupons up to a chosen number
     */
    List<Coupon> findByCustomersAndPriceLessThanEqual(Customer customer, double prise);


    /**
     * Query which check if a coupon expired by check coupon's "end date" param
     *
     * @param couponID id of the Coupon in the DB
     * @param date     end date of the Coupon
     * @return true = not expired  , false =  expired
     */
    boolean existsByIdAndEndDateAfter(int couponID, Date date);


    /**
     * Query which sub the coupon's Amount by 1. (used in coupon purchase method)
     *
     * @param couponID the ID of the Coupon which its amount need to be sub.
     * @Transactional used to let SPRING edit "amount" in the DB.
     * @Modifying // used to let SPRING edit "amount" in the DB.
     */
    @Transactional
    @Modifying
    @Query(value = "UPDATE Coupon SET amount=amount-1 WHERE id= :couponID")
    void subCouponAmountBy1(int couponID);


    /**
     * Query which deleting all the expired coupons - which endDate is before current time (used by DailyJob THREAD)
     *
     * @param date used to enter the current time parameter in the tread method. Allows flexibility of change as wished by the programmer in the thread
     * @Transactional used to let SPRING edit (delete coupons) in the DB.
     */
    @Transactional
    void deleteByEndDateBefore(Date date);

    /**
     * Query which delete a single coupon by it's ID
     *
     * @param id the ID of the coupon we want to delete
     * @Transactional used to let SPRING delete in the DB.
     * @Modifying // used to let SPRING delete in the DB.
     */
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM `couponsdb`.`coupons` WHERE `id`=?1", nativeQuery = true)
    void deleteCouponById(int id);

    /**
     * Query which gain a single coupon element from the system by it's ID
     *
     * @param id the ID of the wished coupon
     * @return the wished single coupon
     */
    Coupon findById(int id);

}
