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
import ru.job4j.urlshortcut.domain.Url;
import ru.job4j.urlshortcut.domain.jsonmodels.Code;

import javax.servlet.http.HttpServletResponse;

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
class UrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UrlController urlController;

    private String authenticateAndGetJWTToken() throws Exception {
        String username = "89eeb9bc-ddaa-452c-9ea3-6ebf60d05e4b";
        String password = "2cae2efd-70fb-4086-99a4-4b59841604cb";
        String body = "{\"username\":\"" + username + "\", \"password\":\"" + password + "\"}";
        MvcResult result = mockMvc.perform(post("/login")
                .content(body))
                .andExpect(status().isOk()).andReturn();
        return result.getResponse().getHeader("Authorization");
    }

    @Test
    public void whenRequestHasAuthorizationAndPassUrlThenSendCode() throws Exception {
        Code code = Code.builder().code("random string code").build();
        Url url = Url.builder().url("https://someurl.org/link").build();
        String token = authenticateAndGetJWTToken();
        when(urlController.convert(any(Url.class)))
                .thenReturn(new ResponseEntity<>(code, HttpStatus.OK));
        mockMvc.perform(
                post("/convert")
                        .header("Authorization", token)
                        .content(url.getJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code", is("random string code")));
        verify(urlController, times(1)).convert(any(Url.class));
        ArgumentCaptor<Url> captor = ArgumentCaptor.forClass(Url.class);
        verify(urlController).convert(captor.capture());
        assertEquals("https://someurl.org/link", captor.getValue().getUrl());
    }

    @Test
    public void whenRequestHasCodeWithParameterThenAddRedirectHeader() throws Exception {
        when(urlController.redirect(any(String.class), any(HttpServletResponse.class)))
                .thenReturn(new ResponseEntity<>(HttpStatus.valueOf(302)));
        MvcResult result = mockMvc.perform(
                get("/redirect/{code}", "code"))
                .andExpect(status().is3xxRedirection()).andReturn();
        verify(urlController, times(1))
                .redirect(any(String.class), any(HttpServletResponse.class));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<HttpServletResponse> captor2 = ArgumentCaptor.forClass(HttpServletResponse.class);
        verify(urlController).redirect(captor.capture(), captor2.capture());
        assertEquals("code", captor.getValue());
        assertEquals(302, captor2.getValue().getStatus());
    }
}