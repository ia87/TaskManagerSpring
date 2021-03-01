package com.my.taskmanagerspring.controller;

import com.my.taskmanagerspring.service.ITodoService;
import com.my.taskmanagerspring.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@SpringBootTest
@AutoConfigureMockMvc
class DefaultControllerTest {
    @MockBean
    UserService userService;
    @MockBean
    private ITodoService todoService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void login() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/login").accept(MediaType.ALL))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.header().string("Content-Type", "text/html;charset=UTF-8"))
        ;
    }
}