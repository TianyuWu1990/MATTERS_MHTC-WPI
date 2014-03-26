package edu.wpi.mhtc.service;

import java.util.List;

public interface PeersService {

	public List<String> getPeersByInitials();
	public List<String> getPeersByFullName();
}
