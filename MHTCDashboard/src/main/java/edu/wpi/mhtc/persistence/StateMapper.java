package edu.wpi.mhtc.persistence;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.model.state.State;
import edu.wpi.mhtc.service.PeersService;

@Component
public class StateMapper {

	private List<State> states;

	@Autowired
	public StateMapper(PeersService service) {

		states = service.getAllPeers();
	}

	public State getStateByID(int id) {

		for (State state : states) {
			if (state.getId() == id) {
				return state;
			}
		}

		return null;
	}

	public State getStateByName(String name) {

		for (State state : states) {
			if (state.getName().equals(name)) {
				return state;
			}
		}

		return null;
	}

	public State getStateByAbbreviation(String abbreviation) {

		for (State state : states) {
			if (state.getAbbr().equals(abbreviation)) {
				return state;
			}
		}

		return null;
	}
	
	public State getStateFromString(String state) {
	    State dbState = this.getStateByName(state);
		if (dbState == null)
			dbState = this.getStateByAbbreviation(state);
		if (dbState == null)
			dbState = this.getStateByID(Integer.parseInt(state));
		
		return dbState;
	}
	
	public List<State> getAllStates() {
		return Collections.unmodifiableList(states);
	}
}
