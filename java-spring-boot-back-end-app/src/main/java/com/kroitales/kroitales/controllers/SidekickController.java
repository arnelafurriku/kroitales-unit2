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

    private final SidekickRepository sidekickRepository;

    public SidekickController(SidekickRepository sidekickRepository) {
        this.sidekickRepository = sidekickRepository;
    }

    @GetMapping
    public List<Sidekick> getAllSidekicks() {
        return sidekickRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sidekick> getSidekickById(@PathVariable Long id) {
        return sidekickRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Sidekick createSidekick(@RequestBody @Valid Sidekick sidekick) {
        return sidekickRepository.save(sidekick);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sidekick> updateSidekick(
            @PathVariable Long id,
            @RequestBody @Valid Sidekick updatedSidekick
    ) {
        return sidekickRepository.findById(id)
                .map(existingSidekick -> {
                    existingSidekick.setName(updatedSidekick.getName());
                    Sidekick savedSidekick = sidekickRepository.save(existingSidekick);
                    return ResponseEntity.ok(savedSidekick);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}