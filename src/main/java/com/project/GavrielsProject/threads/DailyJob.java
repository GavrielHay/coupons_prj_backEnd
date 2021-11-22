package com.project.GavrielsProject.threads;


import com.project.GavrielsProject.repositories.CouponRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Component
@EnableAsync
@EnableScheduling
public class DailyJob {
    /**
     * Access to the Coupon Repository to be able to delete coupons in the DB.
     */
    @Autowired
    CouponRepo couponRepo;
    boolean quit = false;

    /**
     * An asynchronous repeat method (run on "background") which delete all expired coupons in DB every 24H.
     */
    @Async
    @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
    public void DeleteOldCoupons() {
        if (!quit) {
            couponRepo.deleteByEndDateBefore(Date.valueOf(LocalDate.now()));
            System.out.println("* * * * * * * * * * * * * * * * * * Notice! All expired coupons has DELETED automatically by the system --- Next deletion within 24H * * * * * * * * * * * * * * * * * * ");
        }
    }

    /**
     * Stopping the DailyJob - the method allows to prevent the implementation of the "DeleteOldCoupons" method.
     */
    public void setQuit() {
        this.quit = true;
        System.out.println("* * * * * * * * * * * * * * * * * * Notice! you STOPPED the \"Automatic Expired Coupons Deleter\". * * * * * * * * * * * * * * * * * * ");
    }

}
