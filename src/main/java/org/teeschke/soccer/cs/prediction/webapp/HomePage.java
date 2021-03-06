package org.teeschke.soccer.cs.prediction.webapp;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.teeschke.soccer.cs.prediction.webapp.prediction.DecisionTreeModel;
import org.teeschke.soccer.cs.prediction.webapp.prediction.PredictedResult;
import org.teeschke.soccer.cs.prediction.webapp.team.TeamContainer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class HomePage extends WebPage implements Serializable{
	private static final long serialVersionUID = 1L;

	private List<Player> teamA;
	private List<Player> teamB;
	private PredictedResult predictedResult;

	public HomePage(final PageParameters parameters) {
		super(parameters);

		teamA = new ArrayList();
		teamB = new ArrayList();

		add(new TeamContainer("teamA", new PropertyModel<List<Player>>(HomePage.this, "teamA")){
			@Override
			public void onTeamChanged() {
				updatePredictedResult();
			}
		});
		add(new TeamContainer("teamB", new PropertyModel<List<Player>>(HomePage.this, "teamB")){
			@Override
			public void onTeamChanged() {
				updatePredictedResult();
			}
		});

		createPredictedResultLabels();
	}

	private void updatePredictedResult(){
		if(teamA != null && !teamA.isEmpty()
				&& teamB != null && !teamB.isEmpty()){
			try {
				predictedResult = new DecisionTreeModel().predictMatch(teamA, teamB);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createPredictedResultLabels() {
		add(new Label("goalsA", new Model(){
			@Override
			public Serializable getObject() {
				if(predictedResult!=null){
					return String.format("%.2f", predictedResult.goalsA);
				}
				return "?";
			}
		}));
		add(new Label("goalsB", new Model(){
			@Override
			public Serializable getObject() {
				if(predictedResult!=null){
					return String.format("%.2f", predictedResult.goalsB);
				}
				return "?";
			}
		}));
		add(new Label("goalsDiff", new Model(){
			@Override
			public Serializable getObject() {
				if(predictedResult!=null){
					return String.format("%.2f", predictedResult.goalsDiff);
				}
				return "?";
			}
		}));
		add(new Label("valueA", new Model(){
			@Override
			public Serializable getObject() {
				Float value = calcTeamSum(teamA);
				if(value!=null){
					return String.format("%.2f", value);
				}
				return "?";
			}
		}));
		add(new Label("valueB", new Model(){
			@Override
			public Serializable getObject() {
				Float value = calcTeamSum(teamB);
				if(value!=null){
					return String.format("%.2f", value);
				}
				return "?";
			}
		}));
		add(new Label("valueDiff", new Model(){
			@Override
			public Serializable getObject() {
				Float valueA = calcTeamSum(teamA);
				Float valueB = calcTeamSum(teamB);
				if(valueA!=null && valueB!=null){
					return String.format("%.2f", valueA-valueB);
				}
				return "?";
			}
		}));
	}

	private Float calcTeamSum(List<Player> team){
		if(team == null || team.isEmpty()){
			return null;
		}
		float sum = 0f;
		for (Player p : team) {
			sum += p.marketValueInMio;
		}
		return sum;
	}
}
