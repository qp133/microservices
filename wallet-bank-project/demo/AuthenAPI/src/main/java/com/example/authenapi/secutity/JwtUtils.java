package com.example.authenapi.secutity;

import com.example.authenapi.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtils {
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    static ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false).setSerializationInclusion(JsonInclude.Include.NON_NULL);

    // Token có hạn trong vòng 24 giờ kể từ thời điểm tạo, thời gian tính theo giây
    @Value("${jwt.duration}")
    private Integer duration;

    // Lấy giá trị key được cấu hình trong file appliacation.properties
    // Key này sẽ được sử dụng để mã hóa và giải mã
    @Value("${jwt.secret}")
    private String secret;

    // Sinh token
    public String generateToken(UserDetails userDetails) {
        // Lưu thông tin của user vào claims
        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", userDetails.getAuthorities());

        // 1. Định nghĩa các claims: issuer, expiration, subject, id
        // 2. Mã hóa token sử dụng thuật toán HS512 và key bí mật
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + duration * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    // Lấy thông tin được lưu trong token
    public Claims getClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    // Lấy token từ trong cookie
    public String getTokenFromRequest(HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            return requestTokenHeader.substring(7);
        }
        return null;
    }

    public String getAuthentication(String token) throws Exception {
        if (token != null) {
            // parse the token.
            Claims claims =  Jwts.parser()
                    .setSigningKey("supersecret")
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();

            String username = claims.getSubject();
            Date expDate = claims.getExpiration();

            // TODO: check thời gian hết hạn
            Date now = new Date();
            if (expDate != null && expDate.after(now)) {
                return username;
            } else {
                //Token đã hết hạn
                throw new Exception("Verify failed");
            }
        }
        return null;
    }

    public User getUserFromToken(String token) throws Exception{
        if (token != null) {
            Claims claims = Jwts.parser()
                    .setSigningKey("supersecret")
                    .parseClaimsJws(token.replace("Bearer", ""))
                    .getBody();
            String email = claims.getSubject();

            CustomUserDetails customUserDetails = (CustomUserDetails) customUserDetailsService.loadUserByUsername(email);

            User user = User.builder()
                    .email(email)
                    .uniqueIdName(customUserDetails.getUniqueIdName())
                    .uniqueIdValue(customUserDetails.getUniqueIdValue())
                    .phoneNumber(customUserDetails.getPhoneNumber())
                    .fullName(customUserDetails.getFullname())
                    .customerType(customUserDetails.getCustomerType())
                    .customerNo(customUserDetails.getCustomerNo())
                    .accountNo(customUserDetails.getAccountNo())
                    .build();
            return user;
        }
        else {
            throw new Exception("Get user failed");
        }
    }

}