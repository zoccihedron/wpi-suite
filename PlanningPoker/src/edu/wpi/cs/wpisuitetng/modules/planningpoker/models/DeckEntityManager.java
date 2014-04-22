package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.models.Game.GameStatus;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.utils.Mailer;
import edu.wpi.cs.wpisuitetng.modules.planningpoker.utils.Mailer.Notification;

public class DeckEntityManager implements EntityManager<Deck>{
	
	/** The database */
	Data db;

	/**
	 * Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. To make sure
	 * this happens, be sure to place add this entity manager to the map in the
	 * ManagerLayer file.
	 * 
	 * @param db
	 *            a reference to the persistent database
	 */
	public DeckEntityManager(Data db) {
		this.db = db;
	}


	@Override
	public Deck makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		final Deck newDeck = Deck.fromJson(content);
		save(s, newDeck);
		return db.retrieve(Deck.class, "name", s.getProject()).toArray(new Deck[0])[0];
	}

	@Override
	public Deck[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		Deck[] decks = null;
		try{
			decks = db.retrieve(Deck.class, "name", s.getProject()).toArray(new Deck[0]);
		} catch(WPISuiteException e){
			e.printStackTrace();
		}
		if(decks.length < 1 || decks[0] == null){
			throw new NotFoundException("There are no decks in the list");
		}
		return decks;
	}

	@Override
	public Deck[] getAll(Session s) throws WPISuiteException {
		final Deck[] allDecks = db.retrieveAll(new Deck(), s.getProject()).toArray(new Deck[0]);
		return allDecks;
	}

	@Override
	public Deck update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Session s, Deck model) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}
