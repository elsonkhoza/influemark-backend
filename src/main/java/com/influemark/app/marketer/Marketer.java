package com.influemark.app.marketer;

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
@Table(name = "marketer")
public class Marketer {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name ="marketer_name", nullable = false)
    private String name;

    @Column(name = "company_name")
    private String companyName;
    private String website;

    @OneToMany(mappedBy = "marketer")
    private List<Post> posts = new ArrayList<>();

}
