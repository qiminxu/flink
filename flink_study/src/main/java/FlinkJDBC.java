
import com.ververica.cdc.connectors.mysql.MySqlSource;
import com.ververica.cdc.connectors.mysql.table.StartupOptions;
import com.ververica.cdc.debezium.DebeziumSourceFunction;
import com.ververica.cdc.debezium.StringDebeziumDeserializationSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author DELL
 */
public class FlinkJDBC {
    public static void main(String[] args) throws Exception {
        // 1、获取执行环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        // 2、通过FlinkCDC构建SourceFunction
        DebeziumSourceFunction<String> mysqlSource = MySqlSource.<String>builder()
                .hostname("47.101.177.83")
                .port(3306)
                //database
                .databaseList("bz")
                // 如果不添加该参数,则消费指定数据库中的所有表
                .tableList("bz.file_info")
                .username("megalith")
                .password("Admin@2020")
                /**initial:初始化快照,即全量导入后增量导入(检测更新数据写入)
                 * latest:只进行增量导入(不读取历史变化)
                 * timestamp:指定时间戳进行数据导入(大于等于指定时间错读取数据)
                 */
                .startupOptions(StartupOptions.initial())
                .deserializer(new CustomerDeserialization())
                .build();
        // 3、使用CDC Source方式从mysql中读取数据
        DataStreamSource<String> mysqlDS = env.addSource(mysqlSource);

        // 4、打印数据
        mysqlDS.print();

        // 5、执行任务
        env.execute("flinkcdcmysql");
    }
}

