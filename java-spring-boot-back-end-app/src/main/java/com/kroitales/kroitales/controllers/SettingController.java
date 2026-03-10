package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.models.Setting;
import com.kroitales.kroitales.services.SettingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "http://localhost:5173")
public class SettingController {

    private final SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    @GetMapping
    public List<Setting> getAllSettings() {
        return settingService.getAllSettings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Setting> getSettingById(@PathVariable Long id) {
        return ResponseEntity.ok(settingService.getSettingById(id));
    }

    @PostMapping
    public ResponseEntity<Setting> createSetting(@RequestBody @Valid Setting setting) {
        return ResponseEntity.ok(settingService.createSetting(setting));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Setting> updateSetting(
            @PathVariable Long id,
            @RequestBody @Valid Setting updatedSetting
    ) {
        return ResponseEntity.ok(settingService.updateSetting(id, updatedSetting));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSetting(@PathVariable Long id) {
        settingService.deleteSetting(id);
        return ResponseEntity.noContent().build();
    }
}