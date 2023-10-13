package com.influemark.app.platform;

import com.influemark.app.influencer.Influencer;
import com.influemark.app.post.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "platform")
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String url;
    @Column(name = "followers")
    private int numberOfFollowers;

    // Influencer Relationship
    @ManyToOne
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;

    // Post relationship
    @OneToMany(mappedBy = "platform", cascade = CascadeType.REMOVE)
    private List<Post> posts = new ArrayList<>();

}
