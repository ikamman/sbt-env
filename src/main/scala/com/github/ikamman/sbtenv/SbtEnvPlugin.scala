package com.gitgub.ikamman.sbtenvp

import sbt._
import sbt.Keys._
import sbt.complete.DefaultParsers._
import scala.io.Source

object SbtEnvPlugin extends AutoPlugin {

  object autoImport {
    lazy val envFileName =
      settingKey[String]("The file name to define variables.")
    lazy val envVariables =
      settingKey[Map[String, String]]("Variables loaded from *.env file")
  }

  import autoImport._

  override def trigger = allRequirements

  override lazy val globalSettings =
    Seq(
      envFileName := ".env",
      envVariables := parseAndLoad((baseDirectory / envFileName.value).value).getOrElse(Map.empty)
    )

  def parseAndLoad(fileName: String): Option[Map[String, String]] =
    parseFile(new File(fileName))

  def parseFile(file: File): Option[Map[String, String]] = {
    if (!file.exists) {
      None
    } else {
      val source = Source.fromFile(file)
      val result = parseSource(source)
      source.close
      Some(result)
    }
  }

  val LINE_REGEX =
    """(?xms)
       (?:^|\A)           # start of line
       \s*                # leading whitespace
       (?:export\s+)?     # export (optional)
       (                  # start variable name (captured)
         [a-zA-Z_]          # single alphabetic or underscore character
         [a-zA-Z0-9_.-]*    # zero or more alphnumeric, underscore, period or hyphen
       )                  # end variable name (captured)
       (?:\s*=\s*?)       # assignment with whitespace
       (                  # start variable value (captured)
         '(?:\\'|[^'])*'    # single quoted variable
         |                  # or
         "(?:\\"|[^"])*"    # double quoted variable
         |                  # or
         [^\#\r\n]*         # unquoted variable
       )                  # end variable value (captured)
       \s*                # trailing whitespace
       (?:                # start trailing comment (optional)
         \#                 # begin comment
         (?:(?!$).)*        # any character up to end-of-line
       )?                 # end trailing comment (optional)
       (?:$|\z)           # end of line
    """.r

  def parseSource(source: Source): Map[String, String] = parse(source.mkString)

  def parse(source: String): Map[String, String] =
    LINE_REGEX
      .findAllMatchIn(source)
      .map(keyValue =>
        (
          keyValue.group(1),
          unescapeCharacters(removeQuotes(keyValue.group(2)))
        )
      )
      .toMap

  def removeQuotes(value: String): String = {
    value.trim match {
      case quoted if quoted.startsWith("'") && quoted.endsWith("'") =>
        quoted.substring(1, quoted.length - 1)
      case quoted if quoted.startsWith("\"") && quoted.endsWith("\"") =>
        quoted.substring(1, quoted.length - 1)
      case unquoted => unquoted
    }
  }

  def unescapeCharacters(value: String): String = {
    value.replaceAll("""\\([^$])""", "$1")
  }
}
