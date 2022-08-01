package com.example.firstProject.database.resourses;

import com.example.firstProject.database.daos.PersonDao;
import com.example.firstProject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository("postgres")
public class PersonDataAcessService implements PersonDao {

    private final JdbcTemplate jdbcTemplate;

    private static List<Person> db =new ArrayList<>();
    @Autowired
    public PersonDataAcessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int instertPerson(UUID id, Person person) {

        final String sql = "INSERT  INTO person (id,name) VALUES (? , ?)";


        db.add(new Person(id , person.getName()));
        return 1;
    }

    @Override
    public List<Person> selectAllPeople() {
        final String sql = "SELECT  id, name FROM person";
        return jdbcTemplate.query(sql, (resultSet, i) ->{
            UUID id =UUID.fromString(resultSet.getString("id"));
            String name  = resultSet.getString("name");
            return new Person(id,name);
        });
    }

    @Override
    public Optional<Person> selectPersonById(UUID id) {
        final String sql = "SELECT  id, name FROM person WHERE id = ?";

        Person person = jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                (resultSet, i) ->{
                    UUID personId =UUID.fromString(resultSet.getString("id"));
                    String name  = resultSet.getString("name");
                    return new Person(personId,name);
                });
        return Optional.ofNullable(person);
    }

    @Override
    public int deletePersonById(UUID id) {
        Optional<Person> personMaybe = selectPersonById(id);
        if (personMaybe.isEmpty()) {
            return 0;
        }
        db.remove(personMaybe.get());
        return 1;
    }

    @Override
    public int updatePersonById(UUID id, Person update) {
        return selectPersonById(id)
                .map(person ->{
                    int indexOfPersonToUpdate = db.indexOf(person);
                    if (indexOfPersonToUpdate>=0) {
                        db.set(indexOfPersonToUpdate, new Person(id,update.getName()));
                        return 1;
                    }
                    return 0;
                }).orElse(0);
    }

    @Override
    public List<String> getPersonNames() {
        List<String> names = new ArrayList<>();
        db.forEach(person -> {
            names.add(person.getName());
        });
        return names;
    }
}
