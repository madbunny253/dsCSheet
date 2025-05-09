import java.util.*;

public class LeaderElection {
	static int n;
	static int oldC;
	static int newC;
	static int currE;
	static int failedP;
	static int isActive[];
	
	public static void main(String []args) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter number of processes: ");
		n = sc.nextInt();
		
		isActive = new int[n+1];
		for(int i=1; i<=n; i++) {
			isActive[i]=1;
		}
		oldC = n;
		isActive[oldC]=0;
		
		System.out.println("Election initiator: ");
		currE = sc.nextInt();
		System.out.println("Old Leader: " + oldC + "\n");
		
		System.out.println("Enter process that fails except old leader: ");
		failedP = sc.nextInt();
		if(failedP != 0) {
			isActive[failedP] = 0;
		}
		
		newC = election(isActive, oldC, currE);
		
		System.out.println("Finally process " + newC + " became the new leader\n");
		for (int i = 1; i <= n; i++) {
			if (isActive[i] == 1 && i != newC) {
				System.out.println("Process " + newC + " passes a Coordinator (" + newC + ") message to process " + i);
			}
		}
		sc.close();
	}
	
	public static int election(int isActive[], int oldC, int currE) {
		int higherP = currE;
		int ele = 1;
		for(int i=currE; i<=n; i++) {
			if(isActive[i] == 1) {
				for(int j=i+1; j<=n; j++) {
					if(isActive[j] == 1) {
						System.out.println("Election(" + ele + ") - Process " + i + " passes message to process (" + j + ")");
					}
				}
				ele++;
				for(int j=i+1; j<=n; j++) {
					if(isActive[j] == 1) {
						System.out.println("Process " + j + " passes OK message to process (" + i + ")");
						if (higherP < j)
							higherP = j;
					}
				}
				System.out.println();
			}
		}
		return higherP;
	}
}
