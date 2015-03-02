package edu.wpi.mhtc.dashboard.pipeline.dao;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.wpi.mhtc.dashboard.pipeline.scheduler.Schedule;

@Repository
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
	public String getSchedStatus(String sched_date) throws ParseException {
		Date today = new Date();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.ENGLISH);
		Date runDate = format.parse(sched_date);
		
		if (today.after(runDate)) {
			// Now, check the Logger to see the current status
			String sql = "SELECT * FROM mhtc_sch.logs WHERE message = 'Pipeline has finished' AND job = ?";
			
			Object[] args = {sched_date};
			
			List<String> moments = jdbcTemplate.query(sql, args, new RowMapper<String>() {

				@Override
				public String mapRow(ResultSet rs, int rowNum) throws SQLException {
					return rs.getString("moment");
				}
				
			});
			
			for (String moment : moments) {
				DateFormat finishDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
				Date finishDate = finishDateFormat.parse(moment);
				long timediff = finishDate.getTime() - runDate.getTime();
					
				if (timediff <= 604800) { // Check if the difference between finish date and execution date is less than 1 week 
					return "Completed"; 
				}
			}
			
			return "Running..."; 
		} else {
			return "Scheduled to run at " + sched_date;
		}	
	}

	@Override
	public List<Schedule> getAll() throws ParseException {
		String sql = "SELECT * FROM mhtc_sch.schedules";
		
		List<Schedule> schedules = jdbcTemplate.query(sql, new RowMapper<Schedule>() {

			@Override
			public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
				return new Schedule(rs.getString("job_name"), rs.getString("sched_name"), rs.getString("sched_job"), 
						rs.getString("sched_description"), rs.getString("sched_date"), rs.getBoolean("sched_cron"));
			}
			
		});
		
		for (Schedule s: schedules) {
			String status = getSchedStatus(s.getSched_date());
			s.setStatus(status);

			String filename = getTalendJob(s.getSched_job());
			s.setFilename(filename);
		}
		
		return schedules;
	}
	
	@Override
	public String getTalendJob(String sched_job) {
		String sql = "SELECT * FROM mhtc_sch.pipelines WHERE pipelinename = ?";
		
		Object[] args = {sched_job};
		
		String path = jdbcTemplate.query(sql, args, new ResultSetExtractor<String>() {

			@Override
			public String extractData(ResultSet rs) throws SQLException, DataAccessException {
				if (rs.next()) {
					return rs.getString("path");
				}
				return null;
			}
			
		});
		
		File file = new File(path);
		String filename = file.getName();
		return filename.substring(0,filename.lastIndexOf('.')-4);		

	}


}
