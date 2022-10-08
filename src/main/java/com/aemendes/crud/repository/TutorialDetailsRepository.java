package com.aemendes.crud.repository;

import com.aemendes.crud.models.TutorialDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface TutorialDetailsRepository extends JpaRepository<TutorialDetails, Long> {
    @Transactional
    void deleteById(long id);

    @Transactional
    void deleteByTutorialId(long tutorialId);
}
