package com.kroitales.kroitales.data;

import com.kroitales.kroitales.models.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long> {
}