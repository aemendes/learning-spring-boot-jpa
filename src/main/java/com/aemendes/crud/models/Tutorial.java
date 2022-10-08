package com.aemendes.crud.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "tutorials")
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Tutorial extends BaseEntity {
    @Column
    private String title;

    @Column
    private String description;

    @Column
    private boolean published;
}
