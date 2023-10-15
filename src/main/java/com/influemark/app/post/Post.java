package com.influemark.app.post;

import com.influemark.app.marketer.Marketer;
import com.influemark.app.platform.Platform;
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
@Table(name = "post")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "post_url", nullable = false)
    private String url;

    @Column(name = "comments")
    private int numberOfComments;
    @Column(name = "likes")
    private int numberOfLikes;
    @Column(name = "days")
    private int numberOfDays;
    @Column(name = "online")
    private boolean isOnline;

    @Column(name = "approved")
    private boolean isApproved;
    private boolean payed;
    private double cost;

    // Platform relationship
    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    // Marketer
    @ManyToOne
    @JoinColumn(name = "marketer_id")
    private Marketer marketer;

}
