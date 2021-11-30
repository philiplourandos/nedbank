# Introduction

 

This is a proof of concept(POC) for nedbank to develop a micro-service to retrieve rates that the South African Reserve Bank(SARB) publish.

Currently these values seem to be hard coded in the various applications and requires a redeployment of the applications.

 

# Feature requirements

 

* We need a cron process to retrieve the latest rates and store in and in memory cache

* On startup of the application we need to retrieve the rates from SARB

* We need an API to allow services in the bank to retrieve the required rates

 

# Non function requirements

 

* We want this service to adhere to good design principals

* We want test code

* We want it to be performant

 

# Technology

 

As there is a drive at nedbank to move to spring we want to implement this with spring.

 

As we don't have any idea how this will be used by various services we want the API to be performant. The spring reactive libraries will make this feasible.

We can use the caffeine libaray as an in memory cache and look up the various rates from the cache.

We want to use the experimental support for Ahead Of Time(AOT) compilation to further increase performance.

 

As we want to profile the application we would like to use k6.io as the tool to perform this testing as it is very simple using javascript to define the tests.
