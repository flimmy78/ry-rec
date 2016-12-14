package com.rytec.rec.channel.ModbusTcpServer;

import com.rytec.rec.bean.ChannelNode;
import com.rytec.rec.channel.ModbusTcpServer.channel.ModbusChannelInitializer;
import com.rytec.rec.channel.ModbusTcpServer.exception.ConnectionException;
import com.rytec.rec.db.DbConfig;
import com.rytec.rec.node.AbstractNode;
import com.rytec.rec.node.NodeFactory;
import com.rytec.rec.util.ChannelType;
import com.rytec.rec.util.CommandType;
import com.rytec.rec.util.FromWhere;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by danny on 16-11-22.
 */

@Service
@ChannelType(1001)
public class ModbusTcpServer {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    //端口配置
    @Value("${tcp.server.modbus.port}")
    private int port;

    private ServerBootstrap bootstrap;

    private Channel parentChannel;

    // 对应的客户端连接列表
    public final ConcurrentHashMap<String, Channel> clients = new ConcurrentHashMap<String, Channel>();

    // 建立服务器
    @PostConstruct
    public void setup() throws ConnectionException {

        initConfig();

        try {
            final EventLoopGroup bossGroup = new NioEventLoopGroup();
            final EventLoopGroup workerGroup = new NioEventLoopGroup();

            bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    // 数据流处理的pip，完成所有的数据处理逻辑
                    .childHandler(new ModbusChannelInitializer(this))
                    // 连接队列的长度
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // Bind and start to accept incoming connections.
            // ChannelFuture 对象，同步调用
            parentChannel = bootstrap.bind(port).sync().channel();

            //添加一个关闭事件的监听
            parentChannel.closeFuture().addListener(new GenericFutureListener<ChannelFuture>() {

                // Sever 关闭完成时调用，匿名对象
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();

                }
            });
        } catch (Exception ex) {
            Logger.getLogger(ModbusTcpServer.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());

            throw new ConnectionException(ex.getLocalizedMessage());
        }
    }


    //关闭服务，这里是同步还是异步？
    @PreDestroy
    public void close() {
        if (parentChannel != null) {
            parentChannel.close().awaitUninterruptibly();
        }
    }

    //----------------------------------对通道的初始化-----------------------------

    @Autowired
    private DbConfig dbConfig;

    public ConcurrentHashMap<String, ConcurrentHashMap> channelNodes = new ConcurrentHashMap<String, ConcurrentHashMap>();

    /*
    * 初始化对应的HashMap
    * 两级 HashMap
    * 第一级：ip:id->Map
    * 第二级：add:no->ChannelNode
     */
    private void initConfig() {

        List<ChannelNode> channelNodes = dbConfig.getChannelNodeList();


        //第一级的Map ip:id-> map
        for (ChannelNode cn : channelNodes) {
            if (cn.getCtype() != 1001) {
                continue;
            }

            //每一个Channel的标识是IP：PORT
            String hubId = cn.getIp() + ':' + cn.getPort();

            //是否已经存在该Channel
            ConcurrentHashMap<Integer, ChannelNode> hub = this.channelNodes.get(hubId);

            //不存在，建立该Channel
            if (hub == null) {
                hub = new ConcurrentHashMap<Integer, ChannelNode>();
                this.channelNodes.put(hubId, hub);
            }

            hub.put(cn.getAdd() + ':' + cn.getNo(), cn);
        }
    }

    /*
    * 通讯率 9600
    * 每秒1000个字节（双向累计）
    * 每个通讯过程20个字节（最大）
    * 最多可以是50个通讯过程
    * 每个过程约20个毫秒
    * 因此50~100个毫秒是安全的
    *
    * 每个Channel（Socket 连接都需要进行轮训）通过一个HashMap来进行
    * <String, Integer>   ip:port -> 当前的轮训位置
    */
    @Scheduled(fixedDelay = 1000)
    private void doOnTime() {
        for (String key : clients.keySet()) {
            Channel cha = clients.get(key);
            AbstractNode node = NodeFactory.getNode(1001);
            cha.write(node.genFrame(FromWhere.FROM_SYS,1,0, CommandType.CMD_OFF));
        }

    }

}
