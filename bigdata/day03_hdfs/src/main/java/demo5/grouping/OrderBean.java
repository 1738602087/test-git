package demo5.grouping;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
/*
* 定义我们的key2类型*/
public class OrderBean implements WritableComparable<OrderBean> {
    private String OrderId;
    private Double price;

    @Override
    public String toString() {
        return OrderId+"\t"+price;
    }

    public String getOrderid() {
        return OrderId;
    }

    public void setOrderId(String orderid) {
        this.OrderId = orderid;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    /*
     * 排序，按照我们的金额进行排序，首先进行比较我们的orderid,如果我们的这个
     * orderID不相同，我们不需要进行比较价格*/
    @Override
    public int compareTo(OrderBean o) {
        int i=this.OrderId.compareTo(o.OrderId);
        if(i==0) {
            i=this.price.compareTo(o.price);
        }
        return -i;
    }



    /**
     * 序列化的方法
     * @param out
     * @throws IOException
     */
    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(OrderId);
        out.writeDouble(price);
    }

    /**
     * 反序列化的方法
     * @param in
     * @throws IOException
     */
    @Override
    public void readFields(DataInput in) throws IOException {
        this.OrderId = in.readUTF();
        this.price = in.readDouble();

    }
}
