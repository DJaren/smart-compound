package cn.gov.suzhou.system.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.gov.suzhou.common.core.utils.StringUtils;
import cn.gov.suzhou.common.core.web.controller.BaseController;
import cn.gov.suzhou.common.core.web.domain.AjaxResult;
import cn.gov.suzhou.common.core.web.page.TableDataInfo;
import cn.gov.suzhou.common.log.annotation.Log;
import cn.gov.suzhou.common.log.enums.BusinessType;
import cn.gov.suzhou.common.security.utils.SecurityUtils;
import cn.gov.suzhou.system.domain.SysClientDetails;
import cn.gov.suzhou.system.service.ISysClientDetailsService;

/**
 * 终端配置 信息操作处理
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/client")
public class SysClientDetailsController extends BaseController
{
    @Autowired
    private ISysClientDetailsService sysClientDetailsService;

    /**
     * 查询终端配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:client:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysClientDetails sysClientDetails)
    {
        startPage();
        List<SysClientDetails> list = sysClientDetailsService.selectSysClientDetailsList(sysClientDetails);
        return getDataTable(list);
    }

    /**
     * 获取终端配置详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:client:query')")
    @GetMapping(value = "/{clientId}")
    public AjaxResult getInfo(@PathVariable("clientId") String clientId)
    {
        return AjaxResult.success(sysClientDetailsService.selectSysClientDetailsById(clientId));
    }

    /**
     * 新增终端配置
     */
    @PreAuthorize("@ss.hasPermi('system:client:add')")
    @Log(title = "终端配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysClientDetails sysClientDetails)
    {
        String clientId = sysClientDetails.getClientId();
        if (StringUtils.isNotNull(sysClientDetailsService.selectSysClientDetailsById(clientId)))
        {
            return AjaxResult.error("新增终端'" + clientId + "'失败，编号已存在");
        }
        sysClientDetails.setClientSecret(SecurityUtils.encryptPassword(sysClientDetails.getClientSecret()));
        return toAjax(sysClientDetailsService.insertSysClientDetails(sysClientDetails));
    }

    /**
     * 修改终端配置
     */
    @PreAuthorize("@ss.hasPermi('system:client:edit')")
    @Log(title = "终端配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysClientDetails sysClientDetails)
    {
        sysClientDetails.setClientSecret(SecurityUtils.encryptPassword(sysClientDetails.getClientSecret()));
        return toAjax(sysClientDetailsService.updateSysClientDetails(sysClientDetails));
    }

    /**
     * 删除终端配置
     */
    @PreAuthorize("@ss.hasPermi('system:client:remove')")
    @Log(title = "终端配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clientIds}")
    public AjaxResult remove(@PathVariable String[] clientIds)
    {
        return toAjax(sysClientDetailsService.deleteSysClientDetailsByIds(clientIds));
    }
}
