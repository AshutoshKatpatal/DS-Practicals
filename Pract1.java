package rcoem.ds.lamport;

import java.util.HashMap;
import java.util.Scanner;

public class Pract1{

    int e[][] = new int[10][10];
    int en[][] = new int[10][10];
    int ev[] = new int[10];
    int i, p, j, k,input;
    HashMap<Integer, Integer> hm = new HashMap<>();

    public void lamport() {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Enter the number of process:");
        p = sc.nextInt();
        System.out.println("Enter the no of events per process:");
        for (i = 1; i <= p; i++) {
            System.out.println("Nunber of events in process "+i);
            ev[i] = sc.nextInt();
        }
        System.out.println("Enter the relationship:");
        for (i = 1; i <= p; i++) {
            System.out.println("For process: " + i);
            for (j = 1; j <= ev[i]; j++) {
                System.out.println("Event " + (i)+(j)+ " recieves from");
                input = sc.nextInt();
                k = i * 10 + j;
                hm.put(k, input);
                if (j == 1) {
                    en[i][j] = 1;
                }
            }
            
        }
        // calculation of clock time
        for (i = 0; i <= p; i++) {
            for (j = 1; j <= ev[i]; j++) {
                k = i * 10 + j;
                if (hm.get(k) == 0) {
                    en[i][j] = en[i][j - 1] + 1;
                } else {
                    int a = hm.get(k);
                    int p1 = a / 10;
                    int e1 = a % 10;
                    en[i][j]=Math.max(en[i][j-1]+1,en[p1][e1]+1);
                }
            }
        }
        for (i = 1; i <= p; i++) {
            for (j = 2; j <= ev[i]; j++) {
                k = i * 10 + j;
                if (hm.get(k) == 0) {
                    en[i][j] = en[i][j - 1] + 1;
                } else {
                    int a = hm.get(k);
                    int p1 = a / 10;
                    int e1 = a % 10;
                    en[i][j]=Math.max(en[i][j-1]+1,en[p1][e1]+1);
                }
            }
        }
        //printing time stamps
        System.out.println("Time stamps\n");
        for (i = 1; i <= p; i++) {
            for (j = 1; j <= ev[i]; j++) {
                System.out.print(en[i][j]+"\t");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {

        Pract1 p1 = new Pract1();
        p1.lamport();

    }
}