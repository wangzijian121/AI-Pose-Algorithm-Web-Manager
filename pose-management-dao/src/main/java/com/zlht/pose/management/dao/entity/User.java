package com.zlht.pose.management.dao.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.omg.CORBA.IDLType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "user", autoResultMap = true)
public class User {

    @TableId(type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer id;

    @ApiModelProperty(value = "用户类型", required = true)
    private Integer type;

    @ApiModelProperty(value = "昵称", required = false)
    private String nickname;


    @ApiModelProperty(value = "用户名", required = true)
    private String username;


    @ApiModelProperty(value = "123456", required = true)
    private String password;


    @ApiModelProperty(value = "备注", required = false)
    private String mark;


    @ApiModelProperty(value = "创建时间 iso:YYYY-MM-DDTHH:mm:ss.sssZ", required = true)
    private Date createTime;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @ApiModelProperty(value = "{\"creator\": 0, \"institution\": \"润迪体育\", \"lastUsedTime\": \"2021-10-01 10:30:00\", \"lastUsedDuration\": \"2\", \"configurableMiniProgram\": false}", required = true)
    private Map<String, Object> attr;


}