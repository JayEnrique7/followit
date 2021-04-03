package com.backend.demo.config;

import com.backend.demo.config.token.CreateJWT;
import com.backend.demo.dto.Session;
import com.backend.demo.dto.Users;
import com.backend.demo.exceptions.UnauthorizedException;
import com.backend.demo.exceptions.UnexpectedErrorException;
import com.backend.demo.repository.SessionRepository;
import com.backend.demo.service.VerifyJWTService;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;

@RunWith(SpringJUnit4ClassRunner.class)
public class CreateJWTTest {
   @SpyBean
    VerifyJWTService verifyJWTService;

   @MockBean
   SessionRepository sessionRepository;

   @Mock
   Users users;
   @Mock
   Session session;

   CreateJWT createJWT = new CreateJWT();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void validateToken_test_valid() {
        String token = createJWT.createJWT(UUID.randomUUID().toString(), false, "Glenn Quagmire", "glenn.quagmire@mail.com");
        given(users.getId()).willReturn(1);
        given(users.getFirstName()).willReturn("Glenn");
        given(users.getLastName()).willReturn("Quagmire");
        given(users.getEmail()).willReturn("glenn.quagmire@mail.com");
        given(session.getUserId()).willReturn(1);
        System.out.println(token);

        assertTrue(verifyJWTService.validateToken(token, users, session));
    }

    /*@Test
    public void validateToken_test_invalid_token_fail() {
        assertThatThrownBy(() -> verifyJWTService.validateToken("token", users, session)).isInstanceOf(UnauthorizedException.class).hasMessage("Invalid token");
    }

    @Test
    public void validateToken_test_expired_token_fail() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnbGVubi5xdWFnbWlyZUBtYWlsLmNvbSIsImp0aSI6IjBlZTkzYWIyLTBlNDAtNDA5Yy1hNTgzLWY0NWU0YmZkMjE5OSIsImlhdCI6MTYxNzMzMzQxNSwiZXhwIjoxNjE3Mzc2NjE1LCJuYW1lIjoiR2xlbm4gUXVhZ21pcmUiLCJhZG1pbiI6ZmFsc2V9.uCImV06Sk9a6K5JNUvcIMKoBKjA-YkwuiTnggoges-Q";
        willReturn(Optional.of(session)).given(sessionRepository).findSessionByToken(anyString());

        assertThatThrownBy(() -> verifyJWTService.validateToken(token, users, session)).isInstanceOf(ExpiredJwtException.class);
    }

    @Test
    public void getMapFromIoJsonWebTokenClaims_test_fail() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnbGVubi5xdWFnbWlyZUBtYWlsLmNvbSIsImp0aSI6IjBhNzkwNzQ5LThmOTctNGVhOC04MjgwLTY3ZjBjY2Y0ZmIwZiIsImlhdCI6MTYxNzM4MTQzOSwiZXhwIjoxNjE3NDI0NjM5LCJ0ZXN0IjpmYWxzZX0.PYr2ZWwryvHesnD1er4J0Vi2eBOJXt50Y0BOkfBs63c";
        willReturn(Optional.of(session)).given(sessionRepository).findSessionByToken(anyString());
        given(users.getId()).willReturn(1);
        given(users.getFirstName()).willReturn("Glenn");
        given(users.getLastName()).willReturn("Quagmire");
        given(users.getEmail()).willReturn("glenn.quagmire@mail.com");
        given(session.getUserId()).willReturn(1);

        assertThatThrownBy(() -> verifyJWTService.validateToken(token, users, session)).isInstanceOf(UnexpectedErrorException.class).hasMessage("Unexpected error");
    }*/

    @Test
    public void getExp_test() {
        Date dateNow = Calendar.getInstance().getTime();
        Date dateToken = verifyJWTService.getExp(createJWT.createJWT(UUID.randomUUID().toString(), false, "Glenn Quagmire", "glenn.quagmire@mail.com"));
        assertThat(dateNow.before(dateToken), is(true));
    }
}
