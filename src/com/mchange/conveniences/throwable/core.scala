package com.mchange.conveniences.throwable

def extractFullStackTrace(t:Throwable) : String =
  val sw = new java.io.StringWriter()
  t.printStackTrace(new java.io.PrintWriter(sw))
  sw.toString()

extension (t : Throwable)
  def fullStackTrace : String = extractFullStackTrace(t)

