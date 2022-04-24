import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

/**
 * @author xqm
 * @company 冠云信息科技有限公司
 * @since 2022-04-24
 */
public class FlinkDemo {

    public static void main(String[] args) throws Exception {
        //获取执行环境
        StreamExecutionEnvironment environment = StreamExecutionEnvironment.getExecutionEnvironment();

//        RuntimeExecutionMode.BATCH 批处理
//        RuntimeExecutionMode.STREAMING 流处理
        //流批一体
        environment.setRuntimeMode(RuntimeExecutionMode.AUTOMATIC);

        DataStreamSource<String> source = environment.fromElements("java,scala,php,c++", "java,scala,php", "java,scala", "java");

        SingleOutputStreamOperator<String> flatMap = source.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public void flatMap(String s, Collector<String> collector) throws Exception {
                //System.out.println("=====>" + s);
                String[] split = s.split(",");
                //System.out.println("------>" + split);
                for (String word : split) {
                    collector.collect(word);
                }
            }
        });

        //DataStream 下边为DataStream子类
        SingleOutputStreamOperator<String> source1 = flatMap.map(new MapFunction<String, String>() {
            @Override
            public String map(String value) throws Exception {
                return value.toUpperCase();
            }
        });

        source1.print();
        environment.execute();


    }


}
