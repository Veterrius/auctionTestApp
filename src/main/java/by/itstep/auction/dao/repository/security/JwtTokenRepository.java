package by.itstep.auction.dao.repository.security;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public class JwtTokenRepository implements CsrfTokenRepository {

    private String secret;

    @Override
    public CsrfToken generateToken(HttpServletRequest httpServletRequest) {
        return null;
    }

    @Override
    public void saveToken(CsrfToken csrfToken, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
        return null;
    }

    public String getSecret() {
        return secret;
    }
}
