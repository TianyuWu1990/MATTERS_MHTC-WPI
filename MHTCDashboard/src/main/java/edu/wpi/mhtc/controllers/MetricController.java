package edu.wpi.mhtc.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.wpi.mhtc.model.Data.Metric;
import edu.wpi.mhtc.service.MetricService;

/**
 * A controller that provides endpoints that returns metrics or lists of metrics.
 * 
 * @author Stokes
 */
@Controller
public class MetricController {

    private MetricService service;
    
    @Autowired
    public MetricController(MetricService service) {
        this.service = service;
    }
    
    /**
     * Returns a list of all metrics supported by the system.
     */
    @RequestMapping(value = "/data/metrics/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Metric> allMetricsEndpoint() {
        return service.getAllVisibleMetrics();
    }
}
