package com.aemendes.crud.controllers;

import com.aemendes.crud.exception.ResourceNotFoundException;
import com.aemendes.crud.models.Tutorial;
import com.aemendes.crud.models.TutorialDetails;
import com.aemendes.crud.repository.TutorialDetailsRepository;
import com.aemendes.crud.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TutorialDetailsController {

    private final TutorialRepository tutorialRepository;

    private final TutorialDetailsRepository detailsRepository;

    @Autowired
    public TutorialDetailsController(TutorialRepository tutorialRepository, TutorialDetailsRepository tutorialDetailsRepository) {
        this.tutorialRepository = tutorialRepository;
        this.detailsRepository = tutorialDetailsRepository;
    }

    @GetMapping({"/details/{id}", "/tutorials/{id}/details"})
    public TutorialDetails getDetailsById(@PathVariable(value = "id") Long id) {
        return this.detailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial Details with id = " + id));
    }

    @PostMapping("/tutorials/{tutorialId}/details")
    @ResponseStatus(HttpStatus.CREATED)
    public TutorialDetails createDetails(@PathVariable Long tutorialId,
                                         @RequestBody TutorialDetails detailsRequest) {
        final Tutorial tutorial = this.tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId));

        detailsRequest.setCreatedOn(new java.util.Date());
        detailsRequest.setTutorial(tutorial);

        return this.detailsRepository.save(detailsRequest);
    }

    @PutMapping("/details/{id}")
    public TutorialDetails updateDetails(@PathVariable long id,
                                         @RequestBody TutorialDetails detailsRequest) {
        TutorialDetails details = this.detailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Id " + id + " not found"));

        details.setCreatedBy(detailsRequest.getCreatedBy());

        return this.detailsRepository.save(details);
    }

    @DeleteMapping("/details/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDetails(@PathVariable long id) {
        this.detailsRepository.deleteById(id);
    }

    @DeleteMapping("/tutorials/{tutorialId}/details")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDetailsOfTutorial(@PathVariable Long tutorialId) {
        if (!this.tutorialRepository.existsById(tutorialId)) {
            throw new ResourceNotFoundException("Not found Tutorial with id = " + tutorialId);
        }

        this.detailsRepository.deleteByTutorialId(tutorialId);
    }
}
