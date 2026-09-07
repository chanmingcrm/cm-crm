package com.platform.mesh.netty.server.soa.msg.factory;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description 消息广播工厂
 * @author 蝉鸣
 */
public class CcChannelFactory {

    private static final Logger log = LoggerFactory.getLogger(CcChannelFactory.class);

    /**
     * ChannelGroup 无法序列化
     * 房间号 -> ChannelGroup
     * 管理房间内的所有channel
     */
    private final Map<String, ChannelGroup> channelGroupMap = new ConcurrentHashMap<>();
    /**
     * Channel 无法序列化
     * 人员 -> Channel
     * 人员对应连接
     */
    private final Map<String, Channel> channelUserMap = new ConcurrentHashMap<>();


    /**
     * 功能描述:
     * 〈获取通道组中得Channel〉
     * @param groupHash groupHash
     * @author 蝉鸣
     */
    public ChannelGroup getGroup(String groupHash){
        if(ObjectUtil.isEmpty(groupHash)){
            return null;
        }
        //查询当前group是否存在
        if(!channelGroupMap.containsKey(groupHash)){
            return null;
        }
        return channelGroupMap.get(groupHash);
    }

    /**
     * 功能描述:
     * 〈获取Channel〉
     * @param userHash userHash
     * @author 蝉鸣
     */
    public Channel getChanel(String userHash){
        if(ObjectUtil.isEmpty(userHash)){
            return null;
        }
        //查询是否存在
        if(!channelUserMap.containsKey(userHash)){
            return null;
        }
        return channelUserMap.get(userHash);
    }

    /**
     * 功能描述:
     * 〈初始化通道组〉
     * @param groupHash groupHash
     * @param channel channel
     * @author 蝉鸣
     */
    public ChannelGroup addGroup(String groupHash, Channel channel){
        if(ObjectUtil.isEmpty(groupHash) || ObjectUtil.isEmpty(channel)){
            return null;
        }
        //查询当前group是否存在，如果不存在则新建，group分为临时和永久
        ChannelGroup channelGroup;
        if(channelGroupMap.containsKey(groupHash)){
            channelGroup =  channelGroupMap.get(groupHash);
        }else{
            channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        }
        //将当前Channel放入group
        channelGroup.add(channel);
        channelGroupMap.put(groupHash, channelGroup);
        return channelGroup;
    }

    /**
     * 功能描述:
     * 〈删除通道组中得Channel〉
     * @param groupHash groupHash
     * @param channel channel
     * @author 蝉鸣
     */
    public void delGroup(String groupHash, Channel channel){
        if(ObjectUtil.isEmpty(groupHash) || ObjectUtil.isEmpty(channel)){
            return;
        }
        //查询当前group是否存在
        if(!channelGroupMap.containsKey(groupHash)){
            return;
        }
        ChannelGroup channelGroup =  channelGroupMap.get(groupHash);
        channelGroup.remove(channel);
    }

    /**
     * 功能描述:
     * 〈用户是否在线〉
     * @param userHash userHash
     * @author 蝉鸣
     */
    public Boolean userOnLine(String userHash){
        if(ObjectUtil.isEmpty(userHash)){
            return Boolean.FALSE;
        }
        if(channelUserMap.containsKey(userHash)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    /**
     * 功能描述:
     * 〈增加用户连接〉
     * @param userHash userHash
     * @param channel channel
     * @author 蝉鸣
     */
    public void addUser(String userHash, Channel channel){
        if(ObjectUtil.isEmpty(userHash) || ObjectUtil.isEmpty(channel)){
            return;
        }
        //查询是否存在，如果不存在则新增
        if(channelUserMap.containsKey(userHash)){
            return;
        }else{
            channelUserMap.put(userHash, channel);
        }
    }

    /**
     * 功能描述:
     * 〈删除用户连接〉
     * @param userHash userHash
     * @param channel channel
     * @author 蝉鸣
     */
    public void delUser(String userHash, Channel channel){
        if(ObjectUtil.isEmpty(userHash) || ObjectUtil.isEmpty(channel)){
            return;
        }
        channelUserMap.remove(userHash,channel);
    }

    /**
     * 功能描述:
     * 〈获取通道组中得Channel〉
     * @param obMsg obMsg
     * @author 蝉鸣
     */
    public void sendGroupMsg(String groupHash, Object obMsg){
        if(ObjectUtil.isEmpty(groupHash) || ObjectUtil.isEmpty(obMsg)){
            return;
        }
        ChannelGroup channelGroup = getGroup(groupHash);
        if(ObjectUtil.isEmpty(channelGroup)){
            return;
        }
        try {
            channelGroup.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(obMsg)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 功能描述:
     * 〈获取通道组中得Channel〉
     * @param obMsg obMsg
     * @author 蝉鸣
     */
    public void sendUserMsg(String userHash, Object obMsg){
        if(ObjectUtil.isEmpty(userHash) || ObjectUtil.isEmpty(obMsg)){
            return;
        }
        Channel channel = getChanel(userHash);
        if(ObjectUtil.isEmpty(channel)){
            return;
        }
        try {
            channel.writeAndFlush(new TextWebSocketFrame(JSONUtil.toJsonStr(obMsg)));
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
