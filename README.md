# sbt-env
[![Build Status](https://travis-ci.org/ikamman/sbt-env.svg?branch=master)](https://travis-ci.org/ikamman/sbt-env)
[![Download](https://api.bintray.com/packages/nietsnie/sbt-plugins/sbt-env/images/download.svg) ](https://bintray.com/nietsnie/sbt-plugins/sbt-env/_latestVersion)
[![License](https://img.shields.io/badge/license-MIT-blue.svg?style=flat "MIT")](LICENSE)

SBT plugin for loading *.env file variables into SBT global settings

## Installation

Add the following code to your sbt `project/plugins.sbt`:

```sbt
    resolvers += Resolver.bintrayRepo("nietsnie", "sbt-plugins")

    addSbtPlugin("com.github.ikamman" %% "sbt-env" % "0.9.x")
```
After this step you will find all variables in the `Global` scope:
```sbt
    envVariables in Global
```

## Usage
Just create your `.env` file near your `build.sbt`:
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

## A note about requirements
This plugin needs SBT 1.x+ version to work properly.

## Credits and inspiration
Big thanks to [Matt Fellows](https://github.com/mefellows) and his [sbt-dotenv](https://github.com/mefellows/sbt-dotenv) amazing sbt plugin. It was the main inspiration to create this plugin.

## Example
This repo is itself an example ;)