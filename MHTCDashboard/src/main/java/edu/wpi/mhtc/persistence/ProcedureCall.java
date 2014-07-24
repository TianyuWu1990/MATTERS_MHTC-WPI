package edu.wpi.mhtc.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcedureCall<T>
{

	private JdbcProcedure<T> procedure;
	private Map<String, Object> params;
	
	public ProcedureCall(JdbcProcedure<T> procedure) {
		this.procedure = procedure;
		this.params = new HashMap<String, Object>();
	}
	
	public ProcedureCall<T> withParam(String paramName, Object paramValue) {
		this.params.put(paramName, paramValue);
		return this;
	}
	
	public List<T> call() {
		return this.procedure.execute(params);
	}
}
