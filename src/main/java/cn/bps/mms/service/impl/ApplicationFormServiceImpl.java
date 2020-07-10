package cn.bps.mms.service.impl;

import cn.bps.common.lang.CustomizeExceptionCode;
import cn.bps.common.lang.LocalBizServiceException;
import cn.bps.mms.entity.Account;
import cn.bps.mms.entity.ApplicationForm;
import cn.bps.mms.entity.ApplicationFormItem;
import cn.bps.mms.entity.Material;
import cn.bps.mms.mapper.ApplicationFormMapper;
import cn.bps.mms.service.*;
import cn.bps.security.server.service.TokenService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Objects;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 申请单 服务实现类
 * </p>
 *
 * @author bps
 * @since 2020-06-21
 */
@Service
public class ApplicationFormServiceImpl extends ServiceImpl<ApplicationFormMapper, ApplicationForm> implements ApplicationFormService {

    @Resource
    private TokenService tokenService;

    @Resource
    private ApplicationFormItemService applicationFormItemService;

    @Resource
    private RecordService recordService;

    @Resource
    private MaterialService materialService;


    @Override
    public ApplicationForm getApplication(Account account) {
        QueryWrapper<ApplicationForm> wrapper = new QueryWrapper<>();
        wrapper
                .eq("available", true)
                .eq("user_id", account.getId());
        List<ApplicationForm> applicationForms = this.list(wrapper);
        ApplicationForm applicationForm = null;
        if(applicationForms.isEmpty()){
            applicationForm = new ApplicationForm();
            applicationForm.setUserId(account.getId());
            applicationForm.setUserName(account.getName());
            this.save(applicationForm);
        }else {
            applicationForm = applicationForms.get(0);
        }
        return applicationForm;
    }

    @Override
    public ApplicationForm getApplication(String tokenValue) {
        Account account = tokenService.getAccount(tokenValue);
        return getApplication(account);
    }

    @Override
    public void addMessage(ApplicationForm applicationForm, String tokenValue) {
        ApplicationForm myApplicationForm = getApplication(tokenValue);
        myApplicationForm.setMessage(applicationForm.getMessage());
        if(Objects.equal(myApplicationForm.getType(),"批量导入")){
            // 同步物料表
            List<ApplicationFormItem> applicationFormItems = applicationFormItemService.list(myApplicationForm);

            if(applicationFormItems.size() <= 0){
                throw new LocalBizServiceException(CustomizeExceptionCode.NO_CACHE_DATA,"没有缓存数据");
            }

            /* 新的物料 */
            List<Material> newMaterials = applicationFormItems.stream().map(this::initMaterial).filter(e -> StringUtils.isEmpty(e.getId()) == Boolean.TRUE).collect(Collectors.toList());
            materialService.saveBatch(newMaterials);

            /* 仓库中已有的物料 */
            List<Material> updateMaterials = applicationFormItems.stream().map(this::initMaterial).filter(e -> StringUtils.isEmpty(e.getId()) == Boolean.FALSE).collect(Collectors.toList());
            if(updateMaterials.size()>0){
                Collection<Material> materials = materialService.listByIds(updateMaterials.stream()
                        .map(Material::getId)
                        .collect(Collectors.toSet()));
                Map<String, Integer> oldMaterials = materials.stream().collect(Collectors.toMap(Material::getId, Material::getCount));
                updateMaterials.stream().map(e-> e.setCount(e.getCount()+oldMaterials.get(e.getId()))).collect(Collectors.toList());
                materialService.updateBatchById(updateMaterials);
            }


            /* 同步申请单项 */
            Set<String> materialNames = applicationFormItems.stream()
                    .map(ApplicationFormItem::getMaterialName).collect(Collectors.toSet());
            Map<String, Map<String, String>> nameStatusIdDict = materialService.getNameStatusIdDict(materialNames);
            applicationFormItems = applicationFormItems.stream().filter(e -> StringUtils.isEmpty(e.getMaterialId())).map(e -> {
                Map<String, String> statusIdDict = nameStatusIdDict.get(e.getMaterialName());
                if(statusIdDict != null){
                    e.setMaterialId(statusIdDict.get(e.getStatus()));
                }
                return e;
            }).collect(Collectors.toList());
            if(applicationFormItems.size() > 0){
                applicationFormItemService.updateBatchById(applicationFormItems);
            }
        }

        // 清空清单项
        applicationFormItemService.closeItems(myApplicationForm);

        // 记录信息
        recordService.record(myApplicationForm);

        // 关闭清单
        myApplicationForm.setAvailable(false);
        this.updateById(myApplicationForm);

    }

    private Material initMaterial(ApplicationFormItem item){
        Material material = new Material();

        String materialId = item.getMaterialId();

        if(StringUtils.isEmpty(materialId)){
            material.setName(item.getMaterialName());
            material.setCategoryId(item.getCategoryId());
            material.setRepositoryId(item.getRepositoryId());
            material.setSpecialLine(item.getSpecialLine());
            material.setStatus(item.getStatus());
        }else {
            material.setId(materialId);
        }
        material.setCount(item.getCount());
        return material;
    }
}
