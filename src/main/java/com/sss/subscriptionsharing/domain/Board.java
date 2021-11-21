package com.sss.subscriptionsharing.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "board")
    private List<Category> categories = new ArrayList<>();

    protected Board() {
    }

    public static Board create(String name) {
        Board board = new Board();
        board.name = name;
        return board;
    }
}
