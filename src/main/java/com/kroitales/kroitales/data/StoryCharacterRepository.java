package com.kroitales.kroitales.data;

import com.kroitales.kroitales.models.StoryCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoryCharacterRepository extends JpaRepository<StoryCharacter, Long> {
}