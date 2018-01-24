package com.cloudstudy.dto;

public class SystemLogQueryDto {

	private String fromTime;

	private String toTime;

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SystemLogQueryDto [fromTime=");
		builder.append(fromTime);
		builder.append(", toTime=");
		builder.append(toTime);
		builder.append("]");
		return builder.toString();
	}
}
