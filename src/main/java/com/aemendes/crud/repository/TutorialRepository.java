package com.aemendes.crud.repository;

import com.aemendes.crud.models.Tutorial;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TutorialRepository extends JpaRepository<Tutorial, Long> {
    //List<Tutorial> findByPublished(boolean published);
    //List<Tutorial> findByTitleContaining(String title);

    /**
     * JPQL Queries
     */
    @Query("SELECT t FROM Tutorial t")
    List<Tutorial> findAll();

    //@Query("SELECT t FROM Tutorial t WHERE t.published=true")
    //List<Tutorial> findByPublished();

    /**
     * Native Queries
     */
    @Query(value = "SELECT * FROM tutorials", nativeQuery = true)
    List<Tutorial> findAllNative();

    @Query(value = "SELECT * FROM tutorials t WHERE t.published=true", nativeQuery = true)
    List<Tutorial> findByPublishedNative();

    /**
     * Pagination & Filter Controller
     */
    Page<Tutorial> findByPublished(boolean published, Pageable pageable);
    Page<Tutorial> findByTitleContaining(String title, Pageable pageable);
}
