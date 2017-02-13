package com.rytec.rec.util;

/**
 * Created by danny on 17-1-17.
 * <p>
 * 返回值的含义
 */

@JSExport("ERROR_CODE")
public interface ConstantErrorCode {

    @JSExport("成功")
    int RST_SUCCESS = 0;


    // DEVICE 操作的错误代码
    @JSExport("设备未找到")
    int DEVICE_NOT_FOUND = 101;             //设备未找到
    @JSExport("设备的该功能端口未配置")
    int DEVICE_FUN_NOT_CONFIG = 102;        //设备的该功能未配置
    @JSExport("设备没有该功能")
    int DEVICE_FUN_NOT_EXIST = 103;         //设备没有该功能

    // Node通讯的错误列表
    @JSExport("该类型的节点不存在")
    int NODE_TYPE_NOTEXIST = 201;           //该类型的Node不存在

    // Channel错误消息
    @JSExport("通道未连接")
    int CHA_NOT_CONNECT = 301;

}