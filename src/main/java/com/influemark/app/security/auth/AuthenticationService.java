package com.influemark.app.security.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.influemark.app.influencer.Influencer;
import com.influemark.app.influencer.InfluencerRepository;
import com.influemark.app.security.jwt.JwtService;
import com.influemark.app.security.token.Token;
import com.influemark.app.security.token.TokenRepository;
import com.influemark.app.security.token.TokenType;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final InfluencerRepository influencerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    AuthenticationManager authenticationManager;

    public ResponseEntity<String> register(RegisterRequest registerRequest) {

        // new influencer
        Influencer influencer = new Influencer();
        influencer.setName(registerRequest.getName());
        influencer.setEmail(registerRequest.getEmail());
        influencer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        influencer.setRole(registerRequest.getRole());
        // save influencer
        influencerRepository.save(influencer);
        // generating and saving jwt
        String jwtToken = jwtService.generateToken(influencer);
        String refreshToken = jwtService.generateRefreshToken(influencer);
        createAndSaveInfluencerToken(influencer, jwtToken);

        // creating http headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("access-token", jwtToken);
        httpHeaders.add("refresh-token", refreshToken);

        // Response
        return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
    }

    public ResponseEntity<String> login(LoginRequest loginRequest) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        Influencer influencer = this.influencerRepository
                .findByEmail(loginRequest.getEmail())
                .orElseThrow();

        String jwtToken = jwtService.generateToken(influencer);
        String refreshToken = jwtService.generateRefreshToken(influencer);

        // revoke
        revokeAllInfluencerTokens(influencer);
        // save new
        createAndSaveInfluencerToken(influencer, jwtToken);
        // creating http headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("access-token", jwtToken);
        httpHeaders.add("refresh-token", refreshToken);
        // Response
        return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);

    }

    private void createAndSaveInfluencerToken(Influencer influencer, String jwtToken) {
        Token token = new Token();
        token.setInfluencer(influencer);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        // save
        tokenRepository.save(token);
    }

    private void revokeAllInfluencerTokens(Influencer influencer) {
        List<Token> activeTokens
                = tokenRepository.findAllActiveTokensByInfluencer(influencer.getId());
        if (activeTokens.isEmpty())
            return;
        activeTokens.forEach(token -> {
            token.setRevoked(true);
            token.setExpired(true);
        });

        tokenRepository.saveAll(activeTokens);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String influencerEmail;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        influencerEmail = jwtService.extractUsername(refreshToken);

        if (influencerEmail != null) {
            Influencer influencer = this.influencerRepository.findByEmail(influencerEmail)
                    .orElseThrow();

            if (jwtService.isTokenValid(refreshToken, influencer)) {

                String accessToken = jwtService.generateToken(influencer);

                revokeAllInfluencerTokens(influencer);
                createAndSaveInfluencerToken(influencer, accessToken);

                // creating http headers
                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add("access-token", accessToken);
                httpHeaders.add("refresh-token", refreshToken);
                // Response
                ResponseEntity<String> responseEntity = new ResponseEntity<String>(httpHeaders, HttpStatus.OK);

                new ObjectMapper().writeValue(response.getOutputStream(), responseEntity);
            }
        }
    }
}
