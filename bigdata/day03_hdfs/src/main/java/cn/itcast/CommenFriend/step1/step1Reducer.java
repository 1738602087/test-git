package cn.itcast.CommenFriend.step1;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class step1Reducer extends Reducer<Text,Text,Text,Text> {

    @Override
    protected void reduce(Text friend, Iterable<Text> persons, Context context) throws IOException, InterruptedException {
        StringBuffer buffer = new StringBuffer();
        for (Text person : persons) {
            buffer.append(person).append("-");

        }
        context.write(friend, new Text(buffer.toString()));

    }
}
