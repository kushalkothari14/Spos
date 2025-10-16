import java.util.*;

class Bankers {

    // Matrices and arrays used in Banker's Algorithm
    private int[][] need, allocate, max; // need = max - allocate
    private int[] avail; // Available resources
    private int np, nr;  // Number of processes and resources

    // Function to take input from user
    private void input() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Enter number of processes and resources:");
        np = sc.nextInt(); // total processes
        nr = sc.nextInt(); // total resources

        // Initialize matrices
        need = new int[np][nr];
        max = new int[np][nr];
        allocate = new int[np][nr];
        avail = new int[nr];

        // Taking Allocation matrix input
        System.out.println("Enter Allocation Matrix:");
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                allocate[i][j] = sc.nextInt();
            }
        }

        // Taking Maximum Need matrix input
        System.out.println("Enter Max Matrix:");
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                max[i][j] = sc.nextInt();
            }
        }

        // Taking Available resources input
        System.out.println("Enter Available Resources:");
        for (int j = 0; j < nr; j++) {
            avail[j] = sc.nextInt();
        }

        sc.close();
    }

    // Function to calculate Need matrix = Max - Allocation
    private void calc_need() {
        for (int i = 0; i < np; i++) {
            for (int j = 0; j < nr; j++) {
                need[i][j] = max[i][j] - allocate[i][j];
            }
        }
    }

    // Function to check if process i can be safely allocated resources
    private boolean check(int i) {
        for (int j = 0; j < nr; j++) {
            // If required resources > available, can't allocate
            if (avail[j] < need[i][j]) {
                return false;
            }
        }
        return true;
    }

    // Function to check if system is in a safe state
    public void isSafe() {
        input();        // Take input
        calc_need();    // Compute Need matrix

        boolean[] done = new boolean[np]; // Tracks if process is completed
        int count = 0;                    // Number of safely allocated processes

        // Repeat until all processes are allocated or deadlock occurs
        while (count < np) {
            boolean allocated = false;

            // Check each process
            for (int i = 0; i < np; i++) {
                if (!done[i] && check(i)) {
                    // If process can be safely allocated
                    for (int k = 0; k < nr; k++) {
                        avail[k] += allocate[i][k]; // Release resources after execution
                    }

                    System.out.println("Allocated Process: " + i);
                    done[i] = true;  // Mark process as completed
                    allocated = true;
                    count++;         // Increase count of completed processes
                }
            }

            // If no process could be allocated in this pass → unsafe state
            if (!allocated) {
                break;
            }
        }

        // Final check: all processes allocated → safe
        if (count == np) {
            System.out.println("\nSystem is in a SAFE state!");
        } else {
            System.out.println("\nSystem is NOT in a safe state!");
        }
    }

    // Main function to start program
    public static void main(String[] args) {
        new Bankers().isSafe();  // Create object and call isSafe
    }
}



// Enter number of processes and resources:
// 5 3
// Enter Allocation Matrix:
// 0 1 0
// 2 0 0
// 3 0 2
// 2 1 1
// 0 0 2
// Enter Max Matrix:
// 7 5 3
// 3 2 2
// 9 0 2
// 4 2 2
// 5 3 3
// Enter Available Resources:
// 3 3 2
// Allocated Process: 1
// Allocated Process: 3
// Allocated Process: 4
// Allocated Process: 0
// Allocated Process: 2


