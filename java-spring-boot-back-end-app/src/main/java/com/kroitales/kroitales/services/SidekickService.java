package com.kroitales.kroitales.services;

import com.kroitales.kroitales.models.Sidekick;

import java.util.List;

public interface SidekickService {
    List<Sidekick> getAllSidekicks();
    Sidekick getSidekickById(Long id);
    Sidekick createSidekick(Sidekick sidekick);
    Sidekick updateSidekick(Long id, Sidekick updatedSidekick);
    void deleteSidekick(Long id);
}