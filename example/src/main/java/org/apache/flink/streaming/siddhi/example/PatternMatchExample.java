package org.apache.flink.streaming.siddhi.example;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.example.event.TemperatureEvent;

import java.util.Map;

/**
 *
 * @see https://wso2.github.io/siddhi/documentation/siddhi-4.0/#pattern
 */
public class PatternMatchExample {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        int room1No = 6666;
        int room2No = 7777;
        DataStream<TemperatureEvent> inputStream = env.fromElements(
                TemperatureEvent.of(room1No, 25, 1000),
                TemperatureEvent.of(room2No, 25, 2000),
                TemperatureEvent.of(room1No, 25, 3000),
                TemperatureEvent.of(room2No, 25, 4000),
                TemperatureEvent.of(room1No, 35, 5000),
                TemperatureEvent.of(room2No, 25, 6000),
                TemperatureEvent.of(room1No, 45, 7000)
        );
        DataStream<Map<String, Object>> output = SiddhiCEP
                .define("inputStream", inputStream.keyBy("roomNo"),
                        "roomNo", "temp", "timestamp")
                .cql("from every s1 = inputStream "
                    + "-> s2 = inputStream[ roomNo == s1.roomNo and temp > (s1.temp + 5) ] "
                    + "within 1 min "
                    + "select s1.roomNo as roomNo, " +
                        "s1.timestamp as init_ts, " +
                        "s1.temp as init_temp, " +
                        "s2.timestamp as final_ts, " +
                        "s2.temp as final_temp "
                    + "insert into outputStream"
                )
                .returnAsMap("outputStream");

        output.print();
        env.execute();
    }

}
