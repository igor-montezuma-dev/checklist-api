package com.example.checklistapi.entity;



import lombok.Data;
import lombok.EqualsAndHashCode;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(indexes = {@Index(name = "IDX_GUID_CAT", columnList = "guid", unique = false)})
public class CategoryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;
    @Column(unique = true)
    private String categoryName;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<ChecklistItemEntity> checklistItems;
}
