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
	
	
	
	public WorldCup() {
		GenerateTeam gT=new GenerateTeam();
		this.teams =gT.getTeams();
		ranking = new RankingWorldCup(teams);
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
				GameSimulation gameTest = new GameSimulation(game);
				gameTest.play();
				System.out.println(game.getTeam1().getName()+" VS "+game.getTeam2().getName());
				System.out.println("	"+game.getTeam1().getName()+" : "+game.getScore1());
				System.out.println("	"+game.getTeam2().getName()+" : "+game.getScore2());
				
				if(game.getScore1()>game.getScore2()) {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam1().getName());
					ranking.addPointWinningTeam(game.getTeam1().getName(),1);
				}
				else {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam2().getName());
					ranking.addPointWinningTeam(game.getTeam2().getName(),1);
				}
					
			}
		
		 System.out.println(pool.getValue().getName()+"\n	"+pool.getValue().getRanking().toString());
		}
	}
	
	public void simulateGameGroupStage2() {
		/////////////////////////////  SIMULATE GAME GROUPSTAGE 2////////////////////////////////////////////
		for (HashMap.Entry<String, GroupStage> pool : groupStage2.entrySet()) {
			ArrayList<Game> listGames = pool.getValue().getGames();
			for(Game game : listGames) {
				GameSimulation gameTest = new GameSimulation(game);
				gameTest.play();
				System.out.println(game.getTeam1().getName()+" VS "+game.getTeam2().getName());
				System.out.println("	"+game.getTeam1().getName()+" : "+game.getScore1());
				System.out.println("	"+game.getTeam2().getName()+" : "+game.getScore2());
				
				if(game.getScore1()<game.getScore2()) {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam1().getName());
					ranking.addPointWinningTeam(game.getTeam2().getName(),3);
				}
				else {
					pool.getValue().getRanking().addPointWinningTeam(game.getTeam2().getName());
					ranking.addPointWinningTeam(game.getTeam1().getName(),3);
				}
				
			}
			
			System.out.println(pool.getValue().getName()+"\n	"+pool.getValue().getRanking().toString());
		}
	}
	
	
	public void simulationQuarterFinal() {
		Team team1 = groupStage2.get("GROUPE I").getRanking().getRankingTeams()[0];
		Team team2 = groupStage2.get("GROUPE J").getRanking().getRankingTeams()[1];
		Game quarterGame1 = new Game(team1,team2);
		team1 = groupStage2.get("GROUPE K").getRanking().getRankingTeams()[0];
		team2 = groupStage2.get("GROUPE L").getRanking().getRankingTeams()[1];
		Game quarterGame2 = new Game(team1,team2);
		
		GameSimulation gameTest = new GameSimulation(quarterGame1);
		gameTest.play();
		System.out.println(quarterGame1.toString());
	
		GameSimulation gameTest2 = new GameSimulation(quarterGame2);
		gameTest2.play();
		System.out.println(quarterGame2.toString());
		
		Team teamWin1 ,teamWin2;
		if(quarterGame1.getScore1()<quarterGame1.getScore2()) {
			teamWin1=quarterGame1.getTeam2();
			ranking.addPointWinningTeam(quarterGame1.getTeam2().getName(),5);
		}
		else {
			teamWin1=quarterGame1.getTeam1();
			ranking.addPointWinningTeam(quarterGame1.getTeam1().getName(),5);
		}
		
		if(quarterGame2.getScore1()<quarterGame2.getScore2()) {
			teamWin2=quarterGame2.getTeam2();
			ranking.addPointWinningTeam(quarterGame2.getTeam2().getName(),5);
		}
		else {
			teamWin2=quarterGame2.getTeam1();
			ranking.addPointWinningTeam(quarterGame2.getTeam1().getName(),5);
		}
		Game semiGame1 = new Game(teamWin1,teamWin2);
		
		team1 = groupStage2.get("GROUPE J").getRanking().getRankingTeams()[0];
		team2 = groupStage2.get("GROUPE I").getRanking().getRankingTeams()[1];
		Game quarterGame3 = new Game(team1,team2);
		team1 = groupStage2.get("GROUPE L").getRanking().getRankingTeams()[0];
		team2 = groupStage2.get("GROUPE K").getRanking().getRankingTeams()[1];
		Game quarterGame4 = new Game(team1,team2);
		
		GameSimulation gameTest3 = new GameSimulation(quarterGame3);
		gameTest3.play();
		System.out.println(quarterGame3.toString());
		
		GameSimulation gameTest4 = new GameSimulation(quarterGame4);
		gameTest4.play();
		System.out.println(quarterGame4.toString());
		
		Team teamWin3 ,teamWin4;
		if(quarterGame3.getScore1()<quarterGame3.getScore2()) {
			teamWin3=quarterGame3.getTeam2();
			ranking.addPointWinningTeam(quarterGame3.getTeam2().getName(),5);
		}
		else {
			teamWin3=quarterGame3.getTeam1();
			ranking.addPointWinningTeam(quarterGame3.getTeam1().getName(),5);
		}
		
		if(quarterGame4.getScore1()<quarterGame4.getScore2()) {
			teamWin4=quarterGame4.getTeam2();
			ranking.addPointWinningTeam(quarterGame4.getTeam2().getName(),5);
		}
		else {
			teamWin4=quarterGame4.getTeam1();
			ranking.addPointWinningTeam(quarterGame4.getTeam1().getName(),5);
		}
		Game semiGame2 = new Game(teamWin3,teamWin4);
		
		semiFinal.add(semiGame1);
		semiFinal.add(semiGame2);
		
		
	}
	

	
	
	public void simulateSemiFinal() {
		
		Team teamWin1, teamWin2, teamLose1, teamLose2;
		
		Game semiFinal1 = semiFinal.get(0);
		GameSimulation gameTest = new GameSimulation(semiFinal1);
		gameTest.play();
		System.out.println(semiFinal1.toString());
		
		if(semiFinal1.getScore1()<semiFinal1.getScore2()) {
			teamWin1=semiFinal1.getTeam2();
			teamLose1=semiFinal1.getTeam1();
			ranking.addPointWinningTeam(semiFinal1.getTeam2().getName(),5);
		}
		else {
			teamWin1=semiFinal1.getTeam1();
			teamLose1=semiFinal1.getTeam2();
			ranking.addPointWinningTeam(semiFinal1.getTeam1().getName(),5);
		}
		
		Game semiFinal2 = semiFinal.get(1);
		GameSimulation gameTest2 = new GameSimulation(semiFinal2);
		gameTest2.play();
		System.out.println(semiFinal2.toString());
		
		if(semiFinal2.getScore1()<semiFinal2.getScore2()) {
			teamWin2=semiFinal2.getTeam2();
			teamLose2=semiFinal2.getTeam1();
			ranking.addPointWinningTeam(semiFinal2.getTeam2().getName(),5);
		}
		else {
			teamWin2=semiFinal2.getTeam1();
			teamLose2=semiFinal2.getTeam2();
			ranking.addPointWinningTeam(semiFinal2.getTeam1().getName(),5);
		}
		
		finalGame = new Game(teamWin1, teamWin2);
		smallFinalGame= new Game(teamLose1, teamLose2);
		
	}
	
	public void simulateSmallFinal() {
		GameSimulation gameTest = new GameSimulation(smallFinalGame);
		gameTest.play();
		System.out.println(smallFinalGame.toString());
		if(smallFinalGame.getScore1()<smallFinalGame.getScore2()) {
			ranking.addPointWinningTeam(smallFinalGame.getTeam2().getName(),6);
			
		}
		else {
			System.err.println("WINNER : "+finalGame.getTeam1().getName());
			ranking.addPointWinningTeam(finalGame.getTeam1().getName(),6);
			
		}
	}
	
	
	public void simulateFinal() {
		GameSimulation gameTest = new GameSimulation(finalGame);
		gameTest.play();
		System.out.println(finalGame.toString());
		if(finalGame.getScore1()<finalGame.getScore2()) {
			System.err.println("WINNER : "+finalGame.getTeam2().getName());
			ranking.addPointWinningTeam(finalGame.getTeam2().getName(),15);
			ranking.addPointWinningTeam(finalGame.getTeam1().getName(),8);
		}
		else {
			System.err.println("WINNER : "+finalGame.getTeam1().getName());
			ranking.addPointWinningTeam(finalGame.getTeam1().getName(),15);
			ranking.addPointWinningTeam(finalGame.getTeam2().getName(),8);
		}
	}

	
	
	
	
	
}

