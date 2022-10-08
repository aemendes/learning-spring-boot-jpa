package com.aemendes.crud.controllers;

import com.aemendes.crud.exception.ResourceNotFoundException;
import com.aemendes.crud.models.Tutorial;
import com.aemendes.crud.repository.TutorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class TutorialController {

    private final TutorialRepository tutorialRepository;

    @Autowired
    public TutorialController(TutorialRepository tutorialRepository) {
        this.tutorialRepository = tutorialRepository;
    }

    @GetMapping(path = "/tutorials")
    public Map<String, Object> getAllTutorials(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(required = false) String title) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<Tutorial> pageTuts;

        if (title == null)
            pageTuts = tutorialRepository.findAll(paging);
        else
            pageTuts = tutorialRepository.findByTitleContaining(title, paging);

        return Map.ofEntries(
                Map.entry("tutorials", pageTuts.getContent()),
                Map.entry("currentPage", pageTuts.getNumber()),
                Map.entry("totalItems", pageTuts.getTotalElements()),
                Map.entry("totalPages", pageTuts.getTotalPages())
        );
    }

    @GetMapping(path = "/tutorials/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public Tutorial getTutorialById(@PathVariable long id) {
        return tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));
    }

    @PostMapping(path = "/tutorials")
    @ResponseStatus(HttpStatus.CREATED)
    public Tutorial createTutorial(@RequestBody Tutorial tutorial) {
        return this.tutorialRepository
                .save(Tutorial.builder()
                        .title(tutorial.getTitle())
                        .description(tutorial.getDescription())
                        .published(false)
                        .build());
    }

    @PutMapping("/tutorials/{id}")
    public Tutorial updateTutorial(@PathVariable("id") long id, @RequestBody Tutorial tutorial) {
        Tutorial _tutorial = this.tutorialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + id));

        _tutorial.setTitle(tutorial.getTitle());
        _tutorial.setDescription(tutorial.getDescription());
        _tutorial.setPublished(tutorial.isPublished());

        return this.tutorialRepository.save(_tutorial);
    }

    @DeleteMapping("/tutorials/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTutorial(@PathVariable("id") long id) {
        this.tutorialRepository.deleteById(id);
    }

    @DeleteMapping("/tutorials")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllTutorials() {
        this.tutorialRepository.deleteAll();
    }

    @GetMapping("/tutorials/published")
    public Map<String, Object> findByPublished(@RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "3") int size,
                                               @RequestParam(defaultValue = "true") boolean isPublished) {
        final Pageable paging = PageRequest.of(page, size);
        final Page<Tutorial> pageTuts = this.tutorialRepository.findByPublished(isPublished, paging);

        return Map.ofEntries(
                Map.entry("tutorials", pageTuts.getContent()),
                Map.entry("currentPage", pageTuts.getNumber()),
                Map.entry("totalItems", pageTuts.getTotalElements()),
                Map.entry("totalPages", pageTuts.getTotalPages())
        );
    }
}
