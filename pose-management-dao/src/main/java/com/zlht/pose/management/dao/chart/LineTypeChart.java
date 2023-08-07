package com.zlht.pose.management.dao.chart;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LineTypeChart {

    @JsonProperty("X")
    private List<String> xList;
    @JsonProperty("Y")
    private Map<String, List<String>> yList;

}