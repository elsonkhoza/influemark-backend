package com.influemark.app.influencer;

import com.influemark.app.influencer.Location.Location;
import com.influemark.app.platform.Platform;
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
@Table(name = "influencer")
public class Influencer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String username;
    private String email;
    private String password;

    private String website;

    @Column(name = "profile_url")
    private String profileUrl;

    // Location Relationship
    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "location_id")
    private Location location;

    // Platform Relationship
    @OneToMany(mappedBy = "influencer")
    @OrderBy("order.followers")
    private List<Platform> platforms = new ArrayList<>();


}
