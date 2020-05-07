package cn.edu.sustech.cs302;


import com.twitter.heron.api.spout.BaseRichSpout;
import com.twitter.heron.api.spout.SpoutOutputCollector;
import com.twitter.heron.api.topology.OutputFieldsDeclarer;
import com.twitter.heron.api.topology.TopologyContext;
import com.twitter.heron.api.tuple.Fields;
import com.twitter.heron.api.tuple.Values;
import com.twitter.heron.api.utils.Utils;

import java.util.Map;
import java.util.Random;

public class RandomDoubleSpout extends BaseRichSpout {
    private SpoutOutputCollector collector;
    private Random random;
    private final int BOUND = 1024;

    @Override
    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        this.collector = spoutOutputCollector;
        this.random = new Random();
    }

    @Override
    public void nextTuple() {
        Utils.sleep(1000); // slow down to demonstrate output
        double elem = random.nextDouble() * BOUND;
        collector.emit(new Values(elem));
        System.out.printf("emit random double %f\n", elem);
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("randomDouble"));
    }
}
