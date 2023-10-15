package com.influemark.app.Location;


import com.influemark.app.influencer.Influencer;
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
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "visible")
    private boolean isVisible;

    private String town;
    private String city;
    private String region;
    private String country;

    @OneToOne(mappedBy = "location", orphanRemoval = true)
    private Influencer influencer;

}
