package edu.wpi.mhtc.persistence;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.service.PeersService;

@Component
public class StateMapper {

	private List<DBState> states;

	@Autowired
	public StateMapper(PeersService service) {

		states = service.getPeersFull();
	}

	public DBState getStateByID(int id) {

		for (DBState state : states) {
			if (state.getId() == id) {
				return state;
			}
		}

		return null;
	}

	public DBState getStateByName(String name) {

		for (DBState state : states) {
			if (state.getName().equals(name)) {
				return state;
			}
		}

		return null;
	}

	public DBState getStateByAbbreviation(String abbreviation) {

		for (DBState state : states) {
			if (state.getInitial().equals(abbreviation)) {
				return state;
			}
		}

		return null;
	}
	
	public DBState getStateFromString(String state) {
		DBState dbState = this.getStateByName(state);
		if (dbState == null)
			dbState = this.getStateByAbbreviation(state);
		if (dbState == null)
			dbState = this.getStateByID(Integer.parseInt(state));
		
		return dbState;
	}
	
	public List<DBState> getAllStates() {
		return Collections.unmodifiableList(states);
	}
}
