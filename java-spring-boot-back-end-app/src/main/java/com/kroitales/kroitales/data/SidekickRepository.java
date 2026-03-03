package com.kroitales.kroitales.data;

import com.kroitales.kroitales.models.Sidekick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SidekickRepository extends JpaRepository<Sidekick, Long> {
}