package com.kroitales.kroitales.controllers;

import com.kroitales.kroitales.models.Action;
import com.kroitales.kroitales.services.ActionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/actions")
@CrossOrigin(origins = "http://localhost:5173")
public class ActionController {

    private final ActionService actionService;

    public ActionController(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping
    public List<Action> getAllActions() {
        return actionService.getAllActions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Action> getActionById(@PathVariable Long id) {
        return ResponseEntity.ok(actionService.getActionById(id));
    }

    @PostMapping
    public ResponseEntity<Action> createAction(@RequestBody @Valid Action action) {
        return ResponseEntity.ok(actionService.createAction(action));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Action> updateAction(
            @PathVariable Long id,
            @RequestBody @Valid Action updatedAction
    ) {
        return ResponseEntity.ok(actionService.updateAction(id, updatedAction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAction(@PathVariable Long id) {
        actionService.deleteAction(id);
        return ResponseEntity.noContent().build();
    }
}