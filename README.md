# sbt-env
[![Build Status](https://travis-ci.org/ikamman/sbt-env.svg?branch=master)](https://travis-ci.org/ikamman/sbt-env)
[![Download](https://api.bintray.com/packages/nietsnie/sbt-plugins/sbt-env/images/download.svg) ](https://bintray.com/nietsnie/sbt-plugins/sbt-env/_latestVersion)
[![License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat "MIT")](LICENSE)

SBT plugin for loading *.env file variables into SBT global settings

## Installation

Add the following code to your sbt `project/plugins.sbt` file:

```sbt
    addSbtPlugin("com.github.ikamman" %% "sbt-env" % "0.9.x")
```
After this step you will find all variables in the following `Global` setting:
```sbt
    envVariables in Global
```

## Usage

Just create your `.env` file near your `build.sbt` file
```bash
    vi .env
```

add your variables:

```bash
    APPLICATION_VERSION=0.0.1-SNAPSHOT 
    # or
    export SERVER_PORT=25
    # or
    URL_PATH="https://github.com/ikamman/sbt-env"
```

### .env file name change
Here you can change the file name `.env`
```sbt
    envFileName in Global := "versions.env"
```

### Use definied variables to change anything in your sbt file
Due to fact that environment variables are stored in `Setting[Map[String, String]]` you can change anything in your sbt configuration:
```sbt
    version := envVariables.value("APPLICATION_VERSION")
```
