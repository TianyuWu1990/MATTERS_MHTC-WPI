/*
 *  Copyright (C) 2013 Worcester Polytechnic Institute 
 *  All Rights Reserved.
 */
package edu.wpi.mhtc.service;

import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.model.admin.AdminMetric;
import edu.wpi.mhtc.model.admin.TreeViewNode;
import edu.wpi.mhtc.persistence.PSqlRowMapper;
import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;

@Service
public class MetricServiceJdbc implements MetricService {

    private JdbcTemplate template;
    final int RANK = 21;
    final int TALENT = 20;
    final int COST = 37;
    final int ECONOMY = 29;
    final int PROFILE = 45; 
    
    
    @Autowired
    public MetricServiceJdbc(JdbcTemplate template) {
        this.template = template;

    }
  

    /**
     * Retrieves all metrics under parent category ids. 
     * @param parentIds must correspond to ParentId in categories table.
     * @throws SQLException
     */
    @Override
    public List<Metric> getMetricsFromParents(final Integer... parentIds) {
    	
        PSqlStringMappedJdbcCall<Metric> metricCall = new PSqlStringMappedJdbcCall<Metric>(template)
                .withSchemaName("mhtc_sch").withProcedureName("getmetricsbyparent");

        metricCall.addDeclaredRowMapper(new PSqlRowMapper<Metric>() {

            @Override
            public Metric mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                
                String name = null;
                int categoryId = rs.getInt("ParentId");
                if (categoryId == 21) {
                    name = "National";
                } else if (categoryId == 20) {
                    name = "Talent";
                } else if (categoryId == 37) {
                    name = "Cost";
                } else if (categoryId == 29) {
                    name = "Economy";
                }else if (categoryId == 45) {
                    name = "Profile";
                }
                
                final String categoryName = name;
                		
                // trendtypes no longer used
                return new Metric(rs.getInt("Id"), rs.getString("Name"), categoryId, categoryName, 
                		rs.getString("DataType"), "", rs.getString("URL"), rs.getString("Source"),rs.getString("DisplayName"));
            }

        });

        metricCall.addDeclaredParameter(new SqlParameter("parentids", Types.ARRAY));
       
        Map<String, Object> metricParams = new HashMap<String, Object>();
        metricParams.put("parentids", parentIds);

        return metricCall.execute(metricParams);

    }
    
    @Override
    public TreeViewNode getCategoriesAsTree() {
        PSqlStringMappedJdbcCall<TreeViewNode> call = new PSqlStringMappedJdbcCall<TreeViewNode>(template)
                .withSchemaName("mhtc_sch").withProcedureName("getcategories");

        call.addDeclaredRowMapper(new PSqlRowMapper<TreeViewNode>() {

            @Override
            public TreeViewNode mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new TreeViewNode(rs.getString("Name"), rs.getInt("Id"));
            }

        });

        call.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("parentid", Types.INTEGER));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("showall", true);
        params.put("parentid", null);

        List<TreeViewNode> categories = call.execute(params);
        List<TreeViewNode> nodesToExpand = new LinkedList<TreeViewNode>();
        nodesToExpand.addAll(categories);

        while (nodesToExpand.size() > 0) {

            TreeViewNode next = nodesToExpand.get(0);

            Map<String, Object> subParams = new HashMap<String, Object>();
            subParams.put("showall", true);
            subParams.put("parentid", next.getId());

            List<TreeViewNode> subCategories = call.execute(subParams);
            for (TreeViewNode node : subCategories) {
                next.addChild(node);
                nodesToExpand.add(node);
            }

            nodesToExpand.remove(0);
        }

        return new TreeViewNode("All Categories", -1, categories);

    }

    @Override
    public List<AdminMetric> getMetricsForCategory(int categoryID) {
        PSqlStringMappedJdbcCall<AdminMetric> metricCall = new PSqlStringMappedJdbcCall<AdminMetric>(template)
                .withSchemaName("mhtc_sch").withProcedureName("getmetrics");

        metricCall.addDeclaredRowMapper(new PSqlRowMapper<AdminMetric>() {

            @Override
            public AdminMetric mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return new AdminMetric(rs.getInt("Id"), rs.getString("Name"), rs.getString("DataType"), rs
                        .getBoolean("Visible"), rs.getBoolean("IsCalculated"));
            }

        });

        metricCall.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
        metricCall.addDeclaredParameter(new SqlParameter("showall", Types.BOOLEAN));

        Map<String, Object> metricParams = new HashMap<String, Object>();
        metricParams.put("categoryid", categoryID);
        metricParams.put("showall", true);

        return metricCall.execute(metricParams);

    }
    
    

    @Override
    public void storeCategory(String name, int parentId, String source) {
        PSqlStringMappedJdbcCall<Integer> call = new PSqlStringMappedJdbcCall<Integer>(template).withSchemaName(
                "mhtc_sch").withProcedureName("insertcategory");

        call.addDeclaredParameter(new SqlParameter("categname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("parentid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("source", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("categname", name);
        if (parentId == -1) {
            params.put("parentid", null);
        } else {
            params.put("parentid", parentId);
        }

        params.put("source", source);

        call.execute(params);
    }

    @Override
    public void updateCategory(int categoryId, String name, boolean visible, String source) {
        PSqlStringMappedJdbcCall<Integer> call = new PSqlStringMappedJdbcCall<Integer>(template).withSchemaName(
                "mhtc_sch").withProcedureName("updatecategory");

        call.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("cname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("visible", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("source", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("categoryid", categoryId);
        params.put("cname", name);
        params.put("visible", visible);
        params.put("source", source);

        call.execute(params);
    }

    @Override
    public void storeMetric(int categoryId, String name, boolean isCalculated, String type) {
        if (categoryId <= 0) {
            return;
        }
        PSqlStringMappedJdbcCall<Integer> call = new PSqlStringMappedJdbcCall<Integer>(template).withSchemaName(
                "mhtc_sch").withProcedureName("insertmetric");

        call.addDeclaredParameter(new SqlParameter("metricname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("iscaclucated", Types.BOOLEAN)); // TODO
                                                                                    // update
                                                                                    // misnamed
                                                                                    // variable
                                                                                    // on
                                                                                    // db
        call.addDeclaredParameter(new SqlParameter("categoryid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("datatype", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("metricname", name);
        params.put("iscaclucated", isCalculated);
        params.put("categoryid", categoryId);
        params.put("datatype", type);

        call.execute(params);
    }

    @Override
    public void updateMetric(int metricId, String name, boolean visible, boolean isCalculated, String type) {

        PSqlStringMappedJdbcCall<Integer> call = new PSqlStringMappedJdbcCall<Integer>(template).withSchemaName(
                "mhtc_sch").withProcedureName("updatemetric");

        call.addDeclaredParameter(new SqlParameter("metricid", Types.INTEGER));
        call.addDeclaredParameter(new SqlParameter("mname", Types.VARCHAR));
        call.addDeclaredParameter(new SqlParameter("visible", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("iscalculated", Types.BOOLEAN));
        call.addDeclaredParameter(new SqlParameter("datatype", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("metricid", metricId);
        params.put("mname", name);
        params.put("visible", visible);
        params.put("iscalculated", isCalculated);
        params.put("datatype", type);

        call.execute(params);
    }

    @Override
    public List<Metric> getAllMetrics() {

        return getMetricsFromParents(TALENT,COST,RANK,ECONOMY);
    }
}
