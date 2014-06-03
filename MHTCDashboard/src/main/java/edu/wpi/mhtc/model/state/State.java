package edu.wpi.mhtc.model.state;

import java.sql.Types;

import edu.wpi.mhtc.model.persistence.Query;
import edu.wpi.mhtc.model.persistence.QueryProperty;
import edu.wpi.mhtc.model.persistence.Queryable;
import edu.wpi.mhtc.model.persistence.queries.NoParamsQuery;
import edu.wpi.mhtc.persistence.DBState;

@Queryable
public class State
{
    @QueryProperty(name = "StateId", type = Types.INTEGER)
    private int id;
    @QueryProperty(name = "StateName", type = Types.VARCHAR)
	private String name;
    @QueryProperty(name = "StateAbbreviation", type = Types.VARCHAR)
	private String abbr;
    @QueryProperty(name = "StateIsPeerState", type = Types.BOOLEAN)
	private boolean peerState;

    public State() { };
    
	public State(int id, String name, String abbr, boolean peerState)
	{
		this.id = id;
	    this.name = name;
		this.abbr = abbr;
		this.peerState = peerState;
	}

	public State(DBState state)
	{
		this.id = state.getId();
	    this.name = state.getName();
		this.abbr = state.getInitial();
		this.peerState = state.isIspeer();
	}

	public int getId() {
	    return id;
	}

	public String getName() {
		return name;
	}

	public String getAbbr() {
		return abbr;
	}
	
	public boolean isPeerState() {
	    return peerState;
	}
	
	public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public void setPeerState(boolean peerState) {
        this.peerState = peerState;
    }

    public static Query<State> queryForAll = new NoParamsQuery<State>().withName("getallstates").withReturnType(State.class);
}