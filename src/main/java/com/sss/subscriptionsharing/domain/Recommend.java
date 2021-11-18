package com.sss.subscriptionsharing.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Recommend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
