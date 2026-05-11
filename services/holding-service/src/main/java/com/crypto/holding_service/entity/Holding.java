package com.crypto.holding_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "holdings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Holding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userEmail;

    private String symbol;

    private Double quantity;
}