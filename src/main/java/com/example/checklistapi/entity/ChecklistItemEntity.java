package com.example.checklistapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity(name = "ChecklistItem")
@Table(indexes = {@Index(name = "IDX_GUID_CK_IT", columnList = "guid", unique = false)})
public class ChecklistItemEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long checklistItemId;
    private Boolean isComplete;
    private String description;
    private LocalTime deadline;
    private LocalTime postedDate;
    @ManyToOne
    private Category category;
}
