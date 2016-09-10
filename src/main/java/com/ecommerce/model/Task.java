package com.ecommerce.model;

import java.util.Date;

public class Task {

	private long id;
	
	private String name,
	               comment,
	               assigner,
	               assignee,
	               description;

	private Date created,
				 completed;
	
	
	private boolean complete;

	public Task() {}
	
	public Task(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getCompleted() {
		return completed;
	}

	public void setCompleted(Date completed) {
		this.completed = completed;
	}

	public String getAssigner() {
		return assigner;
	}

	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [id=").append(id).append(", name=").append(name)
				.append(", description=").append(description)
				.append(", comment=").append(comment).append(", created=")
				.append(created).append(", complete=").append(complete)
				.append(", assigner=").append(assigner).append(", assignee=")
				.append(assignee).append(", completed=").append(completed)
				.append("]");
		return builder.toString();
	}

}
