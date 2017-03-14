package com.rytec.rec.channel.Video_DH;

import com.rytec.rec.channel.ChannelInterface;
import com.rytec.rec.channel.ChannelMessage;
import com.rytec.rec.service.Video.VideoService;
import com.rytec.rec.util.AnnotationChannelType;
import com.rytec.rec.util.AnnotationJSExport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Danny on 2017/3/13.
 */
@Service
@AnnotationChannelType(2001)
@AnnotationJSExport("海康DVR")
public class VideoDH implements ChannelInterface {

    @Autowired
    VideoService videoService;

    @Override
    public int sendMsg(ChannelMessage msg) {
        return videoService.sendMsg(msg);
    }
}