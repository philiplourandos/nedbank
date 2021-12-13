# Introduction

We have seen that business requires text changes which are hardcoded into the backend services. Changing the text requires a full round of testing for deployment.

# Solution

Using a simple spring boot application and config server we are able to address the following issues listed above:

* Changes to text is committed to git and audit. This does not require an app deployment
* Config server polls the git pull and will pull down the latest changes from the repo to serve to incoming requests
* Bitbucket/azure can be configured to restrict only certain users to modify the required text
* Simple. If the key is in the resouce bundle return the text, otherwise return a 404
* Cost effective
