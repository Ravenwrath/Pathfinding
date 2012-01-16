/*
	This Class is a PlayGrid for the AStarAlgorithm. It delivers a map the A* can refer to for blockades and
	a simple toString() method to view the results you get.
*/
public class PlayGrid {
	private int dimension;	//Size of the PlayGrid; Height and Length.
	
	private boolean[][] terrain;	//2Dimensional Boolean Array that contains blocked Terrain
	private boolean[][] path;	//2Dimensional Boolean Array that later contains the calculated Path.
	
	public PlayGrid(int n){				//Constructor sets dimension and inits terrain and path.
		setDimension(n);
		setTerrain(new boolean[n][n]);
		path = new boolean[n][n];
	}

	//Getter & Setter
	public int getDimension() {
		return dimension;
	}

	public void setDimension(int dimension) {
		this.dimension = dimension;
	}

	public boolean[][] getTerrain() {
		return terrain;
	}

	public void setTerrain(boolean[][] terrain) {
		this.terrain = terrain;
	}
	
	public void setPath(boolean[][] path) {
		this.path = path;
	}
	
	//--------------------------------------------

	@Override			//toString method that creates a String 
	public String toString(){	//with a visualisation of the current PlayGrid
		String out = "";
		for( int j=0; j<getDimension(); j++){
			String Temp= "";
			for( int i=0; i<getDimension(); i++){
				if(terrain[i][getDimension()-j-1]){
					Temp += "X ";	//Blocked Terrain
				}
				else{
					if(path[i][getDimension()-j-1]){
						Temp += "W "; //Path
					}
					else{
						Temp += "_ "; //Normal Field
					}
					
				}
				
			}
			out += "\n"+Temp;
		}
		return out;
	}
	
	public void blockTerrain(int x, int y){
		terrain[x][y] = true;
	}
	
	public void setWayPoint(int x, int y){
		path[x][y] = true;
	}
	
	public void removeWayPoint(int x, int y){
		path[x][y] = false;
	}
}

