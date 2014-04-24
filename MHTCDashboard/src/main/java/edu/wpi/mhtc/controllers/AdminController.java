package edu.wpi.mhtc.controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import edu.wpi.mhtc.model.admin.TreeViewNode;
import edu.wpi.mhtc.rson.ParseException;
import edu.wpi.mhtc.service.MetricsService;

@Controller
public class AdminController {

    private MetricsService service;
    
    @Autowired
    public AdminController(MetricsService service) {
        this.service = service;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String admin(Locale locale, Model model) throws ParseException {
        
        return "admin_tool";
    }
    
    @RequestMapping(value = "/admin/categories", method = RequestMethod.GET)
    public @ResponseBody List<TreeViewNode> categories() throws ParseException {
        List<TreeViewNode> returnList = new LinkedList<TreeViewNode>();
        returnList.add(service.getCategoriesAsTree());
        return returnList;
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/metrics/table")
    public String metricTable(Model model, @PathVariable("categoryid") int categoryId) throws ParseException, JsonGenerationException, JsonMappingException, IOException {
        
        model.addAttribute("jv_metrics", new ObjectMapper().writeValueAsString(service.getMetricsForCategory(categoryId)));
        
        return "admin_metrics_table";
    }
    
    @RequestMapping(value = "/admin/categories/new", method=RequestMethod.POST, params = { "parentid", "name", "source"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addCategory(@RequestParam("parentid") int parentId, @RequestParam("name") String name, @RequestParam("source") String source) {
        
        service.storeCategory(name, parentId, source);
        
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/update", method=RequestMethod.POST, params = { "name", "visible", "source"})
    @ResponseStatus(value = HttpStatus.OK)
    public void updateCategory(Model model, @PathVariable("categoryid") int categoryId, @RequestParam("visible") boolean visible, @RequestParam("name") String name, @RequestParam("source") String source) throws ParseException, JsonGenerationException, JsonMappingException, IOException {

        service.updateCategory(categoryId, name, visible, source);
    }
    
    @RequestMapping(value = "/admin/categories/{categoryid}/metrics/new", method=RequestMethod.POST, params = { "name", "iscalculated", "type"})
    @ResponseStatus(value = HttpStatus.OK)
    public void addMetric(@PathVariable("categoryid") int categoryId, @RequestParam("name") String name, @RequestParam("iscalculated") boolean isCalculated, @RequestParam("type") String type) {
        
        service.storeMetric(categoryId, name, isCalculated, type);
        
    }
    
    @RequestMapping(value = "/admin/metrics/{metricid}/update", method=RequestMethod.POST, params = { "name", "visible", "iscalculated", "type"})
    @ResponseStatus(value = HttpStatus.OK)
    public void updateMetric(@PathVariable("metricid") int metricId, @RequestParam("name") String name, @RequestParam("visible") boolean visible, @RequestParam("iscalculated") boolean isCalculated, @RequestParam("type") String type) {
        
        service.updateMetric(metricId, name, visible, isCalculated, type);
        
    }
    
    @RequestMapping(value = "/admin/upload/add", method=RequestMethod.POST)
    public @ResponseBody String uploadAddFile(@RequestParam("file") MultipartFile file) {
        String name = "filename";
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File(name + "-uploaded")));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + " into " + name + "-uploaded !";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
        
    }
    
}
