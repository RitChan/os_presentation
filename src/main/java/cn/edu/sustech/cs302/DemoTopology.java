package cn.edu.sustech.cs302;

import com.twitter.heron.api.Config;
import com.twitter.heron.api.topology.TopologyBuilder;
import com.twitter.heron.common.basics.ByteAmount;

public class DemoTopology {
    private DemoTopology() {}

    public static void main(String[] args) throws Exception {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("randDouble", new RandomDoubleSpout(), 1);
        builder.setBolt("sum", new SumBolt(), 1)
                .allGrouping("randDouble");
        builder.setBolt("avg", new AvgBolt(), 1)
                .allGrouping("sum");
        HelperRunner.runTopology(args, builder.createTopology(), new Config());
    }
}
