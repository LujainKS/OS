/*  PCB: This class represented the Process Control Block
 * that contains all needed attributes to have a control
 * over the process information whenever they enter the
 * scheduler queues */

public class PCB {

    public static enum ProcessState {
        NEW,
        READY,
        RUNNING,
        WAITING,
        TERMINATED
    }

    // the process state
    private int PId;

    // the process state
    private ProcessState Process_State;

    private int burstTime;
    private int arrivalTime;
    private int endTime;
    private int remainingTime;
    private int ResponseTime;
    private int turnaroundTime;
    private int waitingTime;


    //Constructor
    public PCB(int PId, int arrivalTime, int burstTime) {
        this.PId = PId;
        Process_State = ProcessState.NEW;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        remainingTime = burstTime;
    }

    public int getPId() {
        return PId;
    }

    public void setPId(int PId) {
        this.PId = PId;
    }

    public ProcessState getProcess_State() {
        return Process_State;
    }

    public void setProcess_State(ProcessState process_State) {
        Process_State = process_State;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
        setTurnaroundTime(endTime - getArrivalTime());
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(int remainingTime) {
        this.remainingTime = remainingTime;
    }

    public int getResponseTime() {
        return ResponseTime;
    }

    public void setResponseTime(int responseTime) {
        ResponseTime = responseTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
        setWaitingTime(turnaroundTime - getBurstTime());
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getBurstTime() {return burstTime;}

    public void setBurstTime(int burstTime) {this.burstTime = burstTime;}

    @Override
    public String toString() {
        return "PCB{" +
                "PId=" + PId +
                ", Process_State=" + Process_State +
                ", burstTime=" + burstTime +
                ", arrivalTime=" + arrivalTime +
                ", endTime=" + endTime +
                ", remainingTime=" + remainingTime +
                ", turnaroundTime=" + turnaroundTime +
                ", waitingTime=" + waitingTime +
                '}';
    }
}
