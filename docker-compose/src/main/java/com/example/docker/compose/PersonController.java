package com.example.docker.compose;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("api/v1/person")
@RequiredArgsConstructor
public class PersonController {

    private final PersonRepository personRepository;

    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody Person p) {
        Person saved = this.personRepository.save(p);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPersonById(@PathVariable Long id) {
        return this.personRepository.findById(id)
                .map(p -> ResponseEntity.ok().body(p))
                .orElse(ResponseEntity.notFound().build());
    }

}
