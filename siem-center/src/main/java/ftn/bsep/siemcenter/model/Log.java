package ftn.bsep.siemcenter.model;

import java.time.LocalDateTime;
import java.util.Date;

import org.kie.api.definition.type.Expires;
import org.kie.api.definition.type.Role;
import org.kie.api.definition.type.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ftn.bsep.siemcenter.helpers.LogHelper;
import ftn.bsep.siemcenter.helpers.SeverityLevel;

import ftn.bsep.siemcenter.helpers.LogHelper;
import ftn.bsep.siemcenter.helpers.SeverityLevel;

@Role(Role.Type.EVENT)
@Timestamp("executionTime")
@Expires("2h30m")
@Document(collection = "logs")
public class Log {

	@Id
	private String id;
	private String timestamp;
	private String level;
	private String eventId;
	private String computerName;
	private String sourceName;
	private String message;

    private Date executionTime;
	
	public Log() {
		
	}

	public Log(String timestamp, String level, String eventId, String computerName, String sourceName,
			String message) {
		super();
		this.timestamp = timestamp;
		this.level = level;
		this.eventId = eventId;
		this.computerName = computerName;
		this.sourceName = sourceName;
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

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getComputerName() {
		return computerName;
	}

	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Log [timestamp=" + timestamp + ", level=" + level + ", eventId=" + eventId + ", computerName="
				+ computerName + ", sourceName=" + sourceName + ", message=" + message + "]";
	}

	public Date getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(Date executionTime) {
		this.executionTime = executionTime;
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
