/*******************************************************************************
 * Copyright (c) 2012 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: mpdelladonna
 * rchamer
 * twack
 * bgaffey
 *    
 *******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.core.entitymanagers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import edu.wpi.cs.wpisuitetng.ManagerLayer;
import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.DatabaseException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.AbstractEntityManager;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.ProjectDeserializer;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.utils.Mailer;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.utils.Mailer.Notification;

public class ProjectManager implements EntityManager<Project>{

	Class<Project> project = Project.class;
	Gson gson;
	Data data;
	private String[] allModules;
	
	private static final Logger logger = Logger.getLogger(ProjectManager.class.getName());
	
	public ProjectManager(Data data)
	{
		this.data = data;
		
		// hang the custom serializer/deserializer
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(this.project, new ProjectDeserializer());
		
		this.gson = builder.create();
	}
	
	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		
		return this.getEntity(s, s.getProject().getIdNum())[0].toJSON();
	}

	@Override
	public Project makeEntity(Session s, String content) throws WPISuiteException {	
		User theUser = s.getUser();
		
		logger.log(Level.FINER, "Attempting new Project creation...");
		
		Project p;
		try{
			p = Project.fromJSON(content);
		} catch(JsonSyntaxException e){
			logger.log(Level.WARNING, "Invalid Project entity creation string.");
			throw new BadRequestException("The entity creation string had invalid format. Entity String: " + content);
		}
		
		 
		logger.log(Level.FINE, "New project: "+ p.getName() +" submitted by: "+ theUser.getName() );
		p.setOwner(theUser);
		
		if(getEntity(s,p.getIdNum())[0] == null)
		{
			if(getEntityByName(s, p.getName())[0] == null)
			{
				if(p.getMailAccount().equals("") && p.getMailServer().equals("") && 
						p.getPort().equals("") && p.getPassword().equals(""))
				{
					save(s, p);
				}
				else 
				{
					final ExecutorService thread = Executors.newSingleThreadExecutor();
					final List<User> u = new ArrayList<User>();
					u.add(new User("", "", "", 0));
					final Mailer mailer = new Mailer(new Game(), u, Notification.TEST, p);
					final Future<Boolean> future = thread.submit(mailer);
					
					try {
						if(future.get()) {
							save(s, p);
						} else {
							logger.log(Level.WARNING, "Invalid but non-empty email" +
									" credentials during project creation.");
							throw new ConflictException("The email account provided" +
									"is invalid. Entity String: " + content);
						}
					} catch (Exception e) {
						e.printStackTrace();
						logger.log(Level.WARNING, "Invalid but non-empty email credentials" +
								"during project creation.");
						throw new ConflictException("There was an error accessing the email" + 
								"account provided. Entity String: " + content);

					}
				}
			}
			else
			{
				logger.log(Level.WARNING, "Project Name Conflict Exception during Project creation.");
				throw new ConflictException("A project with the given name already exists. Entity String: " + content);
			}
		}
		else
		{
			logger.log(Level.WARNING, "ID Conflict Exception during Project creation.");
			throw new ConflictException("A project with the given ID already exists. Entity String: " + content); 
		}
		
		logger.log(Level.FINER, "Project creation success!");
		return p;
	}

	@Override
	public Project[] getEntity(Session s, String id) throws WPISuiteException 
	{
		Project[] m = new Project[1];
		if(id.equalsIgnoreCase(""))
		{
			return getAll(s);
		}
		else
		{
			return data.retrieve(project, "idNum", id).toArray(m);
		}
	}
	
	/**
	 * returns a project without requiring a session, 
	 * specifically for the scenario where a session needs to be created.
	 * only ever returns one project, "" is not a valid argument;
	 * 
	 * @param id - the id of the user, in this case it's the idNum
	 * @return a list of matching projects
	 * @throws NotFoundException if the project cannot be found
	 * @throws WPISuiteException if retrieve fails
	 */
	public Project[] getEntity(String id) throws NotFoundException, WPISuiteException
	{
		Project[] m = new Project[1];
		if(id.equalsIgnoreCase(""))
		{
			throw new NotFoundException("No (blank) Project id given.");
		}
		else
		{
			m = data.retrieve(project, "idNum", id).toArray(m);
			
			if(m[0] == null)
			{
				throw new NotFoundException("Project with id <" + id + "> not found.");
			}
			else
			{
				return m;
			}
		}
	}
	
	public Project[] getEntityByName(Session s, String projectName) throws NotFoundException, WPISuiteException
	{
		Project[] m = new Project[1];
		if(projectName.equalsIgnoreCase(""))
		{
			throw new NotFoundException("No (blank) Project name given.");
		}
		else
		{
			return data.retrieve(project, "name", projectName).toArray(m);
		}
	}

	@Override
	public Project[] getAll(Session s) {
		Project[] ret = new Project[1];
		ret = data.retrieveAll(new Project("","")).toArray(ret);
		return ret;
	}

	@Override
	public void save(Session s, Project model) throws WPISuiteException {
		if(s == null){
			throw new WPISuiteException("Null Session.");
		}
		//permissions checking happens in update, create, and delete methods only
		/*User theUser = s.getUser();
		if(Role.ADMIN.equals(theUser.getRole()) || 
				Permission.WRITE.equals(model.getPermission(theUser))){*/
		if(data.save(model))
		{
			logger.log(Level.FINE, "Project Saved :" + model);
			return ;
		}
		/*else
		{
			logger.log(Level.WARNING, "Project Save Failure!");
			throw new DatabaseException("Save failure for Project."); // Session User: " + s.getUsername() + " Project: " + model.getName());
		}
		/*}
		else
		{
			logger.log(Level.WARNING, "ProjectManager Save attempted by user with insufficient permission");
			throw new UnauthorizedException("You do not have the requred permissions to perform this action.");
		}*/
		
	}

	@Override
	public boolean deleteEntity(Session s1, String id) throws WPISuiteException
	{
		if(s1==null){
			throw new WPISuiteException("Null Session.");
		}
		User theUser = s1.getUser();
		Project[] model = this.getEntity(id);
		if(model[0].getPermission(theUser).equals(Permission.WRITE) || 
		   theUser.getRole().equals(Role.ADMIN)){
			Model m = data.delete(data.retrieve(project, "idNum", id).get(0));
			logger.log(Level.INFO, "ProjectManager deleting project <" + id + ">");
			
			return (m != null) ? true : false;
		}
		else{
			logger.log(Level.WARNING, "ProjectManager Delete attempted by user with insufficient permission");
			throw new UnauthorizedException("You do not have the required permissions to perform this action.");
		}
	}
	
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		User theUser = s.getUser();
		logger.log(Level.INFO, "ProjectManager invoking DeleteAll...");
		if(theUser.getRole().equals(Role.ADMIN)){
		data.deleteAll(new Project("",""));
		}
		else
		{
			logger.log(Level.WARNING, "ProjectManager DeleteAll attempted by user with insufficient permission");
			throw new UnauthorizedException("You do not have the required permissions to perform this action.");
		}
	}

	@Override
	public int Count() {
		
		return 0;
	}
	
	public Project update(Session s, Project toUpdate, String changeSet) throws WPISuiteException
	{
		if(s == null){
			throw new WPISuiteException("Null session.");
		}
		
		User theUser = s.getUser();
		if(theUser.equals(toUpdate.getOwner()) || 
		   theUser.getRole().equals(Role.ADMIN)){
		
			// convert updateString into a Map, then load into the User
			try
			{
				logger.log(Level.FINE, "Project update being attempted...");
				Project change = Project.fromJSON(changeSet);
			
				// check if the changes contains each field of name
				if(change.getName() != null && !change.getName().equals(""))
				{
					// check for conflict for changing the project name
					Project isConflict = getEntityByName(s, change.getName())[0];
					if(isConflict != null && !isConflict.getIdNum().equals(change.getIdNum()))
					{
						throw new ConflictException("ProjectManager attempted to update a Project's name to be the same as an existing project");
					}
					
					toUpdate.setName(change.getName());
				}
				
				if(change.getOwner() != null)
				{
					toUpdate.setOwner(change.getOwner());
				}
			}
			catch(ConflictException e)
			{
				logger.log(Level.WARNING, "ProjectManager attempted to update a Project's name to be the same as an existing project");
				throw e;
			}
			catch(Exception e)
			{
				logger.log(Level.WARNING, "ProjectManager.update() had a failure in the changeset mapper.");
				throw new DatabaseException("Failure in the ProjectManager.update() changeset mapper."); // on Mapping failure
			}
			
			// save the changes back
			this.save(s, toUpdate);
			
			// check for changes in each field
			return toUpdate;
		}
		else
		{
			logger.log(Level.WARNING, "Unauthorized Project update attempted.");
			throw new UnauthorizedException("You do not have the required permissions to perform this action.");
		}
	}

	@Override
	public Project update(Session s, String content) throws WPISuiteException {
		Project[] p = null;
		
		String id = AbstractEntityManager.parseFieldFromJSON(content, "idNum");
		
		if(id.equalsIgnoreCase(""))
		{
			throw new NotFoundException("No (blank) Project id given.");
		}
		else
		{
			p = data.retrieve(project, "idNum", id).toArray(p);
			
			if(p[0] == null)
			{
				throw new NotFoundException("Project with id <" + id + "> not found.");
			}
		}
		
		return update(s, p[0], content);
	}

	public void setAllModules(String[] mods)
	{
		this.allModules = mods;
	}
	
	@Override
	public String advancedPut(Session s, String[] args, String content) throws WPISuiteException 
	{
		Project p = getEntity(args[2])[0];
		String[] names = null;
		
		try{
			names = gson.fromJson(content, String[].class);
		}catch(JsonSyntaxException j)
		{
			throw new BadRequestException("Could not parse JSON");
		}
		
		ArrayList<String> success = new ArrayList<String>();
		
		UserManager u = ManagerLayer.getInstance().getUsers();
		
		if(args.length > 3)
		{
			if("add".equals(args[3]))
			{
				for(String person : names)
				{
					if(p.addTeamMember(u.getEntity(s, person)[0]))
						success.add(person);
				}
			}
			else if("remove".equals(args[3]))
			{
				for(String person : names)
				{
					if(p.removeTeamMember(u.getEntity(s, person)[0]))
						success.add(person);
				}
			}
		}
		
		return gson.toJson(success.toArray(names),String[].class );
	}

	@Override
	public String advancedPost(Session s, String string, String content) throws WPISuiteException 
	{
		return gson.toJson(allModules, String[].class);
	}


	

}
