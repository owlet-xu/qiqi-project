package com.attach.springboot.attach.bz1.service.entity;

import javax.validation.constraints.Size;

public class MetadataEntity {
    // 系统，比如qiqi系统，不能为空，用于文件存储路径
    @Size(max = 50)
    private String system;

    @Size(max = 100)
    private String module;

    @Size(max = 36)
    private String businessId;

    @Size(max = 36)
    private String remark;

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
