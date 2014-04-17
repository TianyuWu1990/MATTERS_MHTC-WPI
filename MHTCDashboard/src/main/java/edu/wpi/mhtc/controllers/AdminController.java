package edu.wpi.mhtc.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
    
    @RequestMapping(value = "/admin/metrictable", method=RequestMethod.GET, params = { "category" })
    public String metricTable(Model model, @RequestParam("category") int categoryId) throws ParseException, JsonGenerationException, JsonMappingException, IOException {
        
        model.addAttribute("metrics", new ObjectMapper().writeValueAsString(service.getMetricsForCategory(categoryId)));
        
        return "admin_metrics_table";
    }
    
}
