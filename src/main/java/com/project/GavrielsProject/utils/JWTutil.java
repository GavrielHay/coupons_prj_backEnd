package com.project.GavrielsProject.utils;

import com.project.GavrielsProject.beans.UserDetails;
import com.project.GavrielsProject.enums.ClientType;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Component
@NoArgsConstructor
@AllArgsConstructor
@Data
public class JWTutil {
    /**
     * Signature algorithm field - type of encryption
     */
    private String signatureAlgorithm = SignatureAlgorithm.HS256.getJcaName();
    /**
     * Encoded secret key field - our private key
     */
    private String encodedSecretKey = "67bcab57567e82d686c8a7040e32d181844678992d0169670eaced73bbf81ce3";
    /**
     * Decoded secret key field - creates our private key
     */
    private Key decodedSecretKey = new SecretKeySpec(Base64.getMimeDecoder().decode(encodedSecretKey), this.signatureAlgorithm);

    /**
     * Generate token
     * this method generates our key
     *
     * @param userDetails- the user's details
     * @return Token in String
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("password", userDetails.password);
        claims.put("clientType", userDetails.getClientType());
        claims.put("userID", userDetails.getUserID());
        String myToken = createToken(claims, userDetails.getEmail());
        //System.out.println("New token was created : " + myToken);
        return myToken;
    }

    /**
     * Create token
     * this method creates our token
     *
     * @param claims  - contains the fields which we are basing the token on
     * @param subject - the main field we choose for the token
     * @return Token in String
     */
    private String createToken(Map<String, Object> claims, String subject) {
        Instant now = Instant.now();
        return Jwts.builder().setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(30, ChronoUnit.MINUTES)))
                .signWith(this.decodedSecretKey)
                .compact();
    }

    /**
     * Expose Token
     * this method removes the extras that are not the token itself, from the token received in the header
     *
     * @param token the part with the token form the header of request
     * @return string - The isolated token
     */
    private String exposeToken(String token) {
        if (token.contains("Bearer")) {
            String myToken[] = token.split(" ");
            return myToken.length<2?"empty-token":myToken[1];
        }
        return token;
    }


    /**
     * Extract all claims
     * this method extracts all the claims in json
     *
     * @param token - the user's token
     * @return Claims
     * @throws ExpiredJwtException throws error if something went wrong
     */
    public Claims extractAllClaims(String token) throws ExpiredJwtException, SignatureException {
        token = exposeToken(token);
        JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(this.decodedSecretKey).build();
        //System.out.println(jwtParser.parseClaimsJws(token).getSignature());
        return jwtParser.parseClaimsJws(token).getBody();
    }

    /**
     * Extract email
     * this method extracts the user's email
     *
     * @param token- the user's token
     * @return String - the user's email
     */
    public String extractEmail(String token) {
        token = exposeToken(token);
        // System.out.println("email:"+extractAllClaims(token).getSubject());
        return extractAllClaims(token).getSubject();
    }

    /**
     * Extract clientType
     * this method extracts the user's clientType
     *
     * @param token- the user's token
     * @return String - the user's clientType
     */
    public String extractClientType(String token) {
        token = exposeToken(token);
        //for extract password:
        //System.out.println("password:"+extractAllClaims(token).getId());
        return extractAllClaims(token).get("clientType").toString();
    }

    /**
     * Extract userID
     * this method extracts the user's  ID
     *
     * @param token- the user's token
     * @return String - the user's ID
     */
    public String extractUserID(String token) {
        token = exposeToken(token);
        return extractAllClaims(token).get("userID").toString();
    }

    /**
     * Extract expiration date
     * this method extracts the expiration date of the token
     *
     * @param token -the user's token
     * @return the token's expiration date
     */
    public Date extractExpirationDate(String token) {
        token = exposeToken(token);
        return extractAllClaims(token).getExpiration();
    }


        /**
         * Is token valid
         * this method checks if the token is expired and if Signature is correct
         * @param token - the user's token
         * @return boolean- if it's expired
         */

    private boolean isTokenValid(String token) {
        token = exposeToken(token);
        try {
            extractAllClaims(token);
            return false;
        } catch (ExpiredJwtException | SignatureException | IllegalArgumentException | MalformedJwtException err) {
            return true;
        }
    }


    /**
     * Validate token
     * this method checks the validation of the user's details with the token
     *
     * @param token - the user's token
     * @return boolean - if the token is valid
     */
    public boolean validateToken(String token) {
        token = exposeToken(token);
        //final String username = extractEmail(token);
        return !isTokenValid(token); //validate expiration  & validate signature & validate not null/empty
    }

    /**
     * ClientType check
     * this method checks if the token includes a specific client type
     *
     * @param token - the user's token
     * @param clientType - the client type we want to check
     * @return boolean - if its included
     */
    public boolean clientTypeCheck(String token,ClientType clientType ) {
        token = exposeToken(token);
        return ClientType.valueOf(extractClientType(token)).equals(clientType);
    }

}
