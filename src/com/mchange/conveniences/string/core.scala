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

  def pathJoin( pathSeparator : Char )( path0 : String, path1 : String ) : String =
    val firstEndsWith    = path0.last == pathSeparator
    val secondBeginsWith = path1.head == pathSeparator
    (firstEndsWith, secondBeginsWith ) match
      case (true, true)                  => path0 + path1.tail
      case (true, false) | (false, true) => path0 + path1
      case (false, false)                => (path0 + '/') + path1

  def pathJoin( path0 : String, path1 : String ) : String = pathJoin('/')(path0, path1)
