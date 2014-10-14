package pipeline.db;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import edu.wpi.mhtc.pipeline.config.StateInfoConfig;
import edu.wpi.mhtc.pipeline.db.DBLoader;

public class TestDBLoader {

	@Test
	public void testDBStateLoader() throws Exception {
		StateInfoConfig.getInstance();
		assertEquals(StateInfoConfig.getInstance().getStateByFullName("alabama").getStateID(), "1");
		assertEquals(StateInfoConfig.getInstance().getStateByFullName("new york").getStateID(), "32");
	}
	
	@Test
	public void testDBMetricLoader() throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map = DBLoader.getMetricInfo("3");
		
		assertEquals("8", map.get("alcoholic beverage lic"));
		assertEquals("9", map.get("alcoholic beverage tax"));
		assertEquals("10", map.get("amusement license"));
		assertEquals("60", map.get("total taxes"));
		
		map = DBLoader.getMetricInfo("2");
		assertEquals(map.get("tot_emp"), "7");
	}

}
