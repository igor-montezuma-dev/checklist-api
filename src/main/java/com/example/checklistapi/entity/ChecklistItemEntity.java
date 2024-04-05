package com.example.checklistapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistItemEntity extends BaseEntity{

    private Long checklistItemId;
    private Boolean isCompleted;
    private String itemDescription;
    private LocalTime deadline;
    private LocalTime postedDate;
    private Category category;
}
