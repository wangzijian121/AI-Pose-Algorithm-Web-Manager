package com.zlht.pbr.algorithm.management.api.wechat.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.zlht.pbr.algorithm.management.api.wechat.service.WxReportUserServiceI;
import com.zlht.pbr.algorithm.management.base.impl.BaseServiceImpl;
import com.zlht.pbr.algorithm.management.dao.entity.User;
import com.zlht.pbr.algorithm.management.dao.entity.WeChat;
import com.zlht.pbr.algorithm.management.dao.mapper.UserMapper;
import com.zlht.pbr.algorithm.management.dao.mapper.WeChatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zijian Wang
 */
@Service
public class WxReportUserServiceImpl extends BaseServiceImpl implements WxReportUserServiceI {

    @Autowired
    private WeChatMapper weChatMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reportUser(Map<String, Object> reportMap, int event) {

        QueryWrapper<WeChat> weChatQueryWrapper = new QueryWrapper<>();
        weChatQueryWrapper.eq("app_id", reportMap.get("appId"));
        WeChat weChat = weChatMapper.selectOne(weChatQueryWrapper);

        User user = new User();
        user.setNickname(reportMap.get("nickname").toString());
        user.setType(3);
        user.setCreateTime(new Date());
        Map<String, Object> map = new HashMap<>(3);
        map.put("appName", weChat.getName());
        map.put("openId", reportMap.get("openId"));
        map.put("appId", reportMap.get("appId"));
        user.setAttr(map);

        if (event == 0) {
//            insert
            userMapper.insert(user);

        } else if (event == 1) {
//            update
            UpdateWrapper queryWrapper = new UpdateWrapper();
            queryWrapper.eq("attr->'$.appId'", reportMap.get("appId"));
            queryWrapper.set("nickname", user.getNickname());
            userMapper.update(null, queryWrapper);
        }
    }
}
