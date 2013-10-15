package com.intuit.psd.risk.risk2.configmgr;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import com.intuit.psd.risk.risk2.configmgr.interfaces.ConfigMgr;

public class ConfigMgrImpl implements ConfigMgr {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
    	ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "ApplicationContext.xml" });
    	ConfigMgrImpl cfgMgr = (ConfigMgrImpl)context.getBean("configMgrImpl");
    	String nBolts = cfgMgr.get("STorm_risk_events_no_bolts ");
    	System.out.println("No of Bollts:" + nBolts);
    	//cfgMgr.put("Test_Key", "Test_Val");
	}

	public String get(String key) {
		String sql = "SELECT VALUE FROM RISK_2_CONF WHERE LOWER(KEY) = ?";
		 
		String value = (String)getJdbcTemplate().queryForObject(
				sql, new Object[] { key.toLowerCase() }, String.class);
		return value;
	}

	public void put(String key, String val) {
		String sql = "INSERT INTO RISK_2_CONF VALUES (?, ?)";
		jdbcTemplate.update(sql, new Object[] { key, val } );
	}

}
