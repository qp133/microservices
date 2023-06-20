package com.example.webapi.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "enable_status")
    private Boolean enableStatus;
}