package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.data.SidekickRepository;
import com.kroitales.kroitales.models.Sidekick;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sidekicks")
@CrossOrigin(origins = "http://localhost:5173")
public class SidekickController {

    private final SidekickRepository repository;

    public SidekickController(SidekickRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Sidekick> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sidekick> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sidekick create(@RequestBody @Valid Sidekick sidekick) {
        return repository.save(sidekick);
    }
}