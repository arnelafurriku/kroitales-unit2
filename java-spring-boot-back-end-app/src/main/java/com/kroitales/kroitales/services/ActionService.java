package com.kroitales.kroitales.services;

import com.kroitales.kroitales.models.Action;

import java.util.List;

public interface ActionService {
    List<Action> getAllActions();
    Action getActionById(Long id);
    Action createAction(Action action);
    Action updateAction(Long id, Action updatedAction);
    void deleteAction(Long id);
}