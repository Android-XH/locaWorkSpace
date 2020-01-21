package com.example.wechathoke.observer;

public class ObData<D> {
    private Status status;
    private D data;

    public ObData() {

    }

    public ObData(Status status) {
        this.status = status;
    }

    public ObData(Status status, D data) {
        this.status = status;
        this.data = data;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "ObData{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public D getData() {
        return data;
    }

    public void setData(D data) {
        this.data = data;
    }
}
