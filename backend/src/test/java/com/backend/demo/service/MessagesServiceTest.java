package com.backend.demo.service;

import com.backend.demo.exceptions.UnacceptableException;
import com.backend.demo.model.MessagesPostRequest;
import com.backend.demo.repository.MessagesRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringJUnit4ClassRunner.class)
public class MessagesServiceTest {

    @SpyBean
    MessagesService messagesService;

    @MockBean
    MessagesRepository messagesRepository;
    @MockBean
    UsersService usersService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postMessage_test_fail() {
        MessagesPostRequest messagesPostRequest = new MessagesPostRequest("yo@hej.com", "      ");
        assertThatThrownBy(() -> messagesService.postMessage(messagesPostRequest)).isInstanceOf(UnacceptableException.class).hasMessage("The message can't be blank");
    }

}
