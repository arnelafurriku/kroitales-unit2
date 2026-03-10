package com.kroitales.kroitales.services;

import com.kroitales.kroitales.models.Setting;

import java.util.List;

public interface SettingService {
    List<Setting> getAllSettings();
    Setting getSettingById(Long id);
    Setting createSetting(Setting setting);
    Setting updateSetting(Long id, Setting updatedSetting);
    void deleteSetting(Long id);
}