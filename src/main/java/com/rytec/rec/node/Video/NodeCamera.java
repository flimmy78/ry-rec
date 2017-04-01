package com.rytec.rec.node.Video;

import com.rytec.rec.channel.ChannelInterface;
import com.rytec.rec.channel.ChannelManager;
import com.rytec.rec.channel.ChannelMessage;
import com.rytec.rec.db.model.ChannelNode;
import com.rytec.rec.node.*;
import com.rytec.rec.util.AnnotationJSExport;
import com.rytec.rec.util.AnnotationNodeType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Danny on 2017/3/13.
 */

@Service
@AnnotationNodeType(4001)
@AnnotationJSExport("摄像机")
public class NodeCamera extends BaseNode implements NodeInterface {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ChannelManager channelManager;

    @Autowired
    NodeManager nodeManager;

    // 命令编码
    public ChannelMessage genMessage(int where, int nodeId, int cmd, int value) {
        return null;
    }

    //消息解码
    public void decodeMessage(ChannelMessage msg) {

    }

    // 发送云台控制
    public int sendMessage(NodeMessage msg) {
        /*
        通过 node 查询到 channelId、 nodeId、Adr，然后去操作PTZ接口
         */
        logger.debug("云台控制");

        ChannelNode channelNode = nodeManager.getChannelNodeByNodeId(msg.node).channelNode;
        ChannelInterface channel = channelManager.getChannelInterface(channelNode.getCtype());
        channel.sendMsg(msg);
        return 0;
    }

    public boolean valueCompare(NodeConfig cfg, java.lang.Object oldVal, java.lang.Object newVal) {
        return true;
    }

}
