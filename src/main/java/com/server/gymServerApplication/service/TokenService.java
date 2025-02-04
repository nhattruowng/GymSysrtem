package com.server.gymServerApplication.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.server.gymServerApplication.entity.User;
import com.server.gymServerApplication.repository.IUserrepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class TokenService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    private final IUserrepository iUserrepository;


    @Autowired
    public TokenService(IUserrepository iUserrepository) {
        this.iUserrepository = iUserrepository;
    }

    /**
     * Phương thức tạo JWT cho người dùng
     *
     * @param user Đối tượng User chứa thông tin người dùng
     * @return JWT token dưới dạng chuỗi
     */
    String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

//        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
//                .subject(user.getEmail())
//                .issueTime(new Date(System.currentTimeMillis()))
//                .expirationTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
//                .claim("roles", List.of("ROLE_" + user.getRole().toString())) // Thêm tiền tố "ROLE_" vào quyền// Sử dụng role đã được xử lý
//                .build();
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issueTime(new Date(System.currentTimeMillis()))
                .expirationTime(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .claim("phone", user.getPhone())
                .claim("roles", List.of("ROLE_" + user.getRole().toString()))
                .build();


        JWSObject jwsObject = new JWSObject(header, new Payload(jwtClaimsSet.toJSONObject()));

        try {
            jwsObject.sign(new MACSigner(SECRET_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException("Token generation failed", e);
        }
    }


    /**
     * Phương thức xác thực JWT token
     *
     * @param token JWT token cần xác thực
     * @return JWTClaimsSet nếu xác thực thành công, null nếu không thành công
     * @throws ParseException nếu có lỗi khi phân tích token
     * @throws JOSEException  nếu xác thực không thành công
     */
    public JWTClaimsSet verifyToken(String token) throws ParseException, JOSEException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(SECRET_KEY.getBytes());

        if (signedJWT.verify(verifier)) {
            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            Date expirationTime = claims.getExpirationTime();

            if (expirationTime != null && expirationTime.before(new Date())) {
                throw new RuntimeException("Token has expired");
            }

            return claims;
        }
        throw new RuntimeException("Token verification failed");
    }


}
