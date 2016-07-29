# Event Audit Trail Sample Application

A sample project that integrates with the Event Audit Trail Service

## Table of Contents

  - [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Configure](#configure)
    - [UAA configuration](#uaa-configuration)
        - [Client credentials](#client-credentials)
        - [User login](#user-login)
  - [Build](#build)
  - [Run](#run)
    - [Run Locally](#run-locally)
  - [Deploy on Cloud Foundry](#deploy-on-cloud-foundry)
  - [APIs](#apis)
  - [Development](#development)

## Getting Started

This project will demonstrate how to incorporate the Event Audit Trail Service using the Event Audit Trail SDK into your project.  The Event Audit Trail SDK offers an abstracted access to the Event Audit Trail Service.

## Prerequisites

* [Emerging Verticals Commons](https://github.build.ge.com/emerging-verticals/commons) - Commons library for Emerging Verticals
* [Event Audit Trail SDK](https://github.build.ge.com/emerging-verticals/event-audit-trail-sdk) - Event Audit Trail SDK

## Configure

### Create instance of Event Audit Trail Service

To create an instance of the Event Audit Trail service using the Cloud Foundry cli use the following command:

```
cf create-service event-audit-trail <plan> <service_instance_name> -c '{“trustedIssuerId”: “<predix uaa instance url>/oauth/token”}'
```

**Please Note**: A [Predix UAA](https://www.predix.io/services/service.html?id=1172) instance is required.

### Bind service instance to app

Once the sample app is deployed, bind the service application to the sample application.

```
cf bs <event audit trail sample app> <service_instance_name>
```

Once the app has been bound to the service instance, you can view the environment variables to see the tenant uuid and scope:

The application's *VCAP SERVICES* will contain the following variables:

```
"event-audit-trail": [
   {
    "credentials": {
     "catalog-uri": "<service url>",
     "tenant-uuid": "<tenant uuid>",
     "version": "1",
     "trusted-issuer-ids": "<trusted issuers>",
     "zone-oauth-scope": "event-audit-trail.zone.<tenant uuid>.user"
    },
    "label": "event-audit-trail",
    "name": "<service_instance_name>",
    "plan": "<plan>",
    "provider": null,
    "syslog_drain_url": null,
    "tags": [
     "audit",
     "event",
     "trail"
    ]
   }
```

### UAA configuration

Once a tenant uuid has been issued to your instance you will need to add the **zone-oath-scope** to your UAA instance clients.

#### Client credentials

To use the event audit trail service with the client credentials flow (the SDK requires this), then you need to add the **zone-oath-scope** to your client authorities.  You can do this using the UAA cli with the following command:

```
uaac client update <client_name> --authorities "<list_of_authorities> event-audit-trail.zone.<tenant uuid>.user"
```

Tokens issue by this client will now be authorized to use the event audit trail service instance.


#### User login

To use the event audit service with a user-authenticated flow (e.g. predix-seed), you will need to add a group with the proper scope using the UAA cli.

```
uaac client update <client_name> --scope "<list_of_scope> event-audit-trail.zone.<tenant uuid>.user"

uaac group add event-audit-trail.zone.<tenant uuid>.user

uaac member add event-audit-trail.zone.<tenant uuid>.user <users...>
```

Now tokens issued to this user will be authorized to use the event audit trail service instance.

** Note **  Please make sure you build the prerequisites or they are a part of your repository.

Please set the following parameters in the **application.properties** file.  These properties are used to override properties defined in EventAudit TrailServiceManager in the SDK.

## Build

** Note **  Please make sure you build the prerequisites or they are a part of your repository.

To build the sample project

```bash
mvn clean install
```

## Run

### Run Locally
```bash
java -jar target/event-audit-trail-sample-1.0.0-SNAPSHOT.jar
```

## Deploy on Cloud Foundry

A sample manifest has been supplied for ease of use.  The following parameters must be set in the manifest

**<event-audit-trail-service-instance-name>**

**<uaa-client-id>**

**<uaa-client-secret>**

**<predix-uaa-instance-url>**

### Test
A Sample Postman Collection has been provided to help you test the sample application [here](https://github.build.ge.com/emerging-verticals/event-audit-trail-sample/blob/master/EventAuditTrailSample.json.postman_collection)

## APIs

The sample application contains 3 Rest endpoints.  Each endpoint will generate a different event.  Every call to the APIs will generate a new event.

* GET /test1

* GET /test2

* GET /test3

The events generated will have the following format:

```
{
      "id": 1016,
      "uuid": "cd3f0250-809c-4015-a2cc-35948994db70",
      "tenantUuid": "286db717-a360-4d94-ad59-c2a0fc8b0e45",
      "context": "test3-service-call",
      "tag": "web",
      "classification": 0,
      "enabled": true,
      "timestamp": 1468532481381,
      "lastUpdated": null,
      "data": "{\"data\": \"test3 data\"}"
    }
```

## Development

A sample aspect has been provided as a guideline on how to integrate with Event Audit Trail Service as a cross-cutting concern.  You can read more about
Spring aspect-oriented programming [here](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/aop.html).




