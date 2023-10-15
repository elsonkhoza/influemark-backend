package com.influemark.app.security.token;

import com.influemark.app.influencer.Influencer;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {
    @Column(unique = true)
    public String token;
    @Enumerated(EnumType.STRING)
    public TokenType tokenType = TokenType.BEARER;
    public boolean revoked;
    public boolean expired;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "influencer_id")
    private Influencer influencer;

}
