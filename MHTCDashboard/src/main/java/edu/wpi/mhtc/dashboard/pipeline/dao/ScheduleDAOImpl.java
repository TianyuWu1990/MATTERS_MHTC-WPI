package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;

public class ScheduleDAOImpl implements ScheduleDAO {
	
	@Autowired private JdbcTemplate jdbcTemplate;
	
	public ScheduleDAOImpl() {}

	@Override
	public void save(Schedule schedule) {
		String sql = "INSERT INTO mhtc_sch.schedules(\"job_name\", \"sched_name\", \"sched_job\", \"sched_date\", \"sched_description\", \"sched_cron\") "
				+ "VALUES (?, ?, ?, ?, ?, ?) ";
		
		jdbcTemplate.update(sql, schedule.getJob_name(), schedule.getSched_name(), schedule.getSched_job(), 
				schedule.getSched_date(), schedule.getSched_description(), schedule.isSched_cron());
	}

	@Override
	public void delete(String job_name) {
		String sql = "DELETE FROM mhtc_sch.schedules WHERE job_name = ?";
		jdbcTemplate.update(sql, job_name);
	}

	@Override
	public void update(Schedule schedule) {
		// TODO Auto-generated method stub

	}

	@Override
	public Schedule get(String job_name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Schedule> getAll() {
		String sql = "SELECT * FROM mhtc_sch.schedules";
		
		List<Schedule> schedules = jdbcTemplate.query(sql, new RowMapper<Schedule>() {

			@Override
			public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Schedule(rs.getString("job_name"), rs.getString("sched_name"), rs.getString("sched_job"), 
						rs.getString("sched_description"), rs.getString("sched_date"), rs.getBoolean("sched_cron"));
			}
			
		});
		
		return schedules;
	}

}
