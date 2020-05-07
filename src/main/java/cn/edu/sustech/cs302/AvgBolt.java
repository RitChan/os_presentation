package cn.edu.sustech.cs302;

import com.twitter.heron.api.bolt.BaseBasicBolt;
import com.twitter.heron.api.bolt.BasicOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Tuple;
import com.twitter.heron.api.tuple.Values;

import java.math.BigDecimal;

public class AvgBolt extends BaseBasicBolt {
    private static final int DEFAULT_SCALE = 10;
    private int count;

    AvgBolt() {
        count = 0;
    }


    @Override
    public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
        BigDecimal sum = new BigDecimal(tuple.getString(0));
        BigDecimal toEmit;
        if (sum.compareTo(new BigDecimal(0)) != 0)
            toEmit = sum.divide(new BigDecimal(++count), DEFAULT_SCALE, BigDecimal.ROUND_HALF_UP);
        else
            toEmit = new BigDecimal(0);
        basicOutputCollector.emit(new Values(toEmit.toString()));
        System.out.printf("sum = %8s, avg = %s, count = %d\n",
                sum.toString().substring(0, 10),
                toEmit.toString(), count);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("avg"));
    }
}
