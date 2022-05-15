package NDE;

public class Gen {
	private int vertex;
	private int depth;
	
	public Gen(){
		
	}
	public Gen(int v, int d){
		this.setVertex(v);
		this.setDepth(d);
	}
	
	public int getVertex() {
		return vertex;
	}
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
}
