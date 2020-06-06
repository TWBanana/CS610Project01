import java.text.DecimalFormat;
import java.util.*;

public class Main {

    static double clock = 0.0;
    static int firstClassId = 0;
    static int coachClassId = 0;
    static double firstArrivalTime = 0.0;
    static double coachArrivalTime = 0.0;
    static double firstClassMaxQueueLength = 0.0;
    static double coachClassMaxQueueLength = 0.0;
    static double sumFirstClassWaiting = 0.0;
    static double sumCoachClassWaiting = 0.0;
    static double maxFirstClassWaiting = 0.0;
    static double maxCoachClassWaiting = 0.0;
    static double avgFirstClassWaiting = 0.0;
    static double avgCoachClassWaiting = 0.0;

    // parameters for input
    static double duration;
    static double firstClassAvgArrivalRate;
    static double firstClassAvgServiceRate;
    static double coachClassAvgArrivalRate;
    static double coachClassAvgServiceRate;

    static List<Double> firstClassWaitingTime = new ArrayList<>();
    static List<Double> coachClassWaitingTime = new ArrayList<>();

    // create new object for station
    static ServiceStation firstClassService1 = new ServiceStation();
    static ServiceStation firstClassService2 = new ServiceStation();
    static ServiceStation firstClassService3 = new ServiceStation();

    static ServiceStation coachClassService1 = new ServiceStation();
    static ServiceStation coachClassService2 = new ServiceStation();
    static ServiceStation coachClassService3 = new ServiceStation();
    static ServiceStation coachClassService4 = new ServiceStation();

    // list for storing passengers.
    static Queue<PassengerInfo> firstClassList = new LinkedList<>();
    static Queue<PassengerInfo> coachClassList = new LinkedList<>();

    // passenger waiting on line
    static Queue<PassengerInfo> firstClassQueue = new LinkedList<>();
    static Queue<PassengerInfo> coachClassQueue = new LinkedList<>();


    static Set<Double> firstClassArrivalTime = new HashSet<>();
    static Set<Double> coachClassArrivalTime = new HashSet<>();

    static List<Double> firstClassService1WorkingTime = new ArrayList<>();
    static List<Double> firstClassService2WorkingTime = new ArrayList<>();
    static List<Double> firstClassService3WorkingTime = new ArrayList<>();

    static List<Double> coachClassService1WorkingTime = new ArrayList<>();
    static List<Double> coachClassService2WorkingTime = new ArrayList<>();
    static List<Double> coachClassService3WorkingTime = new ArrayList<>();
    static List<Double> coachClassService4WorkingTime = new ArrayList<>();

    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    public static void main(String[] args) {



        // inputs for simulation
        System.out.println("Enter duration time for check-in: ");
        duration = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter AVERAGE ARRIVAL RATE for FIRST CLASS passengers: ");
        firstClassAvgArrivalRate = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter AVERAGE SERVICE RATE for FIRST CLASS passengers: ");
        firstClassAvgServiceRate = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter AVERAGE ARRIVAL RATE for COACH CLASS passengers: ");
        coachClassAvgArrivalRate = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter AVERAGE SERVICE RATE for COACH CLASS passengers: ");
        coachClassAvgServiceRate = scanner.nextDouble();
        scanner.nextLine();


        // Generating passengers for first class and coach class
        // Adding the passengers into the list before adding to the line
        while (firstArrivalTime <= duration ||
                coachArrivalTime <= duration) {

            if (firstArrivalTime <= duration) {
                PassengerInfo firstClass = new PassengerInfo();
                firstClassId++;
                firstClass.setLevel(1);
                firstClass.setPassengerId(firstClassId);
                firstClass.setArrivalRate(random.nextDouble());
                firstClass.setArrivalRate(Math.ceil(randomNumber(firstClassAvgArrivalRate)));
                firstClass.setServiceRate(Math.ceil(randomNumber(firstClassAvgServiceRate)));
                firstArrivalTime += firstClass.getArrivalRate();
                firstClass.setArrivalTime(firstArrivalTime);
                if (firstClass.getArrivalTime() < duration) {
                    firstClassList.add(firstClass);
                    firstClassArrivalTime.add(firstClass.getArrivalTime());

                }

            }

            if (coachArrivalTime <= duration) {
                PassengerInfo coachClass = new PassengerInfo();
                coachClassId++;
                coachClass.setLevel(2);
                coachClass.setPassengerId(coachClassId);
                coachClass.setArrivalRate(Math.ceil(randomNumber(coachClassAvgArrivalRate)));
                coachClass.setServiceRate(Math.ceil(randomNumber(coachClassAvgServiceRate)));
                coachArrivalTime += coachClass.getArrivalRate();
                coachClass.setArrivalTime(coachArrivalTime);
                if (coachClass.getArrivalTime() < duration) {
                    coachClassList.add(coachClass);
                    coachClassArrivalTime.add(coachClass.getArrivalTime());
                }
            }
        }

        int totalNumberOfFirstClass = firstClassList.size();
        int totalNumberOfCoachClass = coachClassList.size();

        firstClassService1.setFree(true);
        firstClassService2.setFree(true);
        firstClassService3.setFree(true);

        coachClassService1.setFree(true);
        coachClassService2.setFree(true);
        coachClassService3.setFree(true);
        coachClassService4.setFree(true);

        // clock will start at 0 min and increase by 1 min every round.
        while (clock <= duration) {

            if (firstClassArrivalTime.contains(clock)) {
                firstClassQueue.offer(firstClassList.poll());
            }
            if (coachClassArrivalTime.contains(clock)) {
                coachClassQueue.offer(coachClassList.poll());
            }

            // first class service
            if (firstClassService1.isFree() && firstClassQueue.size() != 0) {
                PassengerInfo firstClassPassenger = firstClassQueue.remove();
                firstClassService1.setStartTime(clock);
                firstClassService1.setEndTime(clock + firstClassPassenger.getServiceRate());
                firstClassService1.setFree(false);
                firstClassWaitingTime.add(clock - firstClassPassenger.getArrivalTime());
                firstClassService1WorkingTime.add(firstClassPassenger.getServiceRate());
            } else if (firstClassService2.isFree() && !firstClassService1.isFree() && firstClassQueue.size() != 0) {
                PassengerInfo firstClassPassenger = firstClassQueue.remove();
                firstClassService2.setStartTime(clock);
                firstClassService2.setEndTime(clock + firstClassPassenger.getServiceRate());
                firstClassService2.setFree(false);
                firstClassWaitingTime.add(clock - firstClassPassenger.getArrivalTime());
                firstClassService2WorkingTime.add(firstClassPassenger.getServiceRate());
            } else if (firstClassService3.isFree() && !firstClassService2.isFree() && firstClassQueue.size() != 0) {
                PassengerInfo firstClassPassenger = firstClassQueue.remove();
                firstClassService3.setStartTime(clock);
                firstClassService3.setEndTime(clock + firstClassPassenger.getServiceRate());
                firstClassService3.setFree(false);
                firstClassWaitingTime.add(clock - firstClassPassenger.getArrivalTime());
                firstClassService3WorkingTime.add(firstClassPassenger.getServiceRate());
            }

            // coach class services
            if (coachClassService1.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService1.setStartTime(clock);
                coachClassService1.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService1.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService1WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if (coachClassService2.isFree() && !coachClassService1.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService2.setStartTime(clock);
                coachClassService2.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService2.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService2WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if (coachClassService3.isFree() && !coachClassService2.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService3.setStartTime(clock);
                coachClassService3.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService3.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService3WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if (coachClassService4.isFree() && !coachClassService3.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService4.setStartTime(clock);
                coachClassService4.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService4.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService4WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if ((firstClassService1.isFree() && firstClassQueue.size() == 0) && !coachClassService4.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                firstClassService1.setStartTime(clock);
                firstClassService1.setEndTime(clock + coachClassPassenger.getServiceRate());
                firstClassService1.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                firstClassService1WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if ((firstClassService2.isFree() && firstClassQueue.size() == 0) && !firstClassService1.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                firstClassService2.setStartTime(clock);
                firstClassService2.setEndTime(clock + coachClassPassenger.getServiceRate());
                firstClassService2.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                firstClassService2WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if ((firstClassService3.isFree() && firstClassQueue.size() == 0) && !firstClassService2.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                firstClassService3.setStartTime(clock);
                firstClassService3.setEndTime(clock + coachClassPassenger.getServiceRate());
                firstClassService3.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                firstClassService3WorkingTime.add(coachClassPassenger.getServiceRate());
            }

            // check the first class service is empty
            if (firstClassService1.getEndTime() == clock) {
                firstClassService1.setFree(true);
            }
            if (firstClassService2.getEndTime() == clock) {
                firstClassService2.setFree(true);
            }
            if (firstClassService3.getEndTime() == clock) {
                firstClassService3.setFree(true);
            }

            // check the coach class service is empty
            if (coachClassService1.getEndTime() == clock) {
                coachClassService1.setFree(true);
            }
            if (coachClassService2.getEndTime() == clock) {
                coachClassService2.setFree(true);
            }
            if (coachClassService3.getEndTime() == clock) {
                coachClassService3.setFree(true);
            }
            if (coachClassService4.getEndTime() == clock) {
                coachClassService4.setFree(true);
            }

            firstClassMaxQueueLength = Math.max(firstClassQueue.size(), firstClassMaxQueueLength);
            coachClassMaxQueueLength = Math.max(coachClassQueue.size(), coachClassMaxQueueLength);

            clock++;

            printDisplay();

        }


        // Passengers in line after the duration is over.
        while ( !firstClassService1.isFree() ||
                !firstClassService2.isFree() ||
                !firstClassService3.isFree() ||
                !coachClassService1.isFree() ||
                !coachClassService2.isFree() ||
                !coachClassService3.isFree() ||
                !coachClassService4.isFree()) {

            // first class service
            if (firstClassService1.isFree() && firstClassQueue.size() != 0) {
                PassengerInfo firstClassPassenger = firstClassQueue.remove();
                firstClassService1.setStartTime(clock);
                firstClassService1.setEndTime(clock + firstClassPassenger.getServiceRate());
                firstClassService1.setFree(false);
                firstClassWaitingTime.add(clock - firstClassPassenger.getArrivalTime());
                firstClassService1WorkingTime.add(firstClassPassenger.getServiceRate());
            } else if (firstClassService2.isFree() && !firstClassService1.isFree() && firstClassQueue.size() != 0) {
                PassengerInfo firstClassPassenger = firstClassQueue.remove();
                firstClassService2.setStartTime(clock);
                firstClassService2.setEndTime(clock + firstClassPassenger.getServiceRate());
                firstClassService2.setFree(false);
                firstClassWaitingTime.add(clock - firstClassPassenger.getArrivalTime());
                firstClassService2WorkingTime.add(firstClassPassenger.getServiceRate());
            } else if (firstClassService3.isFree() && !firstClassService2.isFree() && firstClassQueue.size() != 0) {
                PassengerInfo firstClassPassenger = firstClassQueue.remove();
                firstClassService3.setStartTime(clock);
                firstClassService3.setEndTime(clock + firstClassPassenger.getServiceRate());
                firstClassService3.setFree(false);
                firstClassWaitingTime.add(clock - firstClassPassenger.getArrivalTime());
                firstClassService3WorkingTime.add(firstClassPassenger.getServiceRate());
            }

            // coach class services
            if (coachClassService1.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService1.setStartTime(clock);
                coachClassService1.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService1.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService1WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if (coachClassService2.isFree() && !coachClassService1.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService2.setStartTime(clock);
                coachClassService2.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService2.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService2WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if (coachClassService3.isFree() && !coachClassService2.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService3.setStartTime(clock);
                coachClassService3.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService3.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService3WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if (coachClassService4.isFree() && !coachClassService3.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                coachClassService4.setStartTime(clock);
                coachClassService4.setEndTime(clock + coachClassPassenger.getServiceRate());
                coachClassService4.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                coachClassService4WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if ((firstClassService1.isFree() && firstClassQueue.size() == 0) && !coachClassService4.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                firstClassService1.setStartTime(clock);
                firstClassService1.setEndTime(clock + coachClassPassenger.getServiceRate());
                firstClassService1.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                firstClassService1WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if ((firstClassService2.isFree() && firstClassQueue.size() == 0) && !firstClassService1.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                firstClassService2.setStartTime(clock);
                firstClassService2.setEndTime(clock + coachClassPassenger.getServiceRate());
                firstClassService2.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                firstClassService2WorkingTime.add(coachClassPassenger.getServiceRate());
            } else if ((firstClassService3.isFree() && firstClassQueue.size() == 0) && !firstClassService2.isFree() && coachClassQueue.size() != 0) {
                PassengerInfo coachClassPassenger = coachClassQueue.remove();
                firstClassService3.setStartTime(clock);
                firstClassService3.setEndTime(clock + coachClassPassenger.getServiceRate());
                firstClassService3.setFree(false);
                coachClassWaitingTime.add(clock - coachClassPassenger.getArrivalTime());
                firstClassService3WorkingTime.add(coachClassPassenger.getServiceRate());
            }

            // check the first class service is empty
            if (firstClassService1.getEndTime() == clock) {
                firstClassService1.setFree(true);
            }
            if (firstClassService2.getEndTime() == clock) {
                firstClassService2.setFree(true);
            }
            if (firstClassService3.getEndTime() == clock) {
                firstClassService3.setFree(true);
            }

            // check the coach class service is empty
            if (coachClassService1.getEndTime() == clock) {
                coachClassService1.setFree(true);
            }
            if (coachClassService2.getEndTime() == clock) {
                coachClassService2.setFree(true);
            }
            if (coachClassService3.getEndTime() == clock) {
                coachClassService3.setFree(true);
            }
            if (coachClassService4.getEndTime() == clock) {
                coachClassService4.setFree(true);
            }


            clock++;

            printDisplay();

        }


        // Outputs
        for (double time : firstClassWaitingTime) {
            sumFirstClassWaiting += time;
            maxFirstClassWaiting = Math.max(maxFirstClassWaiting, time);
        }

        avgFirstClassWaiting = sumFirstClassWaiting / totalNumberOfFirstClass;

        for (double time : coachClassWaitingTime) {
            sumCoachClassWaiting += time;
            maxCoachClassWaiting = Math.max(maxCoachClassWaiting, time);
        }

        avgCoachClassWaiting = sumCoachClassWaiting / totalNumberOfCoachClass;

        double avgOfFirstClassService1 = (findSum(firstClassService1WorkingTime) / clock) * 100;
        double avgOfFirstClassService2 = (findSum(firstClassService2WorkingTime) / clock) * 100;
        double avgOfFirstClassService3 = (findSum(firstClassService3WorkingTime) / clock) * 100;
        double avgOfCoachClassService1 = (findSum(coachClassService1WorkingTime) / clock) * 100;
        double avgOfCoachClassService2 = (findSum(coachClassService2WorkingTime) / clock) * 100;
        double avgOfCoachClassService3 = (findSum(coachClassService3WorkingTime) / clock) * 100;
        double avgOfCoachClassService4 = (findSum(coachClassService4WorkingTime) / clock) * 100;


        DecimalFormat df = new DecimalFormat("###.##");


        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|                           OUTPUT                            |");
        System.out.println("+-------------------------------------------------------------+");

        System.out.println();
        System.out.println("Total time for simulation: " + clock + " minutes.");
        System.out.println();

        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|                         FIRST CLASS                         |");
        System.out.println("+-------------------------------------------------------------+");

        System.out.println("Maximum length of queue for First Class: " + firstClassMaxQueueLength);
        System.out.println("Maximum waiting time for First Class: " + maxFirstClassWaiting + " minutes.");
        System.out.println("Average waiting time for First CLass: " + df.format(avgFirstClassWaiting) + " minutes.");
        System.out.println("The rate of occupancy of First Class Service 1: " + df.format(avgOfFirstClassService1) + "%");
        System.out.println("The rate of occupancy of First Class Service 2: " + df.format(avgOfFirstClassService2) + "%");
        System.out.println("The rate of occupancy of First Class Service 3: " + df.format(avgOfFirstClassService3) + "%");
        System.out.println("!!!All the FIRST CLASS passengers were finished check-in!!!");

        System.out.println();

        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|                         COACH CLASS                         |");
        System.out.println("+-------------------------------------------------------------+");

        System.out.println("Maximum length of queue for Coach Class: " + coachClassMaxQueueLength);
        System.out.println("Maximum waiting time for Coach Class: " + maxCoachClassWaiting + " minutes.");
        System.out.println("Average waiting time for Coach CLass: " + df.format(avgCoachClassWaiting) + " minutes.");
        System.out.println("The rate of occupancy of Coach Class Service 1: " + df.format(avgOfCoachClassService1) + "%");
        System.out.println("The rate of occupancy of Coach Class Service 2: " + df.format(avgOfCoachClassService2) + "%");
        System.out.println("The rate of occupancy of Coach Class Service 3: " + df.format(avgOfCoachClassService3) + "%");
        System.out.println("The rate of occupancy of Coach Class Service 4: " + df.format(avgOfCoachClassService4) + "%");
        System.out.println("!!!All the COACH CLASS passengers were finished check-in!!!");

        System.out.println();
    }

    public static double randomNumber(double input) {
        return (input * 0.5) + (input * random.nextDouble());
    }

    public static double findSum(List<Double> list) {
        double sum = 0.0;

        for (double i : list) {
            sum += i;
        }
        return sum;
    }

    public static void printDisplay() {
        String s = "|";

        System.out.println("+-------------------------------------------------------------+");
        if(clock < 10) {
            System.out.printf( "| Time: %.1f %51s%n", clock, s);
            if(firstClassQueue.size() < 10) {
                System.out.printf("| First Class Line: %d passengers%31s%n", firstClassQueue.size(), s);
            } else if (firstClassQueue.size() < 100) {
                System.out.printf("| First Class Line: %d passengers%30s%n", firstClassQueue.size(), s);
            } else {
                System.out.printf("| First Class Line: %d passengers%29s%n", firstClassQueue.size(), s);
            }

            if(coachClassQueue.size() < 10) {
                System.out.printf("| Coach Class Line: %d passengers%31s%n", coachClassQueue.size(), s);
            } else if (coachClassQueue.size() < 100) {
                System.out.printf("| Coach Class Line: %d passengers%30s%n", coachClassQueue.size(), s);
            } else {
                System.out.printf("| Coach Class Line: %d passengers%29s%n", coachClassQueue.size(), s);
            }

            System.out.printf("| First Class Service 1: %s %35s%n", firstClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 2: %s %35s%n", firstClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 3: %s %35s%n", firstClassService3.isFree() ? "\u2705" : "\u26D4", s);

            System.out.printf("| Coach Class Service 1: %s %35s%n", coachClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 2: %s %35s%n", coachClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 3: %s %35s%n", coachClassService3.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 4: %s %35s%n", coachClassService4.isFree() ? "\u2705" : "\u26D4", s);


        } else if (clock < 100){
            System.out.printf( "| Time: %.1f %50s%n", clock, s);
            if(firstClassQueue.size() < 10) {
                System.out.printf("| First Class Line: %d passengers%31s%n", firstClassQueue.size(), s);
            } else if (firstClassQueue.size() < 100) {
                System.out.printf("| First Class Line: %d passengers%30s%n", firstClassQueue.size(), s);
            } else {
                System.out.printf("| First Class Line: %d passengers%29s%n", firstClassQueue.size(), s);
            }

            if(coachClassQueue.size() < 10) {
                System.out.printf("| Coach Class Line: %d passengers%31s%n", coachClassQueue.size(), s);
            } else if (coachClassQueue.size() < 100) {
                System.out.printf("| Coach Class Line: %d passengers%30s%n", coachClassQueue.size(), s);
            } else {
                System.out.printf("| Coach Class Line: %d passengers%29s%n", coachClassQueue.size(), s);
            }

            System.out.printf("| First Class Service 1: %s %35s%n", firstClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 2: %s %35s%n", firstClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 3: %s %35s%n", firstClassService3.isFree() ? "\u2705" : "\u26D4", s);

            System.out.printf("| Coach Class Service 1: %s %35s%n", coachClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 2: %s %35s%n", coachClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 3: %s %35s%n", coachClassService3.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 4: %s %35s%n", coachClassService4.isFree() ? "\u2705" : "\u26D4", s);

        } else if (clock < 1000) {
            System.out.printf( "| Time: %.1f %49s%n", clock, s);
            if(firstClassQueue.size() < 10) {
                System.out.printf("| First Class Line: %d passengers%31s%n", firstClassQueue.size(), s);
            } else if (firstClassQueue.size() < 100) {
                System.out.printf("| First Class Line: %d passengers%30s%n", firstClassQueue.size(), s);
            } else {
                System.out.printf("| First Class Line: %d passengers%29s%n", firstClassQueue.size(), s);
            }

            if(coachClassQueue.size() < 10) {
                System.out.printf("| Coach Class Line: %d passengers%31s%n", coachClassQueue.size(), s);
            } else if (coachClassQueue.size() < 100) {
                System.out.printf("| Coach Class Line: %d passengers%30s%n", coachClassQueue.size(), s);
            } else {
                System.out.printf("| Coach Class Line: %d passengers%29s%n", coachClassQueue.size(), s);
            }

            System.out.printf("| First Class Service 1: %s %35s%n", firstClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 2: %s %35s%n", firstClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 3: %s %35s%n", firstClassService3.isFree() ? "\u2705" : "\u26D4", s);

            System.out.printf("| Coach Class Service 1: %s %35s%n", coachClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 2: %s %35s%n", coachClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 3: %s %35s%n", coachClassService3.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 4: %s %35s%n", coachClassService4.isFree() ? "\u2705" : "\u26D4", s);


        } else {
            System.out.printf( "| Time: %.1f %48s%n", clock, s);
            if(firstClassQueue.size() < 10) {
                System.out.printf("| First Class Line: %d passengers%31s%n", firstClassQueue.size(), s);
            } else if (firstClassQueue.size() < 100) {
                System.out.printf("| First Class Line: %d passengers%30s%n", firstClassQueue.size(), s);
            } else {
                System.out.printf("| First Class Line: %d passengers%29s%n", firstClassQueue.size(), s);
            }

            if(coachClassQueue.size() < 10) {
                System.out.printf("| Coach Class Line: %d passengers%31s%n", coachClassQueue.size(), s);
            } else if (coachClassQueue.size() < 100) {
                System.out.printf("| Coach Class Line: %d passengers%30s%n", coachClassQueue.size(), s);
            } else {
                System.out.printf("| Coach Class Line: %d passengers%29s%n", coachClassQueue.size(), s);
            }

            System.out.printf("| First Class Service 1: %s %35s%n", firstClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 2: %s %35s%n", firstClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| First Class Service 3: %s %35s%n", firstClassService3.isFree() ? "\u2705" : "\u26D4", s);

            System.out.printf("| Coach Class Service 1: %s %35s%n", coachClassService1.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 2: %s %35s%n", coachClassService2.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 3: %s %35s%n", coachClassService3.isFree() ? "\u2705" : "\u26D4", s);
            System.out.printf("| Coach Class Service 4: %s %35s%n", coachClassService4.isFree() ? "\u2705" : "\u26D4", s);

        }
        System.out.println("+-------------------------------------------------------------+");
    }

}
