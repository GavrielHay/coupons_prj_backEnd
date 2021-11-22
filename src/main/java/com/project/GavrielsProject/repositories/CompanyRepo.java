package com.project.GavrielsProject.repositories;

import com.project.GavrielsProject.beans.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepo extends JpaRepository<Company, Integer> {

    /**
     * Query which check a successfully Company login try by crossing an email and password from the system
     *
     * @param email    the email which inserted by the company client
     * @param password the password which inserted by the company client
     * @return ture - The login parameters is correct. false - The login parameters is incorrect.
     */
    boolean existsByEmailAndPassword(String email, String password);

    /**
     * Query which gain a single Company element from the system by it's email
     *
     * @param email the email of the wished Company
     * @return the wished single Company
     */
    Company findByEmail(String email);

    //Company findByName(String name);//target the company by name for update
    // boolean existsByName(String name);

    /**
     * Query which gain a single Company element from the system by it's ID
     *
     * @param id the ID of the wished Company
     * @return the wished single Company
     */
    Company findById(int id);
}