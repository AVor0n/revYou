package hh.crossreview.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.inject.Named;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.SecurityContext;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Named
public class JwtTokenUtils {
  @Value("${jwt.secret.access}")
  private String secretAccess;

  @Value("${jwt.lifetime.access}")
  private Duration jwtLifetimeAccess;

  @Value("${jwt.secret.refresh}")
  private String secretRefresh;

  @Value("${jwt.lifetime.refresh}")
  private Duration jwtLifetimeRefresh;



  public String generateAccessToken(UserDetails userDetails) {
    Map<String, Object> claims = new HashMap<>();
    List<String> rolesList = userDetails.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());
    claims.put("role", rolesList);
    Date issuedDate = new Date();
    Date accessExpiredDate = new Date(issuedDate.getTime() + jwtLifetimeAccess.toMillis());
    return Jwts.builder()
            .setClaims(claims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(issuedDate)
            .setExpiration(accessExpiredDate)
            .signWith(SignatureAlgorithm.HS256, secretAccess)
            .compact();
  }

  public String generateRefreshToken(UserDetails userDetails) {
    Date issuedDate = new Date();
    Date refreshExpiredDate = new Date(issuedDate.getTime() + jwtLifetimeRefresh.toMillis());
    return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setExpiration(refreshExpiredDate)
            .signWith(SignatureAlgorithm.HS256, secretRefresh)
            .compact();
  }

  public String getUsername(String token) {
    return getAccessClaims(token).getSubject();
  }

  public List<String> getRoles(String token) {
    return getAccessClaims(token).get("role", List.class);
  }

  public Claims getAccessClaims(String token) {
    return getAllClaimsFromToken(token, secretAccess);
  }

  public Claims getRefreshClaims(String token) {
    return getAllClaimsFromToken(token, secretRefresh);
  }

  private Claims getAllClaimsFromToken(String token, String secret) {
    return Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody();
  }

  public UsernamePasswordAuthenticationToken retrieveTokenFromContext(SecurityContext securityContext) {
    return (UsernamePasswordAuthenticationToken) securityContext.getUserPrincipal();
  }

  public boolean validateRefreshToken(String token) {
    try {
      Jwts.parser()
              .setSigningKey(secretRefresh)
              .parseClaimsJws(token)
              .getBody();
      return true;
    } catch (ExpiredJwtException e) {
      throw new BadRequestException("Время жизни токена истекло");
    } catch (UnsupportedJwtException unsEx) {
      throw new BadRequestException("Неподдерживаемый формат jwt");
    } catch (MalformedJwtException mjEx) {
      throw new BadRequestException("Искаженный jwt");
    } catch (SignatureException sEx) {
      throw new BadRequestException("jwt ключ имеет неверную подпись");
    } catch (Exception e) {
      throw new BadRequestException("Невалидный токен");
    }

  }

}
