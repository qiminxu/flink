import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.ArrayList;
import java.util.Properties;

/**
 * @author xqm
 * @company 冠云信息科技有限公司
 * @since 2022-04-26
 */
public class SourceFlink {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //env.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);
        env.setParallelism(1);
        //从文件中获取数据
        DataStreamSource<String> source = env.readTextFile("filePath");
        //从集合中获取
        DataStreamSource<Object> collection = env.fromCollection(new ArrayList<>());
        //从元素读取数据
        DataStreamSource<Event> elements = env.fromElements(new Event(), new Event());
        //从socket文本流中读取
        DataStreamSource<String> stream = env.socketTextStream("47.101.177.83", 8080);
        //从kafka中读取数据
        DataStreamSource<String> kafkaSource = env.addSource(new FlinkKafkaConsumer<String>("topic", new SimpleStringSchema(), new Properties()));


        //stream.print("1");




        env.execute();


    }
}
