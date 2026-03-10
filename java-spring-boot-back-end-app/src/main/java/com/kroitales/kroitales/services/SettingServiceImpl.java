package com.kroitales.kroitales.services;

import com.kroitales.kroitales.data.SettingRepository;
import com.kroitales.kroitales.models.Setting;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    public SettingServiceImpl(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    @Override
    public List<Setting> getAllSettings() {
        return settingRepository.findAll();
    }

    @Override
    public Setting getSettingById(Long id) {
        return settingRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Setting not found with id: " + id));
    }

    @Override
    public Setting createSetting(Setting setting) {
        return settingRepository.save(setting);
    }

    @Override
    public Setting updateSetting(Long id, Setting updatedSetting) {
        Setting existingSetting = getSettingById(id);
        existingSetting.setName(updatedSetting.getName());

        if (updatedSetting.getDescription() != null) {
            existingSetting.setDescription(updatedSetting.getDescription());
        }

        return settingRepository.save(existingSetting);
    }

    @Override
    public void deleteSetting(Long id) {
        Setting existingSetting = getSettingById(id);
        settingRepository.delete(existingSetting);
    }
}