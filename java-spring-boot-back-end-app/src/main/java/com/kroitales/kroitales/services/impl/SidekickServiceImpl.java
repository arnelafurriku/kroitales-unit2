package com.kroitales.kroitales.services.impl;

import com.kroitales.kroitales.data.SidekickRepository;
import com.kroitales.kroitales.models.Sidekick;
import com.kroitales.kroitales.services.SidekickService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SidekickServiceImpl implements SidekickService {

    private final SidekickRepository sidekickRepository;

    public SidekickServiceImpl(SidekickRepository sidekickRepository) {
        this.sidekickRepository = sidekickRepository;
    }

    @Override
    public List<Sidekick> getAllSidekicks() {
        return sidekickRepository.findAll();
    }

    @Override
    public Sidekick getSidekickById(Long id) {
        return sidekickRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Sidekick not found with id: " + id));
    }

    @Override
    public Sidekick createSidekick(Sidekick sidekick) {
        return sidekickRepository.save(sidekick);
    }

    @Override
    public Sidekick updateSidekick(Long id, Sidekick updatedSidekick) {
        Sidekick existingSidekick = getSidekickById(id);
        existingSidekick.setName(updatedSidekick.getName());
        return sidekickRepository.save(existingSidekick);
    }

    @Override
    public void deleteSidekick(Long id) {
        Sidekick existingSidekick = getSidekickById(id);
        sidekickRepository.delete(existingSidekick);
    }
}