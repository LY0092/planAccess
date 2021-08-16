package com.ruoyi.plan.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.file.FileUtils;
import com.ruoyi.plan.domain.PlanTarget;
import com.ruoyi.plan.domain.PlanTargetDTO;
import com.ruoyi.plan.domain.TargetItemFile;
import com.ruoyi.plan.service.IWorkTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 目标考核Controller
 *
 * @author ruoyi
 * @date 2021-05-28
 */
@RestController
@RequestMapping("/plan/workTarget")
public class WorkTargetController extends BaseController
{
    @Autowired
    private IWorkTargetService workTargetService;

    /**
     * 查询目标考核列表
     *
     * @param paramMap
     * @return
     */
    @PostMapping("/selectList")
    public TableDataInfo selectList(@RequestBody Map paramMap)
    {
        startPage();
        paramMap.put("userId", SecurityUtils.getUserId());
        List<Map> list = workTargetService.selectList(paramMap);
        return getDataTable(list);
    }


    /**
     * 查询目标考核项目列表
     *
     * @param target
     * @return
     */
    @PostMapping("/selectItemInfo")
    public AjaxResult selectItemInfo(@RequestBody PlanTarget target)
    {
        Map itemInfo = workTargetService.selectItemInfo(target.getId());
        return AjaxResult.success(itemInfo);
    }

    /**
     * 保存计划填报
     *
     * @param targetDTO
     * @return
     */
    @PostMapping("/savePlanInfo")
    public AjaxResult savePlanInfo(@RequestBody PlanTargetDTO targetDTO)
    {
        workTargetService.savePlanInfo(targetDTO);
        return success();
    }

    /**
     * 提交计划填报
     *
     * @param targetDTO
     * @return
     */
    @PostMapping("/submitPlanInfo")
    public AjaxResult submitPlanInfo(@RequestBody PlanTargetDTO targetDTO)
    {
        workTargetService.submitPlanInfo(targetDTO);
        return success();
    }



    /**
     * 导入工作计划
     *
     * @param file
     * @return
     * @throws Exception
     */
    @PostMapping("/uploadFile")
    public AjaxResult uploadFile(MultipartFile file, Long itemId)
    {
        TargetItemFile itemFile = new TargetItemFile();
        itemFile.setItemId(itemId);
        itemFile.setFileName(file.getOriginalFilename());
        itemFile.setFileSize(FileUtils.formatFileSize(file.getSize()));
        itemFile.setCreater(SecurityUtils.getLoginUser().getUser().getNickName());
        itemFile.setUploadTime(new Date());
        workTargetService.saveTargetItemFile(itemFile);
        return success();
    }

    /**
     * 删除附件
     *
     * @param itemFile
     * @return
     */
    @PostMapping("/deleteItemFile")
    public AjaxResult deleteItemFile(@RequestBody TargetItemFile itemFile)
    {
        workTargetService.deleteItemFile(itemFile.getId());
        return success();
    }


    /**
     * 查询附件列表
     *
     * @param itemFile
     * @return
     */
    @PostMapping("/selectFileList")
    public TableDataInfo selectFileList(@RequestBody TargetItemFile itemFile)
    {
        Long itemId = itemFile.getItemId();
        List list = workTargetService.selectFileList(itemId);
        return getDataTable(list);
    }






}
