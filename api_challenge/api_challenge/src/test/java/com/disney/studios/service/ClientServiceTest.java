package com.disney.studios.service;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.disney.studios.TestConfig;
import com.disney.studios.domain.VoteRequest;
import com.disney.studios.exception.BusinessException;

@RunWith(SpringRunner.class)
@ContextConfiguration (classes = TestConfig.class)
@DataJpaTest
public class ClientServiceTest {

	@Autowired
	private ClientService clientService;
		
	@Test
	public void testVote_success() {
		
		clientService.addClient("user1");
		VoteRequest v = new VoteRequest();
		v.setClientId(0);
		v.setPictureId(1);
		v.setMark(1);
		clientService.vote(v);
		
	}

	
	@Test(expected = BusinessException.class)
	public void testVote_noClient_failure() {
		
		VoteRequest v = new VoteRequest();
		v.setClientId(10);
		v.setPictureId(1);
		v.setMark(1);
		clientService.vote(v);
	}
	
	@Test(expected = BusinessException.class)
	public void testVotingTwice_failure() {
		
		clientService.addClient("user1");
		VoteRequest v = new VoteRequest();
		v.setClientId(0);
		v.setPictureId(1);
		v.setMark(1);
		
		try {
			clientService.vote(v);
		} catch (BusinessException e) {
			fail("failed on voting once");
		}
		
		//vote twice same mark
		clientService.vote(v);
		
	}
	
	@Test
	public void testVotingTwiceDifferentMark_success() {
		
		clientService.addClient("user1");
		VoteRequest v = new VoteRequest();
		v.setClientId(0);
		v.setPictureId(1);
		v.setMark(1);
		
		clientService.vote(v);
		
		v.setMark(-1);
		//vote twice different mark
		clientService.vote(v);
		
	}
}
