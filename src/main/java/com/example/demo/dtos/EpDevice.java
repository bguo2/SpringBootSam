package com.example.demo.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class EpDevice {
    private String serialNumber;
    private String authorizationCode;
    private int useCount;
    private String description;
    private Date expirationDate;
    private String ascName;
    private String deviceName;
    private String deviceTag;
    private boolean isEsm;

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(Integer useCount) {
        if(useCount == null)
            this.useCount = 0;
        else
            this.useCount = useCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getAscName() {
        return ascName;
    }

    public void setAscName(String ascName) {
        this.ascName = ascName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceTag() {
        return deviceTag;
    }

    public void setDeviceTag(String deviceTag) {
        this.deviceTag = deviceTag;
    }

    public boolean getIsEsm() {
        return isEsm;
    }

    public void setIsEsm(Boolean esm) {
        if(esm == null)
            this.isEsm = false;
        else
            this.isEsm = esm;
    }
}
