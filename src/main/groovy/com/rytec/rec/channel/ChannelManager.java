package com.rytec.rec.channel;

import com.rytec.rec.util.ChannelType;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * 所有的Channel的管理
 * Created by danny on 16-12-16.
 */

@Service
public class ChannelManager {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ApplicationContext context;

    // Channel 接口对象的列表
    public static HashMap<Integer, Object> channelInterfaceMap = new HashMap();

    @PostConstruct
    private void init() {
        Map<String, Object> channels = context.getBeansWithAnnotation(ChannelType.class);
        for (Object channel : channels.values()) {
            Class<? extends Object> channelClass = channel.getClass();
            ChannelType annotation = channelClass.getAnnotation(ChannelType.class);
            channelInterfaceMap.put(annotation.value(), channel);
        }

    }

    // 得到一个Channel的Interface
    public static ChannelInterface getChannelInterface(int type) {
        return (ChannelInterface) channelInterfaceMap.get(type);
    }

    // Channnel 的状态改变
    public void channelOnState(String cid, int state) {
        logger.debug("通道信息：" + cid + '-' + state);
    }

}