package com.mchange.conveniences.throwable

import scala.util.control.NonFatal

def extractFullStackTrace(t:Throwable) : String =
  val sw = new java.io.StringWriter()
  t.printStackTrace(new java.io.PrintWriter(sw))
  sw.toString()

extension (t : Throwable)
  def fullStackTrace : String = extractFullStackTrace(t)

object NonFatals:
  val PrintStackTrace : PartialFunction[Throwable,Unit] = {
    case NonFatal(t : Throwable) => t.printStackTrace()
  }
