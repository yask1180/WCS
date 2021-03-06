package data;

import java.util.ArrayList;
import java.util.HashMap;

import generate.GenerateGroupStage;
import generate.GenerateTeam;
import process.GameSimulation;

/**
 * It is a data class which named "WorldCup"
 * This class constructs the object which 
 * @author WCS Corporation
 *
 */
public class WorldCup {

	/**
	 * 
	 */
	private HashMap<String, GroupStage> groupStage1; 
	private HashMap<String, GroupStage> groupStage2; 
	private ArrayList<Game> quarterFinal = new ArrayList<Game>();
	private ArrayList<Game> semiFinal = new ArrayList<Game>();
	private Game finalGame;
	private Game smallFinalGame;
	private ArrayList<Team> teams;
	private RankingWorldCup ranking;
	private GameSimulation gameSimulation = new GameSimulation();
	
	
	
	public WorldCup() {
		GenerateTeam gT=new GenerateTeam();
		this.teams =gT.getTeams();
		ranking = new RankingWorldCup(teams);
	}
	
	
	
	
	
	
	/**
	 * @return the teams
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}






	/**
	 * @param teams the teams to set
	 */
	public void setTeams(ArrayList<Team> teams) {
		this.teams = teams;
	}






	/**
	 * @return the ranking
	 */
	public RankingWorldCup getRanking() {
		return ranking;
	}




	public void creationGroupStage1() {
		/////////////////////////////// CREATION GROUPSTAGE 1//////////////////////////////////////////////
		GenerateGroupStage generateGroupStage=new GenerateGroupStage(teams);
		groupStage1=generateGroupStage.generateGroupStage1();
		
	}
	
	public void creationGroupStage2() {
		//////////////////////////////////GET TEAM QUALIFIED GROUPSTAGE 2 ///////////////////////////////
		ArrayList<Team> teamQualified=new ArrayList<Team>();
		for (HashMap.Entry<String, GroupStage> pool : groupStage1.entrySet()) {
			Team team1 = pool.getValue().getRanking().getRankingTeams()[0];
			Team team2 = pool.getValue().getRanking().getRankingTeams()[1];
			teamQualified.add(team1);
			teamQualified.add(team2); 
		
		}
		/////////////////////////////// CREATION GROUPSTAGE 2//////////////////////////////////////////////
		GenerateGroupStage generateGroupStage=new GenerateGroupStage(teamQualified);
		groupStage2 = generateGroupStage.generateGroupStage2();
		
	}
	
	public void simulateGameGroupStage1() {
		/////////////////////////////  SIMULATE GAME GROUPSTAGE 1////////////////////////////////////////////
		for (HashMap.Entry<String, GroupStage> pool : groupStage1.entrySet()) {
			ArrayList<Game> listGames = pool.getValue().getGames();
			for(Game game : listGames) {
				
				gameSimulation.play(game);
				//System.out.println(game.toString());
				
				if(game.getScore1()>game.getScore2()) {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam1().getName());
					ranking.addPointWinningTeam(game.getTeam1().getName(),1);
				}
				else {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam2().getName());
					ranking.addPointWinningTeam(game.getTeam2().getName(),1);
				}
					
			}
		
		 //System.out.println(pool.getValue().getName()+"\n	"+pool.getValue().getRanking().toString());
		}
	}
	
	public void simulateGameGroupStage2() {
		/////////////////////////////  SIMULATE GAME GROUPSTAGE 2////////////////////////////////////////////
		for (HashMap.Entry<String, GroupStage> pool : groupStage2.entrySet()) {
			ArrayList<Game> listGames = pool.getValue().getGames();
			for(Game game : listGames) {
				
				gameSimulation.play(game);
				//System.out.println(game.getTeam1().getName()+" VS "+game.getTeam2().getName());
				//System.out.println("	"+game.getTeam1().getName()+" : "+game.getScore1());
				//System.out.println("	"+game.getTeam2().getName()+" : "+game.getScore2());
				
				if(game.getScore1()<game.getScore2()) {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam2().getName());
					ranking.addPointWinningTeam(game.getTeam2().getName(),4);
				}
				else {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam1().getName());
					ranking.addPointWinningTeam(game.getTeam1().getName(),4);
				}
				
			}
			
			//System.out.println(pool.getValue().getName()+"\n	"+pool.getValue().getRanking().toString());
		}
	}
	
	
	public void simulationQuarterFinal() {
		Team team1 = groupStage2.get("GROUPE I").getRanking().getRankingTeams()[0];
		Team team2 = groupStage2.get("GROUPE J").getRanking().getRankingTeams()[1];
		Game quarterGame1 = new Game(team1,team2);
		team1 = groupStage2.get("GROUPE K").getRanking().getRankingTeams()[0];
		team2 = groupStage2.get("GROUPE L").getRanking().getRankingTeams()[1];
		Game quarterGame2 = new Game(team1,team2);
		
		quarterFinal.add(quarterGame1);
		quarterFinal.add(quarterGame2);
		
		gameSimulation.play(quarterGame1);
		//System.out.println(quarterGame1.toString());
	
	
		gameSimulation.play(quarterGame2);
		//System.out.println(quarterGame2.toString());
		
		Team teamWin1 ,teamWin2;
		if(quarterGame1.getScore1()<quarterGame1.getScore2()) {
			teamWin1=quarterGame1.getTeam2();
			ranking.addPointWinningTeam(quarterGame1.getTeam2().getName(),16);
		}
		else {
			teamWin1=quarterGame1.getTeam1();
			ranking.addPointWinningTeam(quarterGame1.getTeam1().getName(),16);
		}
		
		if(quarterGame2.getScore1()<quarterGame2.getScore2()) {
			teamWin2=quarterGame2.getTeam2();
			ranking.addPointWinningTeam(quarterGame2.getTeam2().getName(),16);
		}
		else {
			teamWin2=quarterGame2.getTeam1();
			ranking.addPointWinningTeam(quarterGame2.getTeam1().getName(),16);
		}
		Game semiGame1 = new Game(teamWin1,teamWin2);
		
		team1 = groupStage2.get("GROUPE J").getRanking().getRankingTeams()[0];
		team2 = groupStage2.get("GROUPE I").getRanking().getRankingTeams()[1];
		Game quarterGame3 = new Game(team1,team2);
		team1 = groupStage2.get("GROUPE L").getRanking().getRankingTeams()[0];
		team2 = groupStage2.get("GROUPE K").getRanking().getRankingTeams()[1];
		Game quarterGame4 = new Game(team1,team2);
		
		quarterFinal.add(quarterGame3);
		quarterFinal.add(quarterGame4);
		
		gameSimulation.play(quarterGame3);
		//System.out.println(quarterGame3.toString());
		
		
		gameSimulation.play(quarterGame4);
		//System.out.println(quarterGame4.toString());
		
		Team teamWin3 ,teamWin4;
		if(quarterGame3.getScore1()<quarterGame3.getScore2()) {
			teamWin3=quarterGame3.getTeam2();
			ranking.addPointWinningTeam(quarterGame3.getTeam2().getName(),16);
		}
		else {
			teamWin3=quarterGame3.getTeam1();
			ranking.addPointWinningTeam(quarterGame3.getTeam1().getName(),16);
		}
		
		if(quarterGame4.getScore1()<quarterGame4.getScore2()) {
			teamWin4=quarterGame4.getTeam2();
			ranking.addPointWinningTeam(quarterGame4.getTeam2().getName(),16);
		}
		else {
			teamWin4=quarterGame4.getTeam1();
			ranking.addPointWinningTeam(quarterGame4.getTeam1().getName(),16);
		}
		Game semiGame2 = new Game(teamWin3,teamWin4);
		
		semiFinal.add(semiGame1);
		semiFinal.add(semiGame2);
		
		
	}
	

	
	
	public void simulateSemiFinal() {
		
		Team teamWin1, teamWin2, teamLose1, teamLose2;
		
		Game semiFinal1 = semiFinal.get(0);
	
		gameSimulation.play(semiFinal1);
		//System.out.println(semiFinal1.toString());
		
		if(semiFinal1.getScore1()<semiFinal1.getScore2()) {
			teamWin1=semiFinal1.getTeam2();
			teamLose1=semiFinal1.getTeam1();
			ranking.addPointWinningTeam(semiFinal1.getTeam2().getName(),16);
		}
		else {
			teamWin1=semiFinal1.getTeam1();
			teamLose1=semiFinal1.getTeam2();
			ranking.addPointWinningTeam(semiFinal1.getTeam1().getName(),16);
		}
		
		Game semiFinal2 = semiFinal.get(1);

		gameSimulation.play(semiFinal2);
		//.out.println(semiFinal2.toString());
		
		if(semiFinal2.getScore1()<semiFinal2.getScore2()) {
			teamWin2=semiFinal2.getTeam2();
			teamLose2=semiFinal2.getTeam1();
			ranking.addPointWinningTeam(semiFinal2.getTeam2().getName(),16);
		}
		else {
			teamWin2=semiFinal2.getTeam1();
			teamLose2=semiFinal2.getTeam2();
			ranking.addPointWinningTeam(semiFinal2.getTeam1().getName(),16);
		}
		
		finalGame = new Game(teamWin1, teamWin2);
		smallFinalGame= new Game(teamLose1, teamLose2);
		
	}
	
	public void simulateSmallFinal() {
		
		gameSimulation.play(smallFinalGame);
		//System.out.println(smallFinalGame.toString());
		if(smallFinalGame.getScore1()<smallFinalGame.getScore2()) {
			ranking.addPointWinningTeam(smallFinalGame.getTeam2().getName(),35);
			
		}
		else {
			ranking.addPointWinningTeam(finalGame.getTeam1().getName(),35);
			
		}
	}
	
	
	public void simulateFinal() {
		
		gameSimulation.play(finalGame);
		//System.out.println(finalGame.toString());
		if(finalGame.getScore1()<finalGame.getScore2()) {
			//System.out.println("WINNER : "+finalGame.getTeam2().getName());
			ranking.addPointWinningTeam(finalGame.getTeam2().getName(),100);
			ranking.addPointWinningTeam(finalGame.getTeam1().getName(),60);
		}
		else {
			//System.out.println("WINNER : "+finalGame.getTeam1().getName());
			ranking.addPointWinningTeam(finalGame.getTeam1().getName(),100);
			ranking.addPointWinningTeam(finalGame.getTeam2().getName(),60);
		}
	}






	/**
	 * @return the groupStage1
	 */
	public HashMap<String, GroupStage> getGroupStage1() {
		return groupStage1;
	}






	/**
	 * @return the groupStage2
	 */
	public HashMap<String, GroupStage> getGroupStage2() {
		return groupStage2;
	}






	/**
	 * @return the quarterFinal
	 */
	public ArrayList<Game> getQuarterFinal() {
		return quarterFinal;
	}






	/**
	 * @return the semiFinal
	 */
	public ArrayList<Game> getSemiFinal() {
		return semiFinal;
	}






	/**
	 * @return the finalGame
	 */
	public Game getFinalGame() {
		return finalGame;
	}






	/**
	 * @return the smallFinalGame
	 */
	public Game getSmallFinalGame() {
		return smallFinalGame;
	}






	/**
	 * @return the gameSimulation
	 */
	public GameSimulation getGameSimulation() {
		return gameSimulation;
	}
	
	

	
	
	
	
	
}

