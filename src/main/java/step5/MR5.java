package step5;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MR5 {
    private static String inPath = "../../test/userCF/step4_output";

    private static String outPath = "../../test/userCF/step5_output";
    private static String cache = "../../test/userCF/step1_output/part-r-00000";
    private static String hdfs = "hdfs://daiyh:9000";

    public int run(){
        try {
            Configuration conf = new Configuration();
            conf.set("fs.defaultFS", hdfs);
            Job job = Job.getInstance(conf, "step5");

            job.addCacheArchive(new URI(cache + "#itemUserScore3"));

            job.setJarByClass(MR5.class);
            job.setMapperClass(Mapper5.class);
            job.setReducerClass(Reducer5.class);

            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);

            FileSystem fs = FileSystem.get(conf);
            Path inputPath = new Path(inPath);
            if (fs.exists(inputPath)){
                FileInputFormat.addInputPath(job, inputPath);
            }
            Path outputPath = new Path(outPath);
            fs.delete(outputPath, true);
            FileOutputFormat.setOutputPath(job, outputPath);

            return job.waitForCompletion(true) ? 1 : -1;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void main(String[] args){
        try {
            int result = -1;
            result = new MR5().run();
            BasicConfigurator.configure();
            if (result == 1) {
                System.out.println("step5运行成功。。。");
            } else {
                System.out.println("step5 运行失败。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
