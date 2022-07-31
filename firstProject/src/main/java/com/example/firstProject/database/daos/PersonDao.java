package com.example.firstProject.database.daos;

import com.example.firstProject.models.Person;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {
    int instertPerson (UUID id, Person person);
    default int addPerson(Person person){
        UUID id =UUID.randomUUID();
        return instertPerson(id,person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);
    int deletePersonById(UUID id);
    int updatePersonById(UUID id,Person person);

    List<String> getPersonNames();
}
