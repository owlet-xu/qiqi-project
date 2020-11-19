package com.attach.springboot.attach.bz1.contract.model;

import javax.validation.constraints.Size;

public class MetadataInfo {
    @Size(max = 50)
    private String system;

    @Size(max = 100)
    private String module;

    @Size(max = 36)
    private String businessId;

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
}
