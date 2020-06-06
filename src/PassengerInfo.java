public class PassengerInfo {

    private int passengerId;
    private int level;
    private double arrivalRate;
    private double arrivalTime;
    private double serviceRate;

    public int getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(int passengerId) {
        this.passengerId = passengerId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getArrivalRate() {
        return arrivalRate;
    }

    public void setArrivalRate(double arrivalRate) {
        this.arrivalRate = arrivalRate;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public double getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(double serviceRate) {
        this.serviceRate = serviceRate;
    }

    @Override
    public String toString() {
        return "PassengerInfo{" +
                "passengerId=" + passengerId +
                ", level=" + level +
                ", arrivalRate=" + arrivalRate +
                ", arrivalTime=" + arrivalTime +
                ", serviceRate=" + serviceRate +
                '}';
    }
}
