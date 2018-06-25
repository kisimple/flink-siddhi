package org.apache.flink.streaming.siddhi.example.event;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class TemperatureEvent implements Serializable {

    public static TemperatureEvent of(long id, String roomNo, int temp) {
        return of(id, roomNo, temp, LocalDateTime.now());
    }

    public static TemperatureEvent of(long id, String roomNo, int temp, LocalDateTime datetime) {
        TemperatureEvent event = new TemperatureEvent();
        event.id = id;
        event.roomNo = roomNo;
        event.temp = temp;
        event.datetime = datetime;
        return event;
    }

    private long id;
    private String roomNo;
    private int temp;
    private LocalDateTime datetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TemperatureEvent that = (TemperatureEvent) o;

        return id == that.id
                && temp == that.temp
                && (roomNo != null ? roomNo.equals(that.roomNo) : that.roomNo == null)
                && (datetime != null ? datetime.equals(that.datetime) : that.datetime == null);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNo, temp, datetime);
    }

    @Override
    public String toString() {
        return "TemperatureEvent{" +
                "id=" + id +
                ", roomNo='" + roomNo + '\'' +
                ", temp=" + temp +
                ", datetime=" + datetime +
                '}';
    }

}
