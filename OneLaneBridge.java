public class OneLaneBridge extends Bridge {
    private final Object conditionVariable = new Object();
    int capacity;

    OneLaneBridge(int capacity){
        super();
        this.capacity = capacity;
    }
    @Override
    public void arrive(Car car) throws InterruptedException {
        synchronized (conditionVariable) {
            while ((bridge.size() == capacity) || (direction != car.getDirection())) {
                conditionVariable.wait();
                if (bridge.isEmpty()){
                    direction = car.getDirection();
                }
            }
            car.setEntryTime(currentTime);
            currentTime++;
            bridge.add(car);
            System.out.println(bridge);
            conditionVariable.notifyAll();
        }
    }

    @Override
    public void exit(Car car) throws InterruptedException {

        synchronized (conditionVariable) {
            // While car is blocked by another car, do not exit bridge
            while (bridge.get(0) != car){
                conditionVariable.wait();
            }
            if (!bridge.remove(car)) {
                System.err.println("Yikes");
            }
            System.out.println(bridge);
            conditionVariable.notifyAll();

        }
    }
}
