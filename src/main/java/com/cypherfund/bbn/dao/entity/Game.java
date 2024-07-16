package com.cypherfund.bbn.dao.entity;

import com.cypherfund.bbn.utils.Enumerations;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Enumerations.GAME_STATUS status;

    @Size(max = 255)
    @Column(name = "img_url")
    private String imgUrl;

}
