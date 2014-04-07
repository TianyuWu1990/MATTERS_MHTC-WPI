package edu.wpi.mhtc.service;

import java.util.List;

import edu.wpi.mhtc.model.StateMeta;

public interface PeersService {

	public List<String> getPeersByInitials();
	public List<StateMeta> getPeersByFullName();
}
