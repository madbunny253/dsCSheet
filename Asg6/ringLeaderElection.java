import java.util.*;
public class RingLeaderElection {
	static int num_pr; // number of processes
	static int old_cord; // the failed coordinator or leader
	static int new_cord; // the new elected leader
	static int initiator; // the process that is holding the election
	static int isActive[];
	static int failed_process;
	static int arr[];
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of processes : ");
		num_pr = sc.nextInt();
		
		isActive = new int[num_pr + 1];
		for (int i = 1; i <= num_pr; i++) {
			isActive[i] = 1; 
		}
		old_cord = num_pr;
		isActive[old_cord] = 0;
		
		System.out.println("Enter the process that initiates the election process: ");
		initiator = sc.nextInt();
		System.out.println("Old Leader: " + old_cord + "\n");
		
		System.out.println("Enter the process that fails (other than the leader process), if none then enter 0 : ");
		failed_process = sc.nextInt();
		if (failed_process != 0) {
			isActive[failed_process] = 0;
		}

		new_cord = election_process(isActive, old_cord, initiator);
		
		System.out.println("\nFinally process " + new_cord + " became the new leader\n");
		for (int i = 1; i <= num_pr; i++) {
			if (isActive[i] == 1 && i != new_cord) {
				System.out.println("Process " + new_cord + " passes a Coordinator (" + new_cord + ") message to process " + i);
			}
		}
	}
	
	public static int election_process(int isActive[], int old_cord, int initiator) {
		System.out.println("\nThe election process is started by process " + initiator);
		int[] arr = new int[num_pr];
		int index = 0;
		int current = initiator;

		do {
		    if (isActive[current] == 1) {
		        System.out.println("Process " + current + " sends the Election message");
		        arr[index++] = current;
		        print_array(arr, index);
		    }
		    current = (current % num_pr) + 1;
		} while (current != initiator);

		int new_cord = 0;
		for (int i = 0; i < index; i++) {
		    if (arr[i] > new_cord) {
		        new_cord = arr[i];
		    }
		}

		return new_cord;
	}
	
	public static void print_array(int arr[], int size) {
		System.out.print("[ ");
		for (int i = 0; i < size; i++) {
			if (arr[i] == 0) continue;
				System.out.print(arr[i] + " ");
		}
		System.out.println("]");
	}
}
