package com.simple.echo.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import lombok.extern.slf4j.Slf4j;



@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class EchoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
    void testGet() throws Exception{
		
		mockMvc.
		perform(get("/echo/all")
				.header("test", "testgetheader")
				)
		.andExpect(status().isOk())
		//.andExpect(content().string(containsString("name")))
		.andExpect(jsonPath("$.headers.test", containsString("testgetheader")))
		;

	}


}
