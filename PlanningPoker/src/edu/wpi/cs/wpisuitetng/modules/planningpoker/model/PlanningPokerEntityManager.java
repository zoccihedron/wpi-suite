package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.UnauthorizedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.model.Game;

public class PlanningPokerEntityManager implements EntityManager<Game> {

	Data db;
	
	public PlanningPokerEntityManager(Data db) {
		this.db = db;
	}
	
	@Override
	public Game makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final Game newGame = Game.fromJson(content);
		if(!db.save(newGame, s.getProject())) {
			throw new WPISuiteException();
		}
		return newGame;
	}

	@Override
	public Game[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException();
		}
		Game[] games = null;
		try {
			games = db.retrieve(Game.class, "id", intId, s.getProject()).toArray(new Game[0]);
		} catch (WPISuiteException e) {
			e.printStackTrace();
		}
		if(games.length < 1 || games[0] == null) {
			throw new NotFoundException();
		}
		return games;
	}

	@Override
	public Game[] getAll(Session s) throws WPISuiteException {
		return db.retrieveAll(new Game(), s.getProject()).toArray(new Game[0]);
	}

	@Override
	public Game update(Session s, String content) throws WPISuiteException {
		Game updatedGame = Game.fromJson(content);
		/*
		 * Because of the disconnected objects problem in db4o, we can't just save Games.
		 * We have to get the original defect from db4o, copy properties from updatedGame,
		 * then save the original Game again.
		 */
		List<Model> oldGames = db.retrieve(Game.class, "id", updatedGame.getID(), s.getProject());
		if(oldGames.size() < 1 || oldGames.get(0) == null) {
			throw new BadRequestException("Game with ID does not exist.");
		}
				
		Game existingGame = (Game)oldGames.get(0);		

		// copy values to old Game and fill in our changeset appropriately
		existingGame.copyFrom(updatedGame);
		
		if(!db.save(existingGame, s.getProject())) {
			throw new WPISuiteException();
		}
		
		return existingGame;
	}

	@Override
	public void save(Session s, Game model) throws WPISuiteException {
		db.save(model, s.getProject());
	}
	
	/**
	 * Ensures that a user is of the specified role
	 * @param session the session
	 * @param role the role being verified
	
	 * @throws WPISuiteException user isn't authorized for the given role */
	private void ensureRole(Session session, Role role) throws WPISuiteException {
		User user = (User) db.retrieve(User.class, "username", session.getUsername()).get(0);
		if(!user.getRole().equals(role)) {
			throw new UnauthorizedException();
		}
	}
	
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Implement role check for authorization of delete
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Implement role check for authorization of deleteAll
		db.deleteAll(new Game(), s.getProject());
	}

	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new Game()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

}
