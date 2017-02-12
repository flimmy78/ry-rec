package com.rytec.rec.node.DAM;

import com.rytec.rec.channel.ChannelMessage;
import com.rytec.rec.node.*;
import com.rytec.rec.node.node.NodeInput;
import com.rytec.rec.util.Description;
import com.rytec.rec.util.NodeType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by danny on 16-12-14.
 * <p>
 * 命令格式：
 * byte     modbus地址
 * byes     0x02 命令
 * word     端口地址，从0开始
 * word     数量：1
 * word     CRC16 (不生成)
 * <p>
 * 返回格式：
 * byte     modbus地址
 * byes     0x02 命令
 * byte     字节数
 * byte     端口值
 * word     CRC16 (不生成)
 */

@Service
@NodeType(1002)
@Description("DMA 输入")
public class DAMInput extends NodeInput implements NodeInterface {

    @Autowired
    NodeManager nodeManager;


    // 命令编码
    public ChannelMessage genMessage(int where, int nodeId, int cmd, int value) {

        NodeRuntimeBean nodeRuntimeBean = nodeManager.getChannelNodeByNodeId(nodeId);

        ChannelMessage frame = new ChannelMessage();

        frame.from = where;
        frame.nodeId = nodeId;
        frame.type = cmd;
        frame.responseLen = 6;

        ByteBuf buf = Unpooled.buffer(6);
        buf.writeByte(nodeRuntimeBean.channelNode.getAdr());
        buf.writeByte(0x02);
        buf.writeShort(nodeRuntimeBean.channelNode.getNo());
        buf.writeShort(0x01);

        frame.payload = buf.array();

        return frame;
    }

    // 回应解码
    public NodeMessage decodeMessage(ChannelMessage msg) {
        NodeMessage rst = new NodeMessage();
        rst.from = msg.from;
        rst.type = msg.type;
        rst.node = msg.nodeId;
        ByteBuf payload = (ByteBuf) msg.payload;
        int val = payload.getShort(3);
        if (val > 0) {
            rst.value = true;
        } else {
            rst.value = false;
        }

        return rst;
    }

}
