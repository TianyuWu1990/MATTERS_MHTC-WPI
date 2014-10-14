package pipeline.db;

import org.junit.Test;

import edu.wpi.mhtc.pipeline.data.Line;
import edu.wpi.mhtc.pipeline.data.Metric;
import edu.wpi.mhtc.pipeline.db.DBSaver;

public class TestDBSaver {
	
	@Test
	public void testDBSaver() throws Exception {
		
//		category 3, metric "severance tax", metric id 51
		Line line = new Line();
		line.setState("Alabama");
		line.setYear("2020");							//need new year or will violate key constraints
		Metric m = new Metric("severance tax", 51);
		m.setValue(99999.0f);
		line.addMetric(m);
		
		
		DBSaver.saveLine(line);
	}

}
