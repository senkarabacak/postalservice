package com.disys.Letterservice.Repository;

import com.disys.Letterservice.Entity.LetterEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LetterRepo extends CrudRepository<LetterEntity, Integer> {



    /* This method defines a custom database operation to update the status of letter entities based on their country.
     * It is annotated with @Modifying to indicate that it modifies data in the database.
     * @Transactional ensures that this operation is executed within a transaction.
     */
    @Modifying
    @Transactional
    @Query("UPDATE LetterEntity l SET l.status = CASE WHEN l.country IN ('AT', 'DE', 'CH') THEN 'sent' ELSE 'rejected' END")
    void updateStatusBasedOnCountry();
}