package step1;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class Mapper1 extends Mapper<LongWritable, Text, Text, Text> {

    private Text outKey = new Text();
    private Text outValue = new Text();

    /**
     *
     * @param key  行号：1,2,3......
     * @param value 文本的值： A,1,1
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

//        try {

            String[] values = value.toString().split(",");
            String userId = values[0];
            String itemId = values[1];
            String score = values[2];

            outKey.set(userId);
            outValue.set(itemId + "_" + score);

//        }catch (Exception e){
//            e.printStackTrace();
//        }

        context.write(outKey,outValue);

    }
}
