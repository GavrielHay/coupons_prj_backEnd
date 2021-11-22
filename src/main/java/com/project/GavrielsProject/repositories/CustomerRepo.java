package com.project.GavrielsProject.repositories;

import com.project.GavrielsProject.beans.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    /**
     * Query which check a successfully Customer login try by crossing an email and password from the system
     *
     * @param email    the email which inserted by the customer client
     * @param password the password which inserted by the customer client
     * @return ture - The login parameters is correct. false - The login parameters is incorrect.
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * Query which gain a single Customer element from the system by it's email
     *
     * @param email the email of the wished Customer
     * @return the wished single Customer
     */
    Customer findByEmail(String email);

    /**
     * Query which gain a single Customer element from the system by it's ID
     *
     * @param id the ID of the wished Customer
     * @return the wished single Customer
     */
    Customer findById(int id);

}
