package com.influemark.app.post;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "influencer")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String url;

    @Column(name = "comments")
    private int numberOfComments;
    @Column(name = "likes")
    private int numberOfLikes;
    @Column(name = "days")
    private int numberOfDays;
    @Column(name = "online")
    private boolean isOnline;
    private boolean payed;
    private double cost;
}
