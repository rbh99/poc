package com.disney.studios.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest//(classes = TestConfig.class)
@ComponentScan("com.example")
@AutoConfigureMockMvc
class ClientControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void testAddClient() throws Exception {
		mockMvc.perform(post("/clients/username/aaa"))
		.andExpect(status().isCreated())
		;
	}

}
