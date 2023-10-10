package com.disys.springbootAPI.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.disys.springbootAPI.Entity.PackageEntity;


/* This is a Spring Data JPA repository interface declaration.
 * It is annotated with @Repository, indicating that it is a repository for data access.
 * The repository is designed to interact with the "LetterEntity" class.
 * It extends the "CrudRepository" interface, providing basic CRUD (Create, Read, Update, Delete) operations
 * for the "LetterEntity" class. The "PackageEntity" class represents data stored in the "letter" table.
 * The second generic type parameter, "Integer," specifies the type of the primary key for the entity.
 */


@Repository
public interface PackageRepo extends CrudRepository<PackageEntity, Integer> {
}