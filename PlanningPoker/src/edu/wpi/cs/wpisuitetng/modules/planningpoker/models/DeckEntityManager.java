package edu.wpi.cs.wpisuitetng.modules.planningpoker.models;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;

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
	public Deck[] getEntity(Session s, String name) throws NotFoundException,
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
		final Deck updatedDeck = Deck.fromJson(content);
		final List<Model> oldDecks = db.retrieve(Deck.class,  "name", 
				updatedDeck.getName(), s.getProject());
		if(oldDecks.size() < 1 || oldDecks.get(0) == null){
			throw new BadRequestException("Deck with name does not exist");
		}
		
		final Deck existingDeck = (Deck) oldDecks.get(0);
		existingDeck.copyFrom(updatedDeck);
		if(!db.save(existingDeck, s.getProject())){
			throw new WPISuiteException("Save was not successful");
		}
		
		return existingDeck;
		
		
	}

	@Override
	public void save(Session s, Deck model) throws WPISuiteException {
		if(!db.save(model, s.getProject())){
			throw new WPISuiteException("Save was not successful");
		}
	}

	@Override
	public boolean deleteEntity(Session s, String name) throws WPISuiteException {
		return (db.delete(getEntity(s, name)[0]) != null) ? true : false;
	}

	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		db.deleteAll(new Deck(), s.getProject());
	}

	@Override
	public int Count() throws WPISuiteException {
		return db.retrieveAll(new Deck()).size();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new NotImplementedException();

	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

}
