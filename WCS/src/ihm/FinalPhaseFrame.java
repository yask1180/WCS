package ihm;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import data.Game;
import data.WorldCup;
import ihm.Phase2Frame.Phase1;
import ihm.Phase2Frame.PhaseFinale;
import ihm.Phase2Frame.Teams;


public class FinalPhaseFrame extends JFrame {
	private WorldCup worldCup;
	private JFrame windows;
	private Container contentPane;
	private JPanel panel = new JPanel();
	private JButton phase1Button=new JButton("Phase 1");
	private JButton phase2Button=new JButton("Phase 2");
	private JButton phaseFinalButton=new JButton("Phase Finale");
	private JButton teamsButton=new JButton("Equipe");
	public FinalPhaseFrame(WorldCup worldCup, String windowsTitle) {
		super(windowsTitle);
		setSize(1300,850);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
		setVisible(true);
		this.worldCup=worldCup;
		contentPane=getContentPane();
		windows=this;
		initLayout();
	}
	
	public void initLayout() {
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);
		

		phase1Button.setBounds(150, 50, 200, 25);
		phase2Button.setBounds(400, 50, 200, 25);
		phaseFinalButton.setBounds(650, 50, 200, 25);
		teamsButton.setBounds(900, 50, 200, 25);
		
		panel.add(phase1Button);
		panel.add(phase2Button);
		panel.add(phaseFinalButton);
		panel.add(teamsButton);
		
		initAction();
		
		JPanel pane2 =new TableauPhaseFinal(worldCup);
		pane2.setBounds(100, 150, 1200, 800);
		panel.add(pane2);
		
		
		contentPane.add(panel);
	}
	
	public void initAction() {
		phase1Button.addActionListener(new Phase1());
		phase2Button.addActionListener(new Phase2());
		teamsButton.addActionListener(new Teams());
	}
	
	
	public class Phase1 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			windows.dispose();
			new Phase1Frame(worldCup,"WCS");
		}
	}
	
	public class Phase2 implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			windows.dispose();
			new Phase2Frame(worldCup,"WCS");
		}
	}
	
	
	public class Teams implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			windows.dispose();
			new ListTeamsFrame("TEAMS",worldCup);
		}
	}
	
	
	
	class TableauPhaseFinal extends JPanel{

		WorldCup worldCup;
		private ArrayList<Game> quarterFinal = new ArrayList<Game>();
		private ArrayList<Game> semiFinal = new ArrayList<Game>();
		private Game finalGame;
		private Game smallFinalGame;
		public TableauPhaseFinal(WorldCup worldCup) {
			this.worldCup=worldCup;
			quarterFinal=worldCup.getQuarterFinal();
			semiFinal=worldCup.getSemiFinal();
			finalGame=worldCup.getFinalGame();
			smallFinalGame=worldCup.getSmallFinalGame();
		}
		 public void paint(Graphics g) {  
			  
			 Graphics2D g2 = (Graphics2D)g;
			 
			 Image icone2;
			 int k=0;
			 
			 for(int i = 0;i<2;i++) {
				 for(int j = 0 ;j<2;j++) {
					 Game quarterGame=quarterFinal.get(k);
					 try {
							icone2 = ImageIO.read(new File("score.jpg"));
							g2.drawImage(icone2,0 +(j*800),0+(i*400),250,43,this);
									
						}catch(IOException exc){
								exc.printStackTrace();
						}
					 g2.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
					 //g2.setColor(Color.blue);
					 g2.setColor(Color.black);
					 g2.drawString(quarterGame.getTeam1().getName(), 5 +(j*800), 25+(i*400));
					 g2.drawString(quarterGame.getTeam2().getName(), 170+(j*800), 25+(i*400));
					 g2.setColor(Color.white);
					 g2.drawString(""+quarterGame.getScore1(), 83+(j*800), 25+(i*400));
					 g2.drawString(""+quarterGame.getScore2(), 150+(j*800), 25+(i*400));
					 k++;
				 }
			 }
			 
			 for(int i2= 0;i2<2;i2++) {
				 Game semiGame=semiFinal.get(i2);
				 try {
						icone2 = ImageIO.read(new File("score.jpg"));
						g2.drawImage(icone2,100 +(i2*610),200,250,43,this);
								
				}catch(IOException exc){
							exc.printStackTrace();
				}
				 g2.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
				 //g2.setColor(Color.blue);
				 g2.setColor(Color.black);
				 g2.drawString(semiGame.getTeam1().getName(), 105+(i2*610), 225);
				 g2.drawString(semiGame.getTeam2().getName(), 270+(i2*610), 225);
				 g2.setColor(Color.white);
				 g2.drawString(""+semiGame.getScore1(), 183+(i2*610), 225);
				 g2.drawString(""+semiGame.getScore2(), 250+(i2*610), 225);
				 k++;
				 
			 }
			 
			 
			 try {
					icone2 = ImageIO.read(new File("score.jpg"));
					g2.drawImage(icone2,400,200,250,43,this);
							
			}catch(IOException exc){
						exc.printStackTrace();
			}
			 
			 g2.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
			 //g2.setColor(Color.blue);
			 g2.setColor(Color.black);
			 g2.drawString(finalGame.getTeam1().getName(), 405, 225);
			 g2.drawString(finalGame.getTeam2().getName(), 575, 225);
			 g2.setColor(Color.white);
			 g2.drawString(""+finalGame.getScore1(), 483, 225);
			 g2.drawString(""+finalGame.getScore2(), 550, 225);
			 
			 g2.setStroke(new BasicStroke(10));
			 g2.setColor(new Color(239,144,52));
			 g2.drawLine(125, 43, 125, 121);
			 g2.drawLine(125, 400, 125, 322);
			 g2.drawLine(925, 43, 925, 121);
			 g2.drawLine(925, 400, 925, 322);
			 
			 g2.drawLine(125, 121, 220, 121);
			 g2.drawLine(125, 322, 220, 322);
			 g2.drawLine(925, 121, 830, 121);
			 g2.drawLine(925, 322, 830, 322);
			 
			 g2.drawLine(220, 121, 220, 201);
			 g2.drawLine(830, 121, 830, 201);
			 g2.drawLine(220, 322, 220, 242);
			 g2.drawLine(830, 322, 830, 242);
			 
			 g2.drawLine(350, 222, 400, 222);
			 g2.drawLine(650, 222, 710, 222);
			 
			 try {
					icone2 = ImageIO.read(new File("score.jpg"));
					g2.drawImage(icone2,400,300,250,43,this);
							
			}catch(IOException exc){
						exc.printStackTrace();
			}
			 
			 g2.setFont(new Font("TimesRoman", Font.BOLD, 15)); 
			 //g2.setColor(Color.blue);
			 g2.setColor(Color.black);
			 g2.drawString(smallFinalGame.getTeam1().getName(), 405, 325);
			 g2.drawString(smallFinalGame.getTeam2().getName(), 575, 325);
			 g2.setColor(Color.white);
			 g2.drawString(""+smallFinalGame.getScore1(), 483, 325);
			 g2.drawString(""+smallFinalGame.getScore2(), 550, 325);
			 
			 
			 
			 
			
			
		 }
	}

	public class Back implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			windows.dispose();
			new Phase2Frame(worldCup,"WCS");
			
			
		   
			
		}
	}
}
