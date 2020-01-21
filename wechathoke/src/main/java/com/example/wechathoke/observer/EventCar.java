package com.example.wechathoke.observer;

public class EventCar {
    private static EventCar instance;
    private ConcreteObservableA observableA;

    public static EventCar getDefault() {
        if (instance == null) {
            synchronized (EventCar.class) {
                if (instance == null) {
                    instance = new EventCar();
                }
            }
        }
        return instance;
    }

    private EventCar() {
        observableA = new ConcreteObservableA();
    }

    /**
     * register a observer
     *
     * @param observer
     */
    public void register(Observer observer) {
        if(observableA!=null){
            observableA.addObserver(observer);
        }
    }

    /**
     * unregister a observer
     *
     * @param observer
     */
    public void unregister(Observer observer) {
        if(observableA!=null){
            observableA.removeObserver(observer);
        }
    }

    /**
     * @param obj
     */
    public void post(ObData obj) {
        if(observableA!=null){
            observableA.notifyObservers(obj);
        }
    }
}
