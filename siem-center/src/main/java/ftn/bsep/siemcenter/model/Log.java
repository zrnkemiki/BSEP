package ftn.bsep.siemcenter.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ftn.bsep.siemcenter.helpers.LogHelper;
import ftn.bsep.siemcenter.helpers.SeverityLevel;

@Document(collection = "logs")
public class Log {

	@Id
	private String id;
	private String timestamp;
	private String level;
	private String event_id;
	private String computer_name;
	private String source_name;
	private String message;
	
	public Log() {
		
	}

	public Log(String timestamp, String level, String event_id, String computer_name, String source_name,
			String message) {
		super();
		this.timestamp = timestamp;
		this.level = level;
		this.event_id = event_id;
		this.computer_name = computer_name;
		this.source_name = source_name;
		this.message = message;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getEvent_id() {
		return event_id;
	}

	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}

	public String getComputer_name() {
		return computer_name;
	}

	public void setComputer_name(String computer_name) {
		this.computer_name = computer_name;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Log [timestamp=" + timestamp + ", level=" + level + ", event_id=" + event_id + ", computer_name="
				+ computer_name + ", source_name=" + source_name + ", message=" + message + "]";
	}
	
	public LocalDateTime getTimestampLDT() {
		return LocalDateTime.parse(this.timestamp, LogHelper.FORMATTER);
	}
	
	public SeverityLevel getSevirityLevel() {
		return SeverityLevel.valueOf(this.level);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
	
}
