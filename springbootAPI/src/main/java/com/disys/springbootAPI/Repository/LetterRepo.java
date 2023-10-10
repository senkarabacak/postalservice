package com.disys.springbootAPI.Repository;
import com.disys.springbootAPI.Entity.LetterEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/* This is a Spring Data JPA repository interface declaration.
 * It is annotated with @Repository, indicating that it is a repository for data access.
 * The repository is designed to interact with the "LetterEntity" class.
 * It extends the "CrudRepository" interface, providing basic CRUD (Create, Read, Update, Delete) operations
 * for the "LetterEntity" class. The "LetterEntity" class represents data stored in the "letter" table.
 * The second generic type parameter, "Integer," specifies the type of the primary key for the entity.
 */


@Repository
public interface LetterRepo extends CrudRepository<LetterEntity, Integer> {
}