/*******************************************************************************
 * Copyright (c) 2013 WPI-Suite
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Team Rolling Thunder
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.Attachment;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.DevelopmentTask;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.NoteList;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.TestStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.characteristics.TransactionHistory;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.iterations.IterationModel;

/**
 * Basic Requirement class that contains the data to be stored for a Requirement
 * 
 * 
 * @version $Revision: 1.0 $
 * @author justinhess
 */
public class Requirement extends AbstractModel {
	/** the ID of the requirement */
	private int id; // TODO: move ID stuff to server side?

	/** the name of the requirement */
	private String name;

	/** the release number of the requirement */
	private String release;

	/** the project status of the requirement */
	private RequirementStatus status;

	/** the priority of the requirement */
	private RequirementPriority priority;

	/** a short description of the requirement */
	private String description;

	/** the estimated amount of effort to complete the requirement */
	private int estimate;
	
	/** flag to indicate when the requirement estimate was just edited */
	private boolean estimateEdited;
	
	/** flag to indicate whether the requirement was just created or not */
	private boolean wasCreated;

	/** the actual effort of completing the requirement */
	private int actualEffort;

	/** history of transactions of the requirement */
	private TransactionHistory history;

	/** the type of the requirement */
	private RequirementType type;

	/** notes associated with the requirement */
	private NoteList notes;

	/** iteration the requirement is assigned to */
	private String iteration;
	
	/** the ID of the requirement that this requirement is a sub-requirement of */
	private int parentID = -1;

	/**
	 * team members the requirement is assigned to need to figure out the class
	 * of a user name, then use that instead of TeamMember
	 */
	private List<String> assignedTo;

	/** development tasks associated with the requirement */
	private List<DevelopmentTask> tasks;

	/** acceptance tests associated with the requirement */
	private ArrayList<AcceptanceTest> tests;

	/** attachments associated with the requirement */
	private List<Attachment> attachments;

	/**
	 * Constructs a Requirement with default characteristics
	 */
	public Requirement() {
		super();
		name = description = "";
		release = "";
		status = RequirementStatus.NEW;
		priority = RequirementPriority.BLANK;
		estimate = actualEffort = 0;
		history = new TransactionHistory();
		iteration = "Backlog";
		type = RequirementType.BLANK;
		this.parentID = -1;
		notes = new NoteList();
		tasks = new ArrayList<DevelopmentTask>();
		tests = new ArrayList<AcceptanceTest>();
		attachments = new ArrayList<Attachment>();
	}

	/**
	 * Construct a Requirement with required properties provided and others set
	 * to default
	 * 
	 * @param id
	 *            The ID number of the requirement
	 * @param name
	 *            The name of the requirement
	 * @param description
	 *            A short description of the requirement
	 */
	// need to phase out supplying the ID
	public Requirement(int id, String name, String description) {
		this();
		this.id = id;
		this.name = name;
		this.description = description;
		this.parentID = -1;
	}

	/**
	 * Constructs a requirement from the provided characteristics
	 * 
	 * @param id
	 *            The ID number of the requirement
	 * @param name
	 *            The name of the requirement
	 * @param release
	 *            The release number of the requirement
	 * @param status
	 *            The status of the requirement
	 * @param priority
	 * 			The priorty of the requirement
	 * @param description
	 *            A short description of the requirement
	 * @param estimate
	 *            The estimated time required by the task. This is in a point
	 *            system decided by the user
	 * @param effort
	 *            The estimated amount of work required by the requirement.
	 * @deprecated Should not be used anymore.
	 */
	@Deprecated
	public Requirement(int id, String name, String release,
			RequirementStatus status, RequirementPriority priority,
			String description, int estimate, int effort) {
		this.id = id;
		this.name = name;
		this.release = release;
		this.status = status;
		this.priority = priority;
		this.description = description;
		this.estimate = estimate;
		this.actualEffort = effort;
		this.parentID = -1;
	}

	/**
	 * Returns an instance of Requirement constructed using the given
	 * Requirement encoded as a JSON string.
	 * 
	 * @param json
	 *            JSON-encoded Requirement to deserialize
	
	 * @return the Requirement contained in the given JSON */
	public static Requirement fromJson(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Requirement.class);
	}

	/**
	 * /**Getter for the id
	 * 
	
	 * @return the id */
	public int getId() {
		return id;
	}

	/**
	 * Setter for the id
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * getter for the name
	 * 
	
	 * @return the name */
	public String getName() {
		return name;
	}

	/**
	 * setter for the name
	 * 
	 * @param name
	 *            the name to set
	 */
	public void setName(String n) {
		if (!n.equals(this.name) && !wasCreated) {
			String originalName = this.name;
			String newName = n;
			if (newName.length() > 100)
				newName = newName.substring(0, 100);
			String message = ("Name changed from " + originalName + " to " + newName);
			this.history.add(message);			
		}
		this.name = n;
		if (name.length() > 100)
			this.name = n.substring(0, 100);
	}

	/**
	 * getter for the release number
	 *  
	
	 * @return the release */
	public String getRelease() {
		return release;
	}

	/**
	 * Setter for the release number
	 * 
	 * @param release
	 *            the release to set
	 */
	public void setRelease(String rel) {
		if (!rel.equals(this.release) && !wasCreated) {
			String originalRelease = this.release;
			String newRelease = rel;
			String message = null;
			if (originalRelease.isEmpty()) message = ("Release Number set to '" + newRelease + "'");
			else if (newRelease.isEmpty()) message = ("Release Number set to blank from '" + originalRelease + "'");
			else message = ("Release Number changed from '" + originalRelease + "' to '" + newRelease + "'");
			this.history.add(message);			
		}
		this.release = rel;
	}

	/**
	 * Getter for the status
	 * 
	
	 * @return the status */
	public RequirementStatus getStatus() {
		return status;
	}

	/**
	 * Setter for the status
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(RequirementStatus status) {
		if ((status != this.status) && (!wasCreated)) {
			String originalStatus = this.status.toString();
			String newStatus = status.toString();
			String message = ("Status changed from '" + originalStatus + "' to '" + newStatus + "'");
			this.history.add(message);			
		}

		this.status = status;

	}

	/**
	 * Getter for the description
	 * 
	
	 * @return the description */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for the description
	 * 
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String desc) {
		if (!desc.equals(this.description) && !wasCreated) {
			this.history.add("Description changed");			
		}
		this.description = desc;
	}

	/**
	 * Getter for the estimate
	 * 
	
	 * @return the estimate */
	public int getEstimate() {
		return this.estimate;
	}
	
	/**
	 * Returns the estimate of the parent along with its children
	
	 * @return total estimate */
	public int getTotalEstimate() {
		return getEstimate() + getChildEstimate();
	}
	
	/**
	 * Returns the estimate of the children
	
	 * @return total estimate */
	public int getChildEstimate() {
		List<Requirement> children = getChildren();
		if(children.size() == 0) return 0;
		
		int childEstimates = 0;
		
		for(Requirement child : children)
		{
			childEstimates += child.getTotalEstimate();
		}
		
		return childEstimates;
	}

	/**
	 * Setter for the estimate
	 * 
	 * @param estimate
	 *            the estimate to set	 
	 */
	public void setEstimate(int estimate) {
		if ((estimate != this.estimate) && (!wasCreated)) {
			int originalEstimate = this.estimate;
			int newEstimate = estimate;
			String message = ("Estimate changed from '" + originalEstimate + "' to '" + newEstimate + "'");
			this.history.add(message);
			this.setEstimateEdited(true);
		}	
		
		int diff = estimate - this.estimate;
		this.estimate = estimate;
	}

	/**
	 * Getter for the effort
	 * 
	
	 * @return the effort */
	public int getEffort() {
		return actualEffort;
	}

	/**
	 * Setter for the effort
	 * 
	 * @param effort
	 *            the effort to set
	 */
	public void setEffort(int effort) {
		this.actualEffort = effort;
	}

	/**
	 * Getter for the priority
	 * 
	
	 * @return the priority */
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * Setter for the priority
	 * 
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(RequirementPriority priority) {
		if ((priority != this.priority) && (!wasCreated)) {
			String originalPriority = this.priority.toString();
			String newPriority = priority.toString();
			String message = ("Priority changed from '" + originalPriority + "' to '" + newPriority + "'");
			this.history.add(message);			
		}

		this.priority = priority;
	}

	/**
	 * Getter for the type
	 * 
	
	 * @return the type */
	public RequirementType getType() {
		return type;
	}

	/**
	 * Setter for the type
	 * 
	 * @param type
	 *            the type to set the requirement to
	 */
	public void setType(RequirementType type) {
		if ((type != this.type) && (!wasCreated)) {			
			String originalType = this.type.toString();
			String newType = type.toString();
			String message = null;
			if (originalType.isEmpty()) message = ("Type set to '" + newType + "'");
			else if (newType.isEmpty()) message = ("Type set to blank from '" + originalType + "'");
			else message = ("Type changed from '" + originalType + "' to '" + newType + "'");
			this.history.add(message);			
		}
		this.type = type;
	}

	
	/**
	 * Getter for the notes
	 * 
	
	 * @return the list of notes associated with the requirement */
	public NoteList getNotes(){
		return notes;
	}
	
	/**
	 * Method addNote.
	 * @param noteMsg String
	 */
	public void addNote(String noteMsg) {
		if  (!wasCreated) this.history.add("Note added");		
		// Add note to requirement
		this.getNotes().add(noteMsg);
	}

	/**
	 * Getter for the list of development tasks
	 * 
	
	 * @return the list of development tasks */
	public List<DevelopmentTask> getTasks() {
		return tasks;
	}

	/**
	 * Method to add a development task
	 * 
	 * @param task
	 *            the task to be added to the list of development tasks
	 */
	public void addTask(DevelopmentTask task) {
		tasks.add(task);
	}

	/**
	 * Method to remove a development task
	 * 
	 * @param id the id to remove
	 */
	public void removeTask(int id) {
		// iterate through the list looking for the task to remove
		for (int i = 0; i < this.tasks.size(); i++) {
			if (tasks.get(i).getId() == id) {
				// remove the id
				tasks.remove(i);
				break;
			}
		}
	}

	/**
	 * Getter for AcceptanceTests
	 * 
	
	 * @return the list of acceptance tests for the requirement */
	public ArrayList<AcceptanceTest> getTests() {
		return tests;
	}

	/**
	 * Method for adding an Acceptance Test
	 * 
	 * @param test
	 *            the acceptance test to implement
	 */
	public void addTest(AcceptanceTest test) {
		if (!wasCreated) {
			String msg = "Acceptance test '" + test.getName() + "' added.";
			this.history.add(msg);
		}
		tests.add(test);
	}
	
	/**
	 * Updates the test status
	 * @param testID iD of test
	 * @param status new status
	 */
	public void updateTestStatus(int testID, TestStatus status) {
		for (int i = 0; i < this.tests.size(); i++) {
			if (this.tests.get(i).getId() == testID) {
				this.tests.get(i).setStatus(status);
			}
		}
	}

	/**
	 * Method for removing an Acceptance Test
	 * 
	 * @param id
	 *            the id of the test to remove
	 */
	public void removeTest(int id) {
		// iterate through the list looking for the note to remove
		for (int i = 0; i < this.tests.size(); i++) {
			if (tests.get(i).getId() == id) {
				// remove the id
				tests.remove(i);
				break;
			}
		}
	}

	/**
	 * Getter for attachments
	 * 
	
	 * @return the list of attachments */
	public List<Attachment> getAttachments() {
		return attachments;
	}

	/**
	 * Method to add an attachment
	 * 
	 * @param attachment
	 *            Attachment to add
	 */
	public void addAttachment(Attachment attachment) {
		attachments.add(attachment);
	}

	/**
	 * Method to remove an attachment
	 * 
	 * @param id
	 *            ID of the attachment to be removed
	 */
	public void removeAttachment(int id) {
		// iterate through the list looking for the attachment to remove
		for (int i = 0; i < this.attachments.size(); i++) {
			if (attachments.get(i).getId() == id) {
				// remove the id
				attachments.remove(i);
				break;
			}
		}
	}

	/**
	 * Getter for Iteration. Currently deals in Strings, but will deal with
	 * Iterations in the future
	 * 
	
	 * @return a string representing the iteration it has been assigned to */
	public String getIteration() {
		return iteration;
	}

	/**
	 * Setter for iteration. Currently deals with strings, but will deal with
	 * Iterations in the future.
	 * 
	 * @param newIterationName
	 *            the iteration to assign the requirement to	
	 */
	public void setIteration(String newIterationName) {
		if(newIterationName.trim().length() == 0) newIterationName = "Backlog";
		String curIter = this.iteration;

		Iteration oldIteration = IterationModel.getInstance().getIteration(curIter);
		Iteration newIteration = IterationModel.getInstance().getIteration(newIterationName);
		
		
		if(!this.iteration.equals(newIterationName) && (!wasCreated))
		{
			//create the transaction history
			String message = ("Moved from '"	+ curIter + "' to '" + newIteration + "'");
			this.history.add(message);
		}
		
		//update status as needed
		if((this.status.equals(RequirementStatus.NEW) || this.status.equals(RequirementStatus.OPEN))
				&& !newIterationName.equals("Backlog"))
		{
			this.setStatus(RequirementStatus.INPROGRESS);
		}
		
		if(this.status.equals(RequirementStatus.INPROGRESS) && newIterationName.equals("Backlog"))
		{
			if(wasCreated)
			{
				this.setStatus(RequirementStatus.NEW);
			}
			else
			{
				this.setStatus(RequirementStatus.OPEN);
			}
		}
		this.iteration = newIterationName;
	}


	/**
	 * Getter for parent IDs
	 * 
	
	 * @return the parent ID, which is the ID of the parent of the requirement  */
	public int getParentID() {
		return parentID;
	}

	/**
	 * Setter for parentID
	 * Assign the parent ID for this requirement
	 * @param parentReq ID of the parent          
	
	 * @throws Exception if the parent is an ancestor of the child already */
	public void setParentID(int parentReq) throws Exception {
		if (parentReq == -1 || !RequirementModel.getInstance().getRequirement(parentReq).isAncestor(this.getId())) {
			this.parentID = parentReq;
//			Requirement parentRequirement = RequirementModel.getInstance().getRequirement(parentID);
//			parentRequirement.getHistory().add(this.getName() + " was added as a Child Requirement");
		} else {
			throw new Exception("Cannot add ancestor as parent");
		}
	}

	/**
	 * Checks if a parent requirement is an ancestor of itself
	 * @param parentId The ID of the parent requirement
	
	 * @return true if the parent is an ancestor */
	public boolean hasAncestor(int parentId) {
		if(this.parentID == -1) return false;
		
		return RequirementModel.getInstance().getRequirement(this.parentID).hasAncestor(parentId) || this.parentID == parentId;
	}
	
	/**
	 * Checks if a requirement is an ancestor of a given child
	 * @param childId ID of the child
	
	 * @return true if it is an ancestor of the child */
	public boolean isAncestor(int childId) {
		List<Requirement> children = this.getChildren();
		for (int i = 0; i < children.size(); i++ ) {
			if (children.get(i).getId() == childId || children.get(i).isAncestor(childId))
				return true;
		}
		return false;
	}

	/**
	 * Getter for children
	
	 * @return the children requirements of the requirement */
	public List<Requirement> getChildren() {		
		return RequirementModel.getInstance().getChildren(this);
	}
	
	/**
	 * Getter for parent
	
	 * @return the parent requirement of the sub-requirement */
	public Requirement getParent() {		
		return RequirementModel.getInstance().getRequirement(parentID);
	}
	
	/**
	 * Setter for parent
	 * extracts the ID of parentReq and assigns it to parentID 
	 * 
	 * @param parentReq            
	
	 * @throws Exception if invalid parent */
	public void setParent(Requirement parentReq) throws Exception {
		setParentID(parentReq.getId());
	}

	/**
	 * Getter for AssignedTo
	 * 
	
	 * @return the list of strings representing the users for whom the
	 *         requirement has been assigned to. */
	public List<String> getAssignedTo() {
		return assignedTo;
	}

	/**
	 * Setter for assignedTo
	 * 
	 * @param assignedTo
	 *            the list of strings representing the people who the
	 *            requirement is assigned to.
	 */
	public void setAssignedTo(List<String> assignedTo) {
		this.assignedTo = assignedTo;
	}

	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	/**
	 * Method toJSON.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	@Override
	/**This returns a Json encoded String representation of this requirement object.
	 * 
	 * @return a Json encoded String representation of this requirement
	 * 
	 */
	public String toJSON() {
		return new Gson().toJson(this, Requirement.class);
	}

	/**
	 * Returns an array of Requirements parsed from the given JSON-encoded
	 * string.
	 * 
	 * @param json
	 *            string containing a JSON-encoded array of Requirement
	
	 * @return an array of Requirement deserialized from the given JSON string */
	public static Requirement[] fromJsonArray(String json) {
		final Gson parser = new Gson();
		return parser.fromJson(json, Requirement[].class);
	}

	/**
	 * Method identify.
	 * @param o Object
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object)
	 */
	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Method toString.
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() * @see edu.wpi.cs.wpisuitetng.modules.Model#toString()
	 */
	@Override
	public String toString() {
		if(this.getName().length()>40){
			return this.getName().substring(0, 40)+"...";
		}
		return this.getName();
	}

	/**
	 * Returns whether the requirement has been deleted.
	 * 
	
	 * @return true if the status of the requirement is deleted and false otherwise. */
	public boolean isDeleted() {
		return status == RequirementStatus.DELETED;
	}

	/**
	 * The getter for Transaction History
	 * 
	
	 * @return a TransactionHistory for this requirement */
	public TransactionHistory getHistory() {
		return history;
	}

	/**
	 * The Setter for TransactionHistory
	 * 
	 * @param history
	 *            The history to assign to the requirement
	 */
	public void setHistory(TransactionHistory history) {
		this.history = history;
	}

	/**
	 * @param tests the tests to set
	 */
	public void setTests(ArrayList<AcceptanceTest> tests) {
		this.tests = tests;
	}
	
	/**
	 * Copies all of the values from the given requirement to this requirement.
	 * 
	 * @param toCopyFrom
	 *            the requirement to copy from.
	 */
	public void copyFrom(Requirement toCopyFrom) {
		this.description = toCopyFrom.description;
		this.name = toCopyFrom.name;
		this.actualEffort = toCopyFrom.actualEffort;
		this.estimate = toCopyFrom.estimate;
		this.iteration = toCopyFrom.iteration;
		this.priority = toCopyFrom.priority;
		this.release = toCopyFrom.release;
		this.status = toCopyFrom.status;
		this.type = toCopyFrom.type;
		this.history = toCopyFrom.history;
		this.notes = toCopyFrom.notes;
		this.tests = toCopyFrom.tests;
		this.parentID = toCopyFrom.parentID;
	}

	/**
	
	 * @return the estimateEdited */
	public boolean getEstimateEdited() {
		return estimateEdited;
	}

	/**
	 * @param b the estimateEdited to be set
	 */
	public void setEstimateEdited(boolean b) {
		this.estimateEdited = b;
	}

	/**
	
	 * @return the wasCreated */
	public boolean getWasCreated() {
		return wasCreated;
	}

	/**
	 * @param wasCreated the wasCreated to set
	 */
	public void setWasCreated(boolean wasCreated) {
		this.wasCreated = wasCreated;
	}
}
