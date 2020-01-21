package com.example.wechathoke.observer;

import java.util.ArrayList;
import java.util.Iterator;

public class ConcreteObservableA implements Observable {
    private ArrayList<Observer> observers;

    @Override
    public void addObserver(Observer observer) {
        if (observers == null) {
            observers = new ArrayList<>();
        }else{
            if (!observers.isEmpty()) {
                Iterator<Observer> iterator = observers.iterator();
                while (iterator.hasNext()) {
                    Observer o = iterator.next();
                    if (observer == o) {
                        iterator.remove();
                    }
                }
            }
        }
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        if (observers == null || observers.size() == 0) {
            return;
        }
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ObData obData) {
        if (observers != null && observers.size() > 0) {
            for(Observer observer :observers){
                if (observer != null) {
                    observer.upData(obData);
                }
            }
        }
    }
}
