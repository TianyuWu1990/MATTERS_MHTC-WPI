package edu.wpi.mhtc.model.state;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.rson.RSON;
import edu.wpi.mhtc.service.PeersService;

@Component
public class PeerStates {
	private LinkedList<State> states;

	@Autowired
	public PeerStates(PeersService service) {
		states = (LinkedList<State>) service.getPeers();
	}

	public LinkedList<HashMap<String, LinkedList<State>>> getAsGrid(int rows) throws ParseException {
		@SuppressWarnings("unchecked")
        LinkedList<State> queue = (LinkedList<State>) states.clone();
		LinkedList<HashMap<String, LinkedList<State>>> result = new LinkedList<HashMap<String, LinkedList<State>>>();
		for (int i = 0; i < rows; i++) {
			LinkedList<State> partial = new LinkedList<State>();
			for (int j = 0; j < states.size() / rows; j++) {
				partial.add(queue.pop());
			}
			HashMap<String, LinkedList<State>> tem = new HashMap<String, LinkedList<State>>();
			tem.put("row", partial);
			result.add(tem);
		}

		return result;
	}

}
