import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xqm
 * @company 冠云信息科技有限公司
 * @since 2022-04-26
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Event {

    /**
     * pojo 类 必须是public
     */
    public String name;

    public Long timestamp;

    public String url;
}
