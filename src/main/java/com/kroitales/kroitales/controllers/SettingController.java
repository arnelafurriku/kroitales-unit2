package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.data.SettingRepository;
import com.kroitales.kroitales.models.Setting;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "http://localhost:5173")
public class SettingController {

    private final SettingRepository repository;

    public SettingController(SettingRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Setting> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setting> getById(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Setting create(@RequestBody @Valid Setting setting) {
        return repository.save(setting);
    }
}