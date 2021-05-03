package demo5.grouping;


import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class OrderIdPartition extends Partitioner<OrderBean,NullWritable> {
    /**
     * 自定义我们的分区规则，按照我们的这个orderID进行分区，相同的这个orderID发送到同一个
     * reduce里面去
     * @param orderBean
     * @param nullWritable
     * @param numReducerTasks
     * @return
     */
    @Override
     //相同的orderID算出来的分区号肯定是一样的，所以可以保证我们的这个相同的分区号发送到同一个分区里面
    public int getPartition(OrderBean orderBean, NullWritable nullWritable, int numReducerTasks) {
        int partition= (orderBean.getOrderid().hashCode() & Integer.MAX_VALUE) % numReducerTasks;
        return partition;
    }


}
