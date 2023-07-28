package com.zlht.pose.management.api.controller;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zlht.pose.management.api.service.WeChatServicesI;
import com.zlht.pose.management.api.utils.Result;
import com.zlht.pose.management.dao.entity.WeChat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Api(tags = "小程序信息管理", description = "小程序信息管理")
public class WeChatController extends BaseController {

    private static final Logger logger = LogManager.getLogger(WeChatController.class);
    @Autowired
    WeChatServicesI weChatServices;


    /**
     * 查询小程序信息信息
     *
     * @return weChat
     */
    @ApiOperation(value = "查询小程序信息", notes = "查询小程序信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "小程序审核进度(0已部署，1审核中)", dataTypeClass = int.class),
            @ApiImplicitParam(name = "pageNum", value = "页数(默认1)", dataTypeClass = int.class),
            @ApiImplicitParam(name = "pageSize", value = "页大小(默认10)", dataTypeClass = int.class),
            @ApiImplicitParam(name = "keyword", value = "小程序信息名", dataTypeClass = String.class)
    })
    @GetMapping(value = "/getWeChat")
    @ResponseStatus(HttpStatus.OK)
    public Result<WeChat> queryWeChatList(@RequestParam(required = false, defaultValue = "-1") int status,
                                          @RequestParam(required = false, defaultValue = "1") int pageNum,
                                          @RequestParam(required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(required = false) String keyword) {

        Result result = checkPageParams(pageNum, pageSize);
        if (!result.checkResult()) {
            return result;
        }
        return weChatServices.queryWeChatList(pageNum, pageSize, status, keyword);
    }

    /**
     * 创建小程序信息
     *
     * @return WeChat
     */
    @ApiOperation(value = "创建小程序信息", notes = "创建小程序信息")
    @PostMapping(value = "/createWeChat")
    @ResponseStatus(HttpStatus.OK)
    @JsonIgnoreProperties(value = "id")
    public Result<WeChat> createWeChat(@RequestBody WeChat weChat) {
        Map<String, Object> map = weChatServices.createWeChat(weChat);
        return returnDataList(map);
    }

    /**
     * 更新小程序信息
     *
     * @return WeChat
     */
    @ApiOperation(value = "更新小程序信息", notes = "更新小程序信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要更新的小程序信息ID", required = true, dataTypeClass = int.class)
    })
    @PutMapping(value = "/updateWeChat")
    @ResponseStatus(HttpStatus.OK)
    public Result<WeChat> updateWeChat(@RequestParam int id,
                                       @RequestBody WeChat weChat) {
        Map<String, Object> map = weChatServices.updateWeChat(id, weChat);
        return returnDataList(map);
    }

    /**
     * 删除小程序信息
     *
     * @return WeChat
     */
    @ApiOperation(value = "删除小程序信息", notes = "删除小程序信息")
    @DeleteMapping(value = "/deleteWeChat")
    @ResponseStatus(HttpStatus.OK)
    public Result<WeChat> deleteWeChat(@RequestParam int id) {
        Map<String, Object> map = weChatServices.deleteWeChat(id);
        return returnDataList(map);
    }
}
