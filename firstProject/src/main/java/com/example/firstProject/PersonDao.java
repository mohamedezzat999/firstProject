package com.example.firstProject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PersonDao {
    int instertPerson (UUID id,Person person);
    default int addPerson(Person person){
        UUID id =UUID.randomUUID();
        return instertPerson(id,person);
    }

    List<Person> selectAllPeople();

    Optional<Person> selectPersonById(UUID id);
    int deletePersonById(UUID id);
    int updatePersonById(UUID id,Person person);
}
