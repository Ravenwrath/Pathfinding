/*
	Simple Nodes that contain a Parent and a Position in the PlayGrid. They are Used by the A* Algorithm.
*/
public class PointNode implements Comparable{

	private PointNode Parent;
	private int[] Position = new int[2];

	public PointNode getParent() {
		return Parent;
	}

	public void setParent(PointNode parent) {
		Parent = parent;
	}
	
	public int[] getPosition() {
		return Position;
	}

	public void setPosition(int[] position) {
		Position = position;
	}
	
	public PointNode(PointNode p, int x, int y){
		setParent(p);
		int[] temp = new int[2];
		temp[0] = x;
		temp[1] = y;
		setPosition(temp);
		
	}

//PathLength Calculation----------------------------------------------------
			
	public int getF(){	//Paths length = estimated distance to target + current length @ Point
		int F=getH()+getG();
		return F;
	}
	
	public int getH(){	//Simple estimation of distance from point to target. No diagonal movement.
		int H=0;
		int Distance=0;
		Distance += Math.abs(AStarAlgorithmTest.getEnd()[0]-getPosition()[0]);
		Distance += Math.abs(AStarAlgorithmTest.getEnd()[1]-getPosition()[1]);
		Distance = Distance*10;
		
		return H;
	}
	
	public int getG(){	//Current distance traveled @ Point. Diagonal movement calculated.
		int G=0;
		if(getParent() != null){
			G += getParent().getG();
			int[] TempPp = getParent().getPosition();
			int[] TempPt = getPosition();
			int Distance=0;
			Distance += Math.abs(TempPp[0]-TempPt[0]);
			Distance += Math.abs(TempPp[1]-TempPt[1]);
			
			if(Distance==2){
				G += 14;
				
			}
			else{
				G += 10;
			}
			
			
		}
		
		return G;
	}
	
//-------------------------------------------------------------------------

	@Override						//CompareTo method to use Collections.sort compares
	public int compareTo(Object p) {			//PointNodes regarding their estimated PathLength from the  
		if(getF()<((PointNode) p).getF()){		//of the path through the point.
			return -1;
		}
		if(getF()>((PointNode) p).getF()){
			return 1;
		}
		else{
			return 0;
		}
	}
}
