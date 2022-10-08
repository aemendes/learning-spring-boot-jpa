package com.aemendes.crud.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tutorial_details")
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class TutorialDetails {
    @Id
    private Long id;

    @Column
    private Date createdOn;

    @Column
    private String createdBy;

    /**
     * Tutorial -> TutorialDetail
     *
     * The @OneToOne annotation for one-to-one relationship with the Tutorial entity
     * MapsId annotation that makes the id field serve as both Primary Key and Foreign Key (shared primary key).
     */
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "tutorial_id")
    @ToString.Exclude
    private Tutorial tutorial;
}
