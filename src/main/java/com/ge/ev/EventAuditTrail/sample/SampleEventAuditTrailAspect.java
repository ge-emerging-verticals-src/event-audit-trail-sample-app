package com.ge.ev.EventAuditTrail.sample;

import com.ge.ev.EventAuditTrail.annotations.Auditable;
import com.ge.ev.EventAuditTrail.environment.VcapCredentials;
import com.ge.ev.EventAuditTrail.events.AuditableEvent;
import com.ge.ev.EventAuditTrail.managers.EventAuditTrailServiceManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Aspect
public class SampleEventAuditTrailAspect
{
    @Autowired
    VcapCredentials vcapCredentials;

    @Autowired
    EventAuditTrailServiceManager eventAuditTrailServiceManager;

    @After("@annotation(auditable)")
    public void addLoggableEvent(JoinPoint joinPoint, Auditable auditable) throws Throwable
    {
        eventAuditTrailServiceManager.setAuditTrailBaseUrl(vcapCredentials.getCatalogUri());
        eventAuditTrailServiceManager.setVersion(vcapCredentials.getVersion());

        System.out.println("==eventAuditTrailServiceManager==" +  eventAuditTrailServiceManager.toString());

        ArrayList<AuditableEvent> auditableEvents = new ArrayList<AuditableEvent>();

        AuditableEvent eventLoggerEvent = new AuditableEvent();
        eventLoggerEvent.setTenantUuid(vcapCredentials.getTenantUuid());
        eventLoggerEvent.setContext(auditable.context());
        eventLoggerEvent.setTag(auditable.tag());
        eventLoggerEvent.setClassification(auditable.classification());
        eventLoggerEvent.setData(auditable.data());
        auditableEvents.add(eventLoggerEvent);
        eventAuditTrailServiceManager.addEvents(vcapCredentials.getTenantUuid(), auditableEvents);

        System.out.println(eventAuditTrailServiceManager.toString());

    }
}
