package com.cypherfund.bbn.services.contract;

public interface IAuditLogService {
    void log(Object entity, String message, String action);
}
