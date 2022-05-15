package NDE;

public class Node {
	public int label;
	public double x;
	public double y;
	public double z;
	
	public Node(){
		
	}
	public Node(int label){
		this.label = label;
	}
	public Node(int label, double x, double y, double z){
		this.label = label;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	static public double distance(Node a, Node b){
		double dis;
		dis = Math.sqrt((a.x-b.x)*(a.x-b.x) + (a.y-b.y)*(a.y-b.y) + (a.z-b.z)*(a.z-b.z));
		return dis;
	}
	public void printNode(){
		System.out.println(this.label + "\n" +"x: " + this.x + " y: " + this.y + " z: " + this.z);
	}
//	public static void main(String[] args) {
//		Node a = new Node(1, 1.5, 1, 1.9);
//		Node b = new Node(2,2.2,2.4,-2.4);
//		System.out.println(distance(a, b));
//	}
}
