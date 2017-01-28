package com.rytec.rec.db;

import com.rytec.rec.db.mapper.ChannelNodeMapper;
import com.rytec.rec.db.mapper.DeviceMapper;
import com.rytec.rec.db.mapper.DeviceNodeMapper;
import com.rytec.rec.db.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by danny on 16-12-28.
 * <p>
 * 数据库的所有配置信息都在这里集中读取和初始化
 */

@Service
public class DbConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<ChannelNode> channelNodeList;
    private List<DeviceNode> deviceNodeList;
    private List<Device> deviceList;

    @Autowired
    ChannelNodeMapper channelNodeMapper;

    @Autowired
    DeviceNodeMapper deviceNodeMapper;

    @Autowired
    DeviceMapper deviceMapper;


    /**
     * 初始化 ChannelNode 列表
     */
    private void initChannelNode() {

        ChannelNodeExample channelNodeExample = new ChannelNodeExample();

        channelNodeExample.createCriteria().andNidGreaterThan(0);

        channelNodeList = channelNodeMapper.selectByExample(channelNodeExample);

    }

    /**
     * 初始化 DeviceNode 列表
     */
    private void initDeviceNode() {

        DeviceNodeExample deviceNodeExample = new DeviceNodeExample();

        deviceNodeExample.createCriteria().andNidGreaterThan(0);

        deviceNodeList = deviceNodeMapper.selectByExample(deviceNodeExample);
    }

    /**
     * 初始化Device 列表
     */
    private void initDevice() {
        deviceList = deviceMapper.selectByExample(null);
    }


    /**
     * @return ChannelNode 的列表
     */
    public List<ChannelNode> getChannelNodeList() {
        if (channelNodeList == null) {
            initChannelNode();
        }
        return channelNodeList;
    }

    /**
     * DeviceNode 的列表
     *
     * @return
     */
    public List<DeviceNode> getDeviceNodeList() {
        if (deviceNodeList == null) {
            initDeviceNode();
        }
        return deviceNodeList;
    }

    /**
     * Device 列表
     *
     * @return
     */
    public List<Device> getDeviceList() {
        if (deviceList == null) {
            initDevice();
        }
        return deviceList;
    }

}
