package com.hermesecs.jpatraining.controller;

import com.hermesecs.jpatraining.domain.Collaborator;
import com.hermesecs.jpatraining.repository.CollaboratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/collaborators")
public class CollaboratorController {

    @Autowired
    private CollaboratorRepository collaboratorRepository;

    @RequestMapping(value="/", method=RequestMethod.GET)
    public List<Collaborator> getAllCollaborators() {
        return collaboratorRepository.findAll();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Collaborator getOneCollaborator(@PathVariable(name = "id") Long collaboratorId) {
        if (collaboratorId != null) {
            return collaboratorRepository.findOne(collaboratorId);
        } else {
            return null;
        }
    }
}
