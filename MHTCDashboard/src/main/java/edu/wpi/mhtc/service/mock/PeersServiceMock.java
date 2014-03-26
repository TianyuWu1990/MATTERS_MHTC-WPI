package edu.wpi.mhtc.service.mock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import edu.wpi.mhtc.service.PeersService;

@Service
public class PeersServiceMock implements PeersService {

	@Override
	public List<String> getPeersByInitials() {
		List<String> peers = new ArrayList<String>();
		peers.add("CA");
		peers.add("CO");
		peers.add("CT");
		peers.add("GA");
		peers.add("MA");
		peers.add("MD");
		peers.add("MN");
		peers.add("NC");
		peers.add("NH");
		peers.add("NY");
		peers.add("PA");
		peers.add("TX");
		peers.add("UT");
		peers.add("VA");
		peers.add("WA");
		
		return peers;
	}

	@Override
	public List<String> getPeersByFullName() {
		List<String> peers = new ArrayList<String>();
		peers.add("California");
		peers.add("Colorado");
		peers.add("Connecticut");
		peers.add("Georgia");
		peers.add("Maryland");
		peers.add("Massachusetts");
		peers.add("Minnesota");
		peers.add("New Hampshire");
		peers.add("New York");
		peers.add("North Carolina");
		peers.add("Pennsylvania");
		peers.add("Texas");
		peers.add("Utah");
		peers.add("Virginia");
		peers.add("Washington");
		
		return peers;
	}

}
