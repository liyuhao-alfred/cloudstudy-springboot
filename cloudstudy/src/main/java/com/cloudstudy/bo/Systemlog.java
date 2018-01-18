package com.cloudstudy.bo;

import java.util.Date;

public class Systemlog {
    private Integer id;

    private String logLevel;

    private Date logTime;

    private Integer logType;

    private String remoteCallIp;

    private String threadId;

    private String threadName;

    private String serviceClass;

    private String serviceMethod;

    private Integer serviceResultFlage;

    private String serviceErrorCode;

    private String serviceRunningTime;

    private String serviceArgs;

    private String serviceResult;

    private String serviceErrorMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel == null ? null : logLevel.trim();
    }

    public Date getLogTime() {
        return logTime;
    }

    public void setLogTime(Date logTime) {
        this.logTime = logTime;
    }

    public Integer getLogType() {
        return logType;
    }

    public void setLogType(Integer logType) {
        this.logType = logType;
    }

    public String getRemoteCallIp() {
        return remoteCallIp;
    }

    public void setRemoteCallIp(String remoteCallIp) {
        this.remoteCallIp = remoteCallIp == null ? null : remoteCallIp.trim();
    }

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId == null ? null : threadId.trim();
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName == null ? null : threadName.trim();
    }

    public String getServiceClass() {
        return serviceClass;
    }

    public void setServiceClass(String serviceClass) {
        this.serviceClass = serviceClass == null ? null : serviceClass.trim();
    }

    public String getServiceMethod() {
        return serviceMethod;
    }

    public void setServiceMethod(String serviceMethod) {
        this.serviceMethod = serviceMethod == null ? null : serviceMethod.trim();
    }

    public Integer getServiceResultFlage() {
        return serviceResultFlage;
    }

    public void setServiceResultFlage(Integer serviceResultFlage) {
        this.serviceResultFlage = serviceResultFlage;
    }

    public String getServiceErrorCode() {
        return serviceErrorCode;
    }

    public void setServiceErrorCode(String serviceErrorCode) {
        this.serviceErrorCode = serviceErrorCode == null ? null : serviceErrorCode.trim();
    }

    public String getServiceRunningTime() {
        return serviceRunningTime;
    }

    public void setServiceRunningTime(String serviceRunningTime) {
        this.serviceRunningTime = serviceRunningTime == null ? null : serviceRunningTime.trim();
    }

    public String getServiceArgs() {
        return serviceArgs;
    }

    public void setServiceArgs(String serviceArgs) {
        this.serviceArgs = serviceArgs == null ? null : serviceArgs.trim();
    }

    public String getServiceResult() {
        return serviceResult;
    }

    public void setServiceResult(String serviceResult) {
        this.serviceResult = serviceResult == null ? null : serviceResult.trim();
    }

    public String getServiceErrorMessage() {
        return serviceErrorMessage;
    }

    public void setServiceErrorMessage(String serviceErrorMessage) {
        this.serviceErrorMessage = serviceErrorMessage == null ? null : serviceErrorMessage.trim();
    }
}