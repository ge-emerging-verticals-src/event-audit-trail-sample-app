package com.ge.ev.EventAuditTrail.sample;

import com.ge.ev.EventAuditTrail.annotations.Auditable;
import com.ge.ev.EventAuditTrail.managers.EventAuditTrailServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController
{
    @Autowired
    EventAuditTrailServiceManager eventAuditTrailServiceManager;

    @Auditable(context="test1-service-call", tag="web", data="{\"data\": \"test1 data\"}")
    @RequestMapping(value = "test1",  method = RequestMethod.GET)
    public ResponseEntity<String> test1() throws Exception
    {
        return new ResponseEntity<>("GET test 1", HttpStatus.OK);
    }

    @Auditable(context="test2-service-call", tag="web", data="{\"data\": \"test2 data\"}")
    @RequestMapping(value = "test2",  method = RequestMethod.GET)
    public ResponseEntity<String> test2() throws Exception
    {
        return new ResponseEntity<>("GET test 2", HttpStatus.OK);
    }

    @Auditable(context="test3-service-call", tag="web", data="{\"data\": \"test3 data\"}")
    @RequestMapping(value = "test3",  method = RequestMethod.GET)
    public ResponseEntity<String> test3() throws Exception
    {
        return new ResponseEntity<>("GET test 3", HttpStatus.OK);
    }
}