package ru.job4j.urlshortcut.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.job4j.urlshortcut.domain.jsonmodels.Registration;
import ru.job4j.urlshortcut.domain.jsonmodels.SiteName;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest()
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserController userController;

    private String authenticateAndGetJWTToken() throws Exception {
        String username = "89eeb9bc-ddaa-452c-9ea3-6ebf60d05e4b";
        String password = "2cae2efd-70fb-4086-99a4-4b59841604cb";
        String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/login")
                .content(body))
                .andExpect(status().isOk()).andReturn();
        return result.getResponse().getHeader("Authorization");
    }

    @Test
    public void whenUserExistThenCanGetTokenAndGetResourceOnlyForAuthenticatedUsers() throws Exception {
        String token = authenticateAndGetJWTToken();
        mockMvc.perform(get("/onlyAuthenticated")
                .header("Authorization", token))
                .andExpect(status().isOk());
    }

    @Test
    public void whenPassUrlThenGetUsernameAndPassword() throws Exception {
        Registration registration = Registration.builder()
                .registration(true)
                .login("random login(uuid)")
                .password("random pass(uuid)")
                .build();
        when(userController.registration(any(SiteName.class)))
                .thenReturn(new ResponseEntity<>(registration, HttpStatus.OK));
        mockMvc.perform(
                post("/registration")
                        .content("{\"site\":\"ya.ru\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registration", is(true)))
                .andExpect(jsonPath("$.login", is("random login(uuid)")))
                .andExpect(jsonPath("$.password", is("random pass(uuid)")));
        verify(userController, times(1)).registration(any(SiteName.class));
        ArgumentCaptor<SiteName> captor = ArgumentCaptor.forClass(SiteName.class);
        verify(userController).registration(captor.capture());
        assertEquals("ya.ru", captor.getValue().getSite());
    }

}