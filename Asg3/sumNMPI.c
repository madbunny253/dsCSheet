#include <mpi.h>
#include <stdio.h>
#include <stdlib.h>

#define n 10

int a[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
int a2[1000];

int main(int argc, char* argv[]) {
    int pid, np, elements_per_process, n_elements_received;
    MPI_Status status;

    MPI_Init(&argc, &argv);
    MPI_Comm_rank(MPI_COMM_WORLD, &pid);
    MPI_Comm_size(MPI_COMM_WORLD, &np);

    if (pid == 0) {
        int index, i;
        elements_per_process = n / np;

        printf("Total elements: %d\n", n);
        printf("Total processes: %d\n", np);
        printf("Each process gets ~%d elements\n\n", elements_per_process);

        // Send data to other processes
        if (np > 1) {
            for (i = 1; i < np - 1; i++) {
                index = i * elements_per_process;

                MPI_Send(&elements_per_process, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
                MPI_Send(&a[index], elements_per_process, MPI_INT, i, 0, MPI_COMM_WORLD);

                printf("Sent %d elements to Process %d: ", elements_per_process, i);
                for (int j = 0; j < elements_per_process; j++)
                    printf("%d ", a[index + j]);
                printf("\n");
            }

            // Send remaining elements to last process
            index = i * elements_per_process;
            int elements_left = n - index;

            MPI_Send(&elements_left, 1, MPI_INT, i, 0, MPI_COMM_WORLD);
            MPI_Send(&a[index], elements_left, MPI_INT, i, 0, MPI_COMM_WORLD);

            printf("Sent %d elements to Process %d: ", elements_left, i);
            for (int j = 0; j < elements_left; j++)
                printf("%d ", a[index + j]);
            printf("\n");
        }

        // Master process work
        int sum = 0;
        printf("\nProcess 0 computing sum of its own elements: ");
        for (i = 0; i < elements_per_process; i++) {
            printf("%d ", a[i]);
            sum += a[i];
        }
        printf("\nPartial sum from Process 0: %d\n", sum);

        // Receive partial sums from other processes
        int tmp;
        for (i = 1; i < np; i++) {
            MPI_Recv(&tmp, 1, MPI_INT, MPI_ANY_SOURCE, 0, MPI_COMM_WORLD, &status);
            printf("Received partial sum %d from Process %d\n", tmp, status.MPI_SOURCE);
            sum += tmp;
        }

        printf("\nFinal Sum of Array (computed using MPI): %d\n", sum);
    } 
    else {
        MPI_Recv(&n_elements_received, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);
        MPI_Recv(&a2, n_elements_received, MPI_INT, 0, 0, MPI_COMM_WORLD, &status);

        printf("\nProcess %d received %d elements: ", pid, n_elements_received);
        for (int i = 0; i < n_elements_received; i++)
            printf("%d ", a2[i]);
        printf("\n");

        int partial_sum = 0;
        for (int i = 0; i < n_elements_received; i++)
            partial_sum += a2[i];

        printf("Process %d computed partial sum: %d\n", pid, partial_sum);
        MPI_Send(&partial_sum, 1, MPI_INT, 0, 0, MPI_COMM_WORLD);
    }

    MPI_Finalize();
    return 0;
}

