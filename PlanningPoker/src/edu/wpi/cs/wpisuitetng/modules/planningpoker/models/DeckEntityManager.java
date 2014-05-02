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
	
	private int id_count = 0;

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
		return db.retrieve(Deck.class, "id", newDeck.getId(), s.getProject())
				.toArray(new Deck[0])[0];
	}

	@Override
	public Deck[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		final int intId = Integer.parseInt(id);
		if (intId < 1) {
			throw new NotFoundException("ID is not valid");
		}
		
		Deck[] decks = null;
		try{
			decks = db.retrieve(Deck.class, "id", intId, s.getProject())
					.toArray(new Deck[0]);
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
		final Deck[] allDecks = db.retrieveAll(new Deck(), s.getProject())
				.toArray(new Deck[0]);
		return allDecks;
	}

	@Override
	public Deck update(Session s, String content) throws WPISuiteException {
		final Deck updatedDeck = Deck.fromJson(content);
		final List<Model> oldDecks = db.retrieve(Deck.class,  "id", 
				updatedDeck.getId(), s.getProject());
		if(oldDecks.size() < 1 || oldDecks.get(0) == null){
			throw new BadRequestException("Deck with ID does not exist");
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
		if(id_count == 0){
			final Deck[] retrieved = db.retrieveAll(new Deck(), s.getProject())
					.toArray(new Deck[0]);
			if(retrieved.length == 0){
				id_count = 1;
			} else {
				id_count = getDeckWithLargestId(retrieved) + 1;
			}
		}
		
		model.setId(id_count);
		id_count++;
		
		if(!db.save(model, s.getProject())){
			throw new WPISuiteException("Save was not successful");
		}
	}

	


	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		return (db.delete(getEntity(s, id)[0]) != null) ? true : false;
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
		throw new NotImplementedException();
	}
	
	private int getDeckWithLargestId(Deck[] retrieved) {
		int largestId = 0;
		for (int i = 0; i < retrieved.length; i++) {
			if (retrieved[i].getId() > largestId) {
				largestId = retrieved[i].getId();
			}
		}
		return largestId;
	}

}
