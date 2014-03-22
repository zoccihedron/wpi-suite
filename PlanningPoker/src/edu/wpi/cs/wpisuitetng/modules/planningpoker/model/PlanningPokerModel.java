package edu.wpi.cs.wpisuitetng.modules.planningpoker.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.Permission;
import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;

public class PlanningPokerModel extends AbstractModel {
	
	private List<Game> games;
	
	public PlanningPokerModel() {
		this.games = new ArrayList<Game>();
	}
	
	public void AddGame(Game newGame) {
		games.add(newGame);
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, PlanningPokerModel.class);
		return json;
	}
	
	
	public String fromJSON() {
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Permission getPermission(User u) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPermission(Permission p, User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public Project getProject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProject(Project p) {
		// TODO Auto-generated method stub

	}

}
