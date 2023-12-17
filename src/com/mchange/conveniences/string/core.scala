package com.mchange.conveniences.string

extension ( s : String )
  def asOptionNotBlank( trim : Boolean ) : Option[String] =
    if s == null then
      None
    else
      val trimmed = s.trim()
      if trimmed.nonEmpty then
        Some( if trim then trimmed else s )
      else
        None

  def asOptionNotBlank : Option[String] = asOptionNotBlank( true )


