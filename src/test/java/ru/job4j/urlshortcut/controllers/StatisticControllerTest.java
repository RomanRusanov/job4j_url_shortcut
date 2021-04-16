package ru.job4j.urlshortcut.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.urlshortcut.domain.jsonmodels.StatisticResponse;

import java.util.Collections;

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
class StatisticControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    StatisticController statisticController;

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
    public void whenRequestStatisticHasAuthorizationThenSendStatisticResponse() throws Exception {
        String token = this.authenticateAndGetJWTToken();
        StatisticResponse statisticResponse = StatisticResponse.builder()
                .url("https://someurl.org/link")
                .total(5L)
                .build();
        when(statisticController.statistic(any(String.class)))
                .thenReturn(Collections.singletonList(statisticResponse));
        mockMvc.perform(
                get("/statistic")
                        .header("Authorization", token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].url", is("https://someurl.org/link")))
                .andExpect(jsonPath("$[0].total", is(5)));
        verify(statisticController, times(1)).statistic(any(String.class));
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(statisticController).statistic(captor.capture());
        assertEquals("89eeb9bc-ddaa-452c-9ea3-6ebf60d05e4b", captor.getValue());
    }

}