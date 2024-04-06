package com.example.checklistapi.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;


@Data
@MappedSuperclass
public class BaseEntity {
    private String guid;
}
