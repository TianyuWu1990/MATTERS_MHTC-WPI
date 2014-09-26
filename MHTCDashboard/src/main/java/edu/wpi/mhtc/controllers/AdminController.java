package edu.wpi.mhtc.controllers;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.persistence.PSqlRowMapper;

import edu.wpi.mhtc.persistence.PSqlStringMappedJdbcCall;
//import edu.wpi.mhtc.persistence.JdbcProcedure;
import edu.wpi.mhtc.service.MetricService;
import edu.wpi.mhtc.dashboard.pipeline.db.DBLoader;
import edu.wpi.mhtc.dashboard.pipeline.main.DataPipeline;

@Controller
public class AdminController {

    private DateFormat fileDateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    
    private MetricService service;
    private JdbcTemplate template;
    
    @Autowired
    public AdminController(MetricService service, JdbcTemplate template) {
        this.service = service;
        this.template = template;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Locale locale, Model model) throws Exception {
        Map<String, String> categories = DBLoader.getCategoryInfo();
        
        model.addAttribute("categories", categories);
        
        return "admin_tool";
    }
    
    @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
    public @ResponseBody List<Metric> categories() {
        //List<TreeViewNode> returnList = new LinkedList<TreeViewNode>();
        //returnList.add(service.getCategoriesAsTree());
        return null;
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/metrics/table")
    public String metricTable(Model model, @PathVariable("categoryid") int categoryId) {
        
        //model.addAttribute("jv_metrics", service.getMetricsForCategory(categoryId));
        
        return "admin_metrics_table";
    }
    
    @RequestMapping(value = "/admin/categories/new", method=RequestMethod.POST, params = { "parentid", "name", "source"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addCategory(@RequestParam("parentid") int parentId, @RequestParam("name") String name, @RequestParam("source") String source) {
        
        //service.storeCategory(name, parentId, source);
        
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/update", method=RequestMethod.POST, params = { "name", "visible", "source"})
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCategory(Model model, @PathVariable("categoryid") int categoryId, @RequestParam("visible") boolean visible, @RequestParam("name") String name, @RequestParam("source") String source) {

        //service.updateCategory(categoryId, name, visible, source);
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/metrics/new", method=RequestMethod.POST, params = { "name", "iscalculated", "type"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addMetric(@PathVariable("categoryid") int categoryId, @RequestParam("name") String name, @RequestParam("iscalculated") boolean isCalculated, @RequestParam("type") String type) {
        
        //service.storeMetric(categoryId, name, isCalculated, type);
        
    }
    
    @RequestMapping(value = "/admin/metrics/{metricid}/update", method=RequestMethod.POST, params = { "name", "visible", "iscalculated", "type"})
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMetric(@PathVariable("metricid") int metricId, @RequestParam("name") String name, @RequestParam("visible") boolean visible, @RequestParam("iscalculated") boolean isCalculated, @RequestParam("type") String type) {
        
        //service.updateMetric(metricId, name, visible, isCalculated, type);
        
    }
    
    @RequestMapping(value = "/admin/upload/add", method=RequestMethod.POST)
    public @ResponseBody String uploadAddFile(@RequestParam("file") MultipartFile file, @RequestParam("category") String categoryID) {
        String name = "Upload - " + fileDateFormat.format(new Date()) + ".xlsx";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(name));
                stream.write(bytes);
                stream.close();
                
                BufferedReader br = new BufferedReader(new FileReader(name));
                String category = br.readLine().split(",")[0];
                br.close();
                
                DataPipeline.run(new File(name), "" + getCatId(category));
                
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                throw new RuntimeException(e);
                //return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
        
    }
    
    
    private int getCatId(String catname) {
    	PSqlStringMappedJdbcCall <Integer> call = new PSqlStringMappedJdbcCall<Integer>(template).withSchemaName(
                "mhtc_sch").withProcedureName("getcategorybyname");

        call.addDeclaredRowMapper(new PSqlRowMapper<Integer>() {

            @Override
            public Integer mapRow(SqlRowSet rs, int rowNum) throws SQLException {
                return rs.getInt(1);
            }

        });

        call.addDeclaredParameter(new SqlParameter("inname", Types.VARCHAR));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("inname", catname);

        // TODO temp while testing
        return 2;
        //return call.execute(params).get(0);
    }
    
}
