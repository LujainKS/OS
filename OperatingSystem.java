/*  OperatingSystem: This class represented the interface
 * of the program, it controls the flow of the queues,
 * take user input, and format the final output. Also, it
 * contains global variables that are used to calculate some
 * values such as average waiting time and throughput. */

import java.util.ArrayList;
import java.util.Scanner;

public class OperatingSystem {
    
    static ArrayList<PCB> processesArray = new ArrayList<>(); //TO STORE ALL CREATED PROCESSES

    static int numberOfProcesses; //USER INPUT
    static int processBurstTime;  //USER INPUT
    static int globalTimeTracker = 0; //TO TRACK THE END TIME OF EACH PROCESS
    static double totalWaitingTime = 0; //TO CALCULATE THE AVERAGE WAITING TIME
    static int numberOfCompletedProcessesTracker = 0; //TO CALCULATE THE THROUGHOUT AT THE END OF EACH QUEUE

    static String space = " "; //TO CONTROL THE OUTPUT FORMAT


    public static void main(String[] args) {

        headerPrinter();
        numberOfProcessesGetter();
        burstTimeGetter();
        System.out.println("\n\t\t\t****Processes Info before Entering the Ready Queue****");
        processesDetailsPrinter();

        Algorithm.Q0();
        Algorithm.Q1();
        Algorithm.Q2();

        System.out.println("\nThank You For Using Multilevel-Feedback-Queue Scheduler Program :)! ");

    }

    public static void headerPrinter()
    {
        System.out.println(" ************************************************************************************************************* ");
        System.out.println(" ************************************ Multilevel-Feedback-Queue Scheduler ************************************ ");
        System.out.println(" ************************************************************************************************************* \n");
    }// end of headerPrinter method

    
    public static void numberOfProcessesGetter()
    {
        Scanner input = new Scanner(System.in);
        try { //In case the user entered characters
            do
            {
                System.out.print(" How many processes do you want to create? ");
                numberOfProcesses = input.nextInt();
                System.out.println(" ------------------------------------------- ");
            } while(!numberOfProcessValidator(numberOfProcesses)); //If numberOfProcessValidator() returned TRUE, the entered value is positive

        } catch (Exception e)
        {
            System.out.println(" ------------------------------------------- ");
            System.out.println(" Wrong Entry! \n Only Numbers Are Allowed");
            numberOfProcessesGetter();
        }
    }// end of inputGetter method


    public static void burstTimeGetter() {
        //To Have A control In case The User Entered Characters
        boolean continueFlag = false;

        Scanner input = new Scanner(System.in);

        for (int i = 1; i <= numberOfProcesses; i++) {

            do {
                continueFlag = false;

                try { //In case the user entered characters
                    System.out.print(" Enter the Burst Time of Process #" + i + ": ");
                    processBurstTime = input.nextInt();
                }  catch (Exception e)
                {
                    System.out.println(" ------------------------------------------- ");
                    System.out.println(" Wrong Entry! \n Only Numbers Are Allowed");
                    input.next();
                    continueFlag = true;
                }

            } while (continueFlag || !processBurstTimeValidator(processBurstTime)); //If processBurstTimeValidator() returned TRUE, the entered value is positive


            PCB pcb = new PCB(i, 0, processBurstTime);
            processesArray.add(pcb);
        }//end of the for loop block

    }//end of burstTimeGetter method




    public static void processesDetailsPrinter()
    {
        System.out.println("\nProcess ID  | Arrival Time |  Burst Time  |   Remaining Time  |   End Time  ");
        System.out.println("--------------------------------------------------------------------------------");
        for (PCB pcb: processesArray)
        {
            System.out.println("\t" + pcb.getPId() + space.repeat(12-4-(countDig(pcb.getPId()))) + "|" +
                            "\t" + pcb.getArrivalTime() + space.repeat(15-4-(countDig(pcb.getArrivalTime()))) + "|" +
                            "\t" + pcb.getBurstTime() + space.repeat(14-4-(countDig(pcb.getBurstTime()))) + "|" +
                            "\t\t" + pcb.getRemainingTime() + space.repeat(18-4-(countDig(pcb.getRemainingTime()))) + "|" +
                            "\t\t" + ( pcb.getEndTime()==0? "-":pcb.getEndTime() ) + space.repeat(15-4-( pcb.getEndTime()==0? 1:countDig(pcb.getEndTime()) )) +  "|" +
                            "\t\t" + pcb.getProcess_State());
        }
        System.out.println("Total Spent Time: " + globalTimeTracker);
        //The time unit calculated based on the assumption that each burst 1 unit represents 1 milliseconds
        System.out.printf("Throughput(Number of completed processes per time unit): %.3f %s\n" ,
                (numberOfCompletedProcessesTracker/(double)globalTimeTracker),
                numberOfCompletedProcessesTracker>0? "ms":"");
    }// end of processesDetailsPrinter method


    public static void processesDetailsPrinterV2()
    {
        System.out.println("\nProcess ID  | Arrival Time |  Burst Time  |   Remaining Time  |   End Time  |   Turnaround Time  |   Response Time  |   Waiting Time  ");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------------");
        for (PCB pcb: processesArray)
        {
            System.out.println("\t" + pcb.getPId() + space.repeat(12-4-(countDig(pcb.getPId()))) + "|" +
                    "\t" + pcb.getArrivalTime() + space.repeat(15-4-(countDig(pcb.getArrivalTime()))) + "|" +
                    "\t" + pcb.getBurstTime() + space.repeat(14-4-(countDig(pcb.getBurstTime()))) + "|" +
                    "\t\t" + pcb.getRemainingTime() + space.repeat(18-4-(countDig(pcb.getRemainingTime()))) + "|" +
                    "\t\t" + pcb.getEndTime() + space.repeat(12-4-(countDig(pcb.getEndTime()))) +  "|" +
                    "\t\t" + pcb.getTurnaroundTime() + space.repeat(17-4-(countDig(pcb.getTurnaroundTime()))) +  "|" +
                    "\t\t" + pcb.getResponseTime() + space.repeat(16-4-(countDig(pcb.getResponseTime()))) +  "|" +
                    "\t\t" + pcb.getWaitingTime() + space.repeat(16-4-(countDig(pcb.getWaitingTime()))) +  "|" +
                    "\t\t" + pcb.getProcess_State());
            totalWaitingTime += pcb.getWaitingTime();
        }
        System.out.println("Total Spent Time: " + globalTimeTracker);
        System.out.printf("Average Waiting Time: %.2f \n" , (totalWaitingTime/numberOfProcesses));
        //The time unit calculated based on the assumption that each burst 1 unit represents 1 milliseconds
        System.out.printf("Throughput(Number of completed processes per time unit): %.3f ms\n" ,
                (numberOfCompletedProcessesTracker/(double)globalTimeTracker));
    }// end of processesDetailsPrinter method



    private static boolean numberOfProcessValidator(int num)
    {
            if (num < 0)
            {
                System.out.println(" WRONG ENTRY! \n The number of processes must be positive value! try again ");
                return false;
            }
            else if (num == 0)
            {
                System.out.println(" No Processes Are Going To Enter to the System! ");
                System.out.println(" Thank You For Using Multilevel-Feedback-Queue Scheduler Program! ");
                System.exit(0);
                return false;
            }
            else
            {
                return true;
            }
    }//end of numberOfProcessValidator method


    private static boolean processBurstTimeValidator(int burstTime)
    {
            if (burstTime < 0)
            {
                System.out.println(" WRONG ENTRY! \n The Burst Time must be positive value! try again ");
                return false;
            }
            else
            {
                return true;
            }
    }// end of processBurstTimeValidator


    //to control the output table format
    public static int countDig(int n)
    {
        // converting the number n to a string
        String str = Integer.toString(n);
        // computing the size of the string
        int size = str.length();
        return size;
    }//end of countDig method
}
