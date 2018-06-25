package org.apache.flink.streaming.siddhi.example;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.siddhi.SiddhiCEP;
import org.apache.flink.streaming.siddhi.example.event.TemperatureEvent;
import org.apache.flink.types.Row;

/**
 *
 * @see https://wso2.github.io/siddhi/documentation/siddhi-4.0/#pattern
 */
public class PatternMatchExample {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        String room1 = "room1";
        String room2 = "room2";
        DataStream<TemperatureEvent> inputStream = env.fromElements(
                TemperatureEvent.of(2, room1, 35),
                TemperatureEvent.of(4, room1, 41),
                TemperatureEvent.of(6, room1, 42),
                TemperatureEvent.of(8, room1, 47),
                TemperatureEvent.of(10, room1, 45),

                TemperatureEvent.of(20, room2, 35),
                TemperatureEvent.of(40, room2, 35),
                TemperatureEvent.of(60,room2, 50),
                TemperatureEvent.of(80,room2, 52)
        );

        String sequence =
                  "from every s1 = inputStream[not (temp is null)], "
                + "not inputStream[ temp < s1.temp ] and inputStream[ temp > 40 ], "
                + "s2 = inputStream[ roomNo == s1.roomNo and temp > s1.temp ]+, "
                + "s3 = inputStream[ roomNo == s1.roomNo and temp < s2[last].temp ] "
                + "within 1 min "
                + "select s1.roomNo as room_no, s2[last].temp as peek_temp "
                + "group by s1.roomNo "
                + "having not(peek_temp is null) and peek_temp > 40 "
                + "output every 1 events "
                + "insert into outputStream";

        String pattern =
                  "from every (s1 = inputStream[not (temp is null) and instanceOfInteger(temp)]<1>) -> "
                + "not inputStream[ temp < (s1.temp + 5) ] "
                    + "and s3 = inputStream[ temp < (s1.temp + 10) ] "
                + "within 1 year "
                + "select s1.roomNo as room_no, count(s1.temp) as cnt, " +
                    "avg(s1[last].temp) as avg_init_temp, avg(s3.temp) as avg_final_temp "
                + "group by s1.roomNo "
                + "having not(s3.id is null) and instanceOfDouble(avg_final_temp) and avg_final_temp > 40 "
                + "output every 1 events "
                // + "output every 1 millisecond "
                + "insert into outputStream";

        DataStream<Row> output = SiddhiCEP
                .define("inputStream", inputStream.keyBy("roomNo"),
                        "id", "roomNo", "temp", "datetime")
                .cql(sequence)
                .returnAsRow("outputStream");

        output.print();
        env.execute();
    }

}
