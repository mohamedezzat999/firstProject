package com.example.firstProject.controllers;

import com.example.firstProject.models.Person;
import com.example.firstProject.services.PersonService;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("v1/person")
 class PersonController {

    private final PersonService personService;

    PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/add")
    public void addPerson(@RequestBody Person person){
        personService.addPerson(person);
    }
    @GetMapping("/get-all")
    public List<Person> getAllPeople(){
        return personService.getAllPeople();
    }
    @GetMapping(path = "/get/{id}")
    public Person getPersonById(@PathVariable("id") UUID id){
        return personService.getPersonById(id).orElse(null);
    }

    @DeleteMapping(path = "/delete/{id}")
    public void deletePersonById(@PathVariable("id") UUID id){
        personService.deletePerson(id);
    }

    @PutMapping(path = "/update/{id}")
    public void updatePerson(@PathVariable("id") UUID id,@RequestBody Person personToUpdate){
        personService.updatePerson(id,personToUpdate);
    }
    @GetMapping("/get-names")
    public List<String> usersNames(){
        return personService.getNames();
    }
}
