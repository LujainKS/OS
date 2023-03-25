/* Algorithm: This class contains all of required algorithms
 * to build the Multilevel-Feedback-Queue scheduler, which
 * are Round Robin with quantum of 8 and 16 as well as
 * First-Come First-Served Algorithms
 * Course: CCCS-225 */

public class Algorithm {

    //RR quantum = 8
    public static void Q0()
    {
        int quantum = 8;

        RoundRobinAlgorithm(quantum);

        System.out.println(" \n\n\n\t\t\t****Processes Information After Q0 Executed**** ");
        OperatingSystem.processesDetailsPrinter();
    }// end of Q0 RoundRobin method

    //RR quantum = 16
    public static void Q1()
    {
        int quantum = 16;

        RoundRobinAlgorithm(quantum);

        System.out.println(" \n\n\n\t\t\t***Processes Information After Q1 Executed*** ");
        OperatingSystem.processesDetailsPrinter();
    }// end of Q1 RoundRobin method

    //First-Come First-Served
    public static void Q2()
    {
        for (PCB pcb: OperatingSystem.processesArray)
        {
            if (pcb.getProcess_State() != PCB.ProcessState.TERMINATED)
            {
                pcb.setProcess_State(PCB.ProcessState.RUNNING);

                OperatingSystem.globalTimeTracker += pcb.getRemainingTime();
                pcb.setRemainingTime(0);

                pcb.setProcess_State(PCB.ProcessState.TERMINATED);
                pcb.setEndTime(OperatingSystem.globalTimeTracker);
                OperatingSystem.numberOfCompletedProcessesTracker++;
            }
        }//end of for loop

        System.out.println(" \n\n\n\t\t\t\t\t\t\t\t\t\t\t\t***Processes Information After Q2 Executed*** ");
        OperatingSystem.processesDetailsPrinterV2();
    }// end of Q2 First-Come First-Served method


    public static void RoundRobinAlgorithm(int quantum)
    {
        for (PCB pcb: OperatingSystem.processesArray)
        {
            if (pcb.getProcess_State() != PCB.ProcessState.TERMINATED)
            {
                ResponseTimeUpdater(pcb);
                pcb.setProcess_State(PCB.ProcessState.RUNNING);

                if (pcb.getRemainingTime() > quantum) //if the remaining time is larger than 16/8 -> positive result
                {
                    OperatingSystem.globalTimeTracker += quantum;
                    pcb.setRemainingTime(pcb.getRemainingTime() - quantum);
                    pcb.setProcess_State(PCB.ProcessState.READY);
                }//end of if block
                else  //if the remaining time is smaller than 16/8 -> negative result or 0
                {
                    OperatingSystem.globalTimeTracker += pcb.getRemainingTime();
                    pcb.setRemainingTime(0);
                    pcb.setProcess_State(PCB.ProcessState.TERMINATED);
                    pcb.setEndTime(OperatingSystem.globalTimeTracker);
                    OperatingSystem.numberOfCompletedProcessesTracker++;
                }//end of else block

            }
        }//end of for loop
    }// end of RoundRobin method


    // Response Time: Amount of time it takes from when a request
    // was submitted until the first response is produced, not output
    public static void ResponseTimeUpdater(PCB pcb)
    {
        if (pcb.getProcess_State() == PCB.ProcessState.NEW)
        {
            pcb.setResponseTime( OperatingSystem.globalTimeTracker );
        }
    }// end of RoundRobin method
}



