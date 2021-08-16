package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.dto.HistoryDataDTO;

import java.util.List;

public interface IFormHistoryDataService {

    public List<HistoryDataDTO> historyDataShow(String instanceId);
}
