import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;

/*
	Main class that contains the A*Algorithm to find the Path on the PlayGrid.
	The Algorithm gets operated via console and bufferedReader:
		"setDimension:n" - changes Height and Width of the PlayGrid to n.
		"setStart:X,Y" - changes Startposition to X,Y.
		"setEnd:X,Y" - changes Endposition to X,y.
		"Start" - starts the A*Algorithm and shows the result.
		"Reset"	- Deletes every calculated Path from the PlayGrid.
		"quit" - quits. 
*/

public class AStarAlgorithmTest {
	static int[] Start = new int[2];	//contains the Coordinates of the Startposition
	static int[] End = new int[2];		//contains the Coordinates of the Endposition
	public static int[] getEnd(){
		return End;
	}
	
	public static void main(String args[]){

		PlayGrid Grid = new PlayGrid(10);	//Init of the PlayGrid with dimension 10.		
		
		for ( int i=0; i<Grid.getDimension()-1 ;i++){	//Blocks points of the PlayGrid
			int tempx = (int) (Math.random()*Grid.getDimension());
			int tempy = (int) (Math.random()*Grid.getDimension());
			Grid.blockTerrain(tempx, tempy);
		}
		
		
		System.out.println(Grid);

		InputStreamReader ir = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(ir);
		String CurLine = "";
		//Program-Loop
		while (!CurLine.equals("quit")){
			try {
				CurLine = br.readLine();
				//setDimension
				if(CurLine.length()>=13){	 
					if(CurLine.substring(0,13).equals("setDimension:")){
					
					Grid = new  PlayGrid(Integer.parseInt(CurLine.substring(13)));
					System.out.println("Dimension changed -> ");
					for ( int i=0; i<Grid.getDimension()-1 ;i++){
						int tempx = (int) (Math.random()*Grid.getDimension());
						int tempy = (int) (Math.random()*Grid.getDimension());
						Grid.blockTerrain(tempx, tempy);
					}
					System.out.println(Grid);
					
					
					}
				}
				
				if(CurLine.length()>=8){	
					//setStart(
					if(CurLine.substring(0,9).equals("setStart:")){
						
						String[] tempCoord = CurLine.substring(9).split(",");
						int[] intCoord = new int[2];
						for(int i=0; i<2;i++){
							intCoord[i] = Integer.parseInt(tempCoord[i],10);
						}
				
					
						if(!Grid.getTerrain()[intCoord[0]][intCoord[1]]){
						
							Start[0] = intCoord[0];
							Start[1] = intCoord[1];
							System.out.println("Startposition set to:["+Start[0]+"]"+"["+Start[1]+"]");
						}
					
						else{
						
							System.out.println("Error: Startposition blocked.");
					
						}		
				
					}
	
					//setEnd
					if(CurLine.substring(0,7).equals("setEnd:")){
						
						String[] tempCoord = CurLine.substring(7).split(",");
						int[] intCoord = new int[2];
						for(int i=0; i<2;i++){
							intCoord[i] = Integer.parseInt(tempCoord[i],10);
						}
				
					
						if(!Grid.getTerrain()[intCoord[0]][intCoord[1]]){
						
							End[0] = intCoord[0];
							End[1] = intCoord[1];
							System.out.println("Endposition set to:["+End[0]+"]"+"["+End[1]+"]");
						}
					
						else{
						
							System.out.println("Error: Endposition blocked.");
					
						}		
				
					}
				}
				
				
				//Reset
				if(CurLine.equals("Reset")){
					
					Grid.setPath(new boolean[Grid.getDimension()][Grid.getDimension()]);
					System.out.println("Reset Path -> ");
					System.out.println(Grid);
				}
				
				
				//A* Algorithm <--------- Point of Interest
				if(CurLine.equals("Start")){
					ArrayList<PointNode> OpenList = new ArrayList<PointNode>();
					ArrayList<PointNode> ClosedList = new ArrayList<PointNode>();
					OpenList.add(new PointNode(null,Start[0],Start[1]));
					boolean isFound;
					
					PointNode TempPN = OpenList.get(0);
					
					while(true){
						//Sorts PointNodes on OpenList by pathlength.
						Collections.sort(OpenList);
						
						//Moves PointNode with lowest pathlength from OpenList to ClosedList.
						TempPN = OpenList.remove(0);
						ClosedList.add(TempPN);
						
									
						
						//for-loops and if pick every adjacent PointNode of this PointNode
						for(int i=-1; i<2;i++){ 
							for( int j=-1; j<2; j++){
								if(TempPN.getPosition()[0]+i >= 0 
								&& TempPN.getPosition()[0]+i <= Grid.getDimension()-1
								&& TempPN.getPosition()[1]+j >= 0 
								&& TempPN.getPosition()[1]+j <= Grid.getDimension()-1
								&& (j!=0 || i!=0)){
									
									//WalkableCheck
									if(!Grid.getTerrain()[TempPN.getPosition()[0]+i][TempPN.getPosition()[1]+j]){
										
										//ClosedListCheck
										boolean isInCL = false;
										for(PointNode CL : ClosedList){
											if(CL.getPosition()[0] == TempPN.getPosition()[0]+i 
											&& CL.getPosition()[1] == TempPN.getPosition()[1]+j){
												isInCL = true;
												break;
											}
										}
										if(!isInCL){
											
											//OpenListCheck
											boolean isInOL = false;
											for(PointNode OL : OpenList){
												if(OL.getPosition()[0] == TempPN.getPosition()[0]+i 
												&& OL.getPosition()[1] == TempPN.getPosition()[1]+j){
													isInOL = true;
													if(TempPN.compareTo(OL) == 1){ 
														OL.setParent(TempPN);
													}
													break;
												}
											}
											
											if(!isInOL){
												OpenList.add(new PointNode(TempPN, TempPN.getPosition()[0]+i, TempPN.getPosition()[1]+j));
											}
										}
									}
								}
							}
						}
						//InteruptClauses
						if(TempPN.getPosition()[0] == End[0] && TempPN.getPosition()[1] == End[1]){
							isFound = true;
							break;	
							
						}
						else{
							if(OpenList.isEmpty()){
								isFound = false;
								break;
							}
						}
						
					}
			
					//After InteruptClause -> Check
					if(isFound){
						System.out.println("Path Found!");
						PointNode WayPoint = ClosedList.get(ClosedList.size()-1);
						while(WayPoint.getParent() != null){
							Grid.setWayPoint(WayPoint.getPosition()[0], WayPoint.getPosition()[1]);
							WayPoint = WayPoint.getParent();
							
						}
						Grid.setWayPoint(WayPoint.getPosition()[0], WayPoint.getPosition()[1]);
						System.out.println(Grid);
					}
					else{
						System.out.println("Path not Found!");
					}
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
	
	}
}
