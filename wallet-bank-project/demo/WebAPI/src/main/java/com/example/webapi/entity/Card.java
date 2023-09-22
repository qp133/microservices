package com.example.webapi.entity;

import lombok.*;

import javax.persistence.*;

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
    private String card_number;

    @Column(name = "card_holder_name")
    private String card_holder_name;

    @Column(name = "card_exp")
    private String card_exp;

    @Column(name = "card_ccv")
    private String card_ccv;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}