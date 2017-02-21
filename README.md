# Carbon Identity Commons

---

|  Branch | Build Status |
| :------------ |:-------------
| master      | [![Build Status](https://wso2.org/jenkins/buildStatus/icon?job=carbon-identity-commons)](https://wso2.org/jenkins/job/carbon-identity-commons) |


---
Carbon Identity Commons project includes common components for all Carbon Identity components.
## Features:
* Identity Commons.
* Event framework.

## Getting Started

This component is currently under development and can try-out as follows.

## Download

Use Maven snippet:

Commons component:
````xml
<dependency>
    <groupId>org.wso2.carbon.identity.commons</groupId>
    <artifactId>org.wso2.carbon.identity.commons</artifactId>
    <version>${carbon.identity.commons.version}</version>
</dependency>
````

Events component:
````xml
<dependency>
    <groupId>org.wso2.carbon.identity.commons</groupId>
    <artifactId>org.wso2.carbon.identity.event</artifactId>
    <version>${carbon.identity.commons.version}</version>
</dependency>
````
### Snapshot Releases

Use following Maven repository for snapshot versions of Carbon Identity Commons.

````xml
<repository>
    <id>wso2.snapshots</id>
    <name>WSO2 Snapshot Repository</name>
    <url>http://maven.wso2.org/nexus/content/repositories/snapshots/</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>daily</updatePolicy>
    </snapshots>
    <releases>
        <enabled>false</enabled>
    </releases>
</repository>
````

### Released Versions

Use following Maven repository for released stable versions of Carbon Identity Commons.

````xml
<repository>
    <id>wso2.releases</id>
    <name>WSO2 Releases Repository</name>
    <url>http://maven.wso2.org/nexus/content/repositories/releases/</url>
    <releases>
        <enabled>true</enabled>
        <updatePolicy>daily</updatePolicy>
        <checksumPolicy>ignore</checksumPolicy>
    </releases>
</repository>
````
## Building From Source

Clone this repository first (`https://github.com/wso2/carbon-identity-commons.git`) and use Maven install to build
`mvn clean install`.

## Contributing to Carbon Identity Commons Project

Pull requests are highly encouraged and we recommend you to create a [JIRA](https://wso2.org/jira/projects/IDENTITY/issues/IDENTITY) to discuss the issue or feature that you
 are contributing to.

## License

Carbon Identity Commons is available under the Apache 2 License.

## Copyright

Copyright (c) 2017, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.