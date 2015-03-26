/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.dao.dashboard;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.dashboard.Metric;
import edu.wpi.mhtc.model.dashboard.State;
import edu.wpi.mhtc.service.dashboard.StateService;
import edu.wpi.mhtc.util.persistence.MetricMapper;
import edu.wpi.mhtc.util.persistence.PSqlRowMapper;
import edu.wpi.mhtc.util.persistence.PSqlStringMappedJdbcCall;

@Service
public class StateServiceJDBC implements StateService {

    private JdbcTemplate template;
    private MetricMapper metricMapper;

    @Autowired
    public StateServiceJDBC(JdbcTemplate template, MetricMapper metricMapper) {
        this.template = template;
        this.metricMapper = metricMapper;
    }

    @Override
    public List<State> getAllPeers() {

        PSqlStringMappedJdbcCall<State> call = new PSqlStringMappedJdbcCall<State>(template).withSchemaName("mhtc_sch")
                .withProcedureName("getstates");

        call.addDeclaredRowMapper(new PSqlRowMapper<State>() {

            @Override
            public State mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new State(rs.getInt("Id"), rs.getString("Name"), rs.getString("Abbreviation"), true);
            }
            
        });

        call.addDeclaredParameter(new SqlParameter("showonlypeerstates", Types.BOOLEAN));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("showonlypeerstates", true);

        return call.execute(params);

    }

    @Override
    public List<State> getPeersFull() {

        PSqlStringMappedJdbcCall<State> call = new PSqlStringMappedJdbcCall<State>(template).withSchemaName(
                "mhtc_sch").withProcedureName("getstates");

        call.addDeclaredRowMapper(new PSqlRowMapper<State>() {

            @Override
            public State mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new State(rs.getInt("Id"), rs.getString("Name"), rs.getString("Abbreviation"), rs
                        .getBoolean("IsPeerState"));
            }

        });

        call.addDeclaredParameter(new SqlParameter("showonlypeerstates", Types.BOOLEAN));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("showonlypeerstates", false);

        return call.execute(params);

    }

    @Override
    public List<State> getTopTenPeersForMetric(String metric, int year) {
        return getTenStates(metric, year, "gettoptenstates_maxyear");
    }

    @Override
    public List<State> getBottomTenPeersForMetric(String metric, int year) {
        return getTenStates(metric, year, "getbottomtenstates_maxyear");

    }

    private List<State> getTenStates(String metric, int year, String operation) {
        Metric dbMetric = metricMapper.getMetricFromString(metric);

        PSqlStringMappedJdbcCall<State> call = new PSqlStringMappedJdbcCall<State>(template).withSchemaName(
                "mhtc_sch").withProcedureName(operation);

        call.addDeclaredRowMapper(new PSqlRowMapper<State>() {

            @Override
            public State mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new State(rs.getInt("StateId"), rs.getString("StateName"), rs.getString("Abbreviation"), true);
            }

        });

        call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));
        //call.addDeclaredParameter(new SqlParameter("compareyear", Types.INTEGER));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("metricid", dbMetric.getId());
        
        //params.put("compareyear", year);

        return call.execute(params);
    }

    @Override
    public List<State> getAllStates() {
        PSqlStringMappedJdbcCall<State> call = new PSqlStringMappedJdbcCall<State>(template).withSchemaName("mhtc_sch")
                .withProcedureName("getstates");

        call.addDeclaredRowMapper(new PSqlRowMapper<State>() {

            @Override
            public State mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new State(rs.getInt("Id"), rs.getString("Name"), rs.getString("Abbreviation"), rs.getBoolean("IsPeerState"));
            }
            
        });

        call.addDeclaredParameter(new SqlParameter("showonlypeerstates", Types.BOOLEAN));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("showonlypeerstates", false);

        return call.execute(params);
    }

}
