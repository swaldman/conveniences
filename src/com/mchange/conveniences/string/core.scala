package com.mchange.conveniences.string

extension ( s : String )
  def toOptionNotBlank( trim : Boolean ) : Option[String] =
    if s == null then
      None
    else
      val trimmed = s.trim()
      if trimmed.nonEmpty then
        Some( if trim then trimmed else s )
      else
        None

  def toOptionNotBlank : Option[String] = toOptionNotBlank( true )


