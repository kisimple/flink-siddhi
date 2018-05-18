package org.apache.flink.streaming.siddhi.example.event;

import java.io.Serializable;
import java.util.Objects;

public class TemperatureEvent implements Serializable {

    public static TemperatureEvent of(int roomNo, int temp) {
        return of(roomNo, temp, System.currentTimeMillis());
    }

    public static TemperatureEvent of(int roomNo, int temp, long timestamp) {
        TemperatureEvent event = new TemperatureEvent();
        event.roomNo = roomNo;
        event.temp = temp;
        event.timestamp = timestamp;
        return event;
    }

    private int roomNo;
    private int temp;
    private long timestamp;

    public int getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(int roomNo) {
        this.roomNo = roomNo;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TemperatureEvent that = (TemperatureEvent) o;
        return roomNo == that.roomNo && temp == that.temp && timestamp == that.timestamp;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNo, temp, timestamp);
    }

    @Override
    public String toString() {
        return "TemperatureEvent{" +
                "roomNo=" + roomNo +
                ", temp=" + temp +
                ", timestamp=" + timestamp +
                '}';
    }

}
