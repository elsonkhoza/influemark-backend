package com.influemark.app.security.auth;

import com.influemark.app.influencer.Influencer;
import com.influemark.app.influencer.InfluencerRepository;
import com.influemark.app.security.jwt.JwtService;
import com.influemark.app.security.token.Token;
import com.influemark.app.security.token.TokenRepository;
import com.influemark.app.security.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private  final InfluencerRepository influencerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    public ResponseEntity<String> register(RegisterRequest registerRequest){

        // new influencer
        Influencer influencer=new Influencer();
        influencer.setName(registerRequest.getName());
        influencer.setEmail(registerRequest.getEmail());
        influencer.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        influencer.setRole(registerRequest.getRole());
        // save influencer
        influencerRepository.save(influencer);
        // generating and saving jwt
        var jwtToken=jwtService.generateToken(influencer);
        var refreshToken=jwtService.generateRefreshToken(influencer);
        createAndSaveInfluencerToken(influencer,jwtToken);

        // creating http headers
        HttpHeaders httpHeaders=new HttpHeaders();
        httpHeaders.add("access-token",jwtToken);
        httpHeaders.add("refresh-token",refreshToken);

        // Response
        return new ResponseEntity<String>(httpHeaders, HttpStatus.OK);
    }

    private void createAndSaveInfluencerToken(Influencer influencer, String jwtToken) {
        Token token=new Token();
        token.setInfluencer(influencer);
        token.setToken(jwtToken);
        token.setTokenType(TokenType.BEARER);
        token.setExpired(false);
        token.setRevoked(false);
        // save
        tokenRepository.save(token);
    }

}
