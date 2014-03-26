package edu.wpi.mhtc.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

public class HeadlinesServiceJDBC implements HeadlinesService {

	private JdbcTemplate template;

	public HeadlinesServiceJDBC(JdbcTemplate template) {
		this.template = template;

	}
}
