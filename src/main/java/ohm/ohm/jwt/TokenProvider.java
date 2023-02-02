package ohm.ohm.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

//토큰의 생성,토큰의 유효성 검증등을 담당
@Component
public class TokenProvider implements InitializingBean {


    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final String secret;
    private final long tokenValidityInMillseconds;

    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-validity-in-seconds}") long tokenValidityInMillseconds
    ) {
        this.secret = secret;
        this.tokenValidityInMillseconds = tokenValidityInMillseconds * 1000;
    }


    //Bean이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    //Authentication객체의 권한 정보를 이용해 토큰을 생성하는 createToken 메소드
    public String createToken(Authentication authentication) {

        //권한들
        String autorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));


        //만료시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMillseconds);

        //jwt생성후 리턴
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, autorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    //Token에 담겨있는 정보를 이용해 Authentication객체를 리턴하는 메소드 생성 ,createToken 메서드와 상반된 관계
    public Authentication getAuthentication(String token) {


        //전달받은 토큰으로 클레임을 생성
        Claims claims = Jwts.parserBuilder().setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        //클레임에서 권한 정보들을 빼낸다
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //권한 정보들을 이용해 User 객체를 만든다.
        User principal = new User(claims.getSubject(), "", authorities);

        //최종적으로 Authentication 객체를 리턴한다.
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    //토큰의 유효성 검증을 수행하는 ValidateToken 메소드
    public boolean validateToken(String token) {
        try {
            //Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
                Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        //    Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT토큰이 잘못되었습니다.");
        }
        return false;
    }
}