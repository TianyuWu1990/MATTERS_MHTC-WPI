package edu.wpi.mhtc.persistence;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

import org.mockito.Mockito;

import edu.wpi.mhtc.service.MetricsService;

public class MetricMapperTest {

    List<DBMetric> testMetrics;
    
    MetricsService mockService;
    
    MetricMapper mapper;
    

    @BeforeClass
    public void classSetup() {
        initMetrics();

        mockService = Mockito.mock(MetricsService.class);
        Mockito.when(mockService.getAvailibleStatistics()).thenReturn(testMetrics);
    }

    @Before
    public void testSetup() {
        mapper = new MetricMapper(mockService);
    }

    private void initMetrics() {
        testMetrics = new ArrayList<DBMetric>();
        
        testMetrics.add(new DBMetric(1, "one", ""));
        testMetrics.add(new DBMetric(2, "two", ""));
        testMetrics.add(new DBMetric(3, "three", ""));
        testMetrics.add(new DBMetric(4, "four", ""));
    }

    public void testSearchForInvalidIdGivesNull() {
        assertEquals(null, mapper.getMetricByID(0));
    }
    
    public void testSearchForValidIdGivesMetric() {
        assertEquals(2, mapper.getMetricByID(2).getId());
    }
    
    public void testSearchForInvlidNameGivesNull() {
        assertEquals(null, mapper.getMetricByName("zero"));
    }
    
    public void testSearchForValidNameGivesMetric() {
        assertEquals(2, mapper.getMetricByName("two").getId());
    }

}
