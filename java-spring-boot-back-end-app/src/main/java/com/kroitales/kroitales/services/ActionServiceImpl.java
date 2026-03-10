package com.kroitales.kroitales.services;

import com.kroitales.kroitales.data.ActionRepository;
import com.kroitales.kroitales.models.Action;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {

    private final ActionRepository actionRepository;

    public ActionServiceImpl(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }

    @Override
    public List<Action> getAllActions() {
        return actionRepository.findAll();
    }

    @Override
    public Action getActionById(Long id) {
        return actionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Action not found with id: " + id));
    }

    @Override
    public Action createAction(Action action) {
        return actionRepository.save(action);
    }

    @Override
    public Action updateAction(Long id, Action updatedAction) {
        Action existingAction = getActionById(id);
        existingAction.setName(updatedAction.getName());

        if (updatedAction.getDescription() != null) {
            existingAction.setDescription(updatedAction.getDescription());
        }

        return actionRepository.save(existingAction);
    }

    @Override
    public void deleteAction(Long id) {
        Action existingAction = getActionById(id);
        actionRepository.delete(existingAction);
    }
}