package com.disys.Packageservice.Repository;

import com.disys.Packageservice.Entity.PackageEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageRepo extends CrudRepository<PackageEntity, Integer> {

     /* This method defines a custom database operation to update the status of package entities based on their weight.
     * It is annotated with @Modifying to indicate that it modifies data in the database.
     * @Transactional ensures that this operation is executed within a transaction.
     */
    @Modifying
    @Transactional
    @Query("UPDATE PackageEntity p SET p.status = CASE WHEN  p.weight > 0 AND p.weight < 25 THEN 'sent' ELSE 'rejected' END")
    void updateStatusBasedOnWeight();
}