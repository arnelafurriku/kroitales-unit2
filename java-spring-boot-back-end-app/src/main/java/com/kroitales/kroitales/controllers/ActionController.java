package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.data.ActionRepository;
import com.kroitales.kroitales.models.Action;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@CrossOrigin(origins = "http://localhost:5173")
public class ActionController {

    private final ActionRepository repository;

    public ActionController(ActionRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Action> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Action create(@RequestBody @Valid Action action) {
        return repository.save(action);
    }
}