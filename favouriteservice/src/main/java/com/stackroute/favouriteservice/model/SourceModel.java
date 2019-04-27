package com.stackroute.favouriteservice.model;

public class SourceModel {

	private String sourceId;

	private String sourceName;

	
	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	

	@Override
	public String toString() {
		return "Source [id=" + sourceId + ",name=" + sourceName +  "]";
	}

}
