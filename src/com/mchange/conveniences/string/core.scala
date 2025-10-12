package com.mchange.conveniences.string

// Requires features not always supported by scalajs
// private val TrimHardRegex = """(?:^\p{Z}+)|(?:\p{Z}+$)""".r

private val TrimHardRegex =
  val spaceCharClass = """[\s\u00A0\u2001\u2002\u2003\u2004\u2005\u2006\u2007\u2008\u2009\u200a\u200b\u202F\u205F\u3000\uFEFF]"""
  raw"""(?:^${spaceCharClass}+)|(?:${spaceCharClass}+$$)""".r

extension ( s : String )
  def trimUnicode : String = TrimHardRegex.replaceAllIn(s,"")

  def nullOrEmpty : Boolean = s == null || s.isEmpty

  def nullOrBlank : Boolean = s == null || s.trim.isEmpty

  def nullOrBlankUnicode : Boolean = s == null || s.trimUnicode.isEmpty

  private inline def toOptionNotBlank( inline unicode : Boolean)( trim : Boolean ) : Option[String] =
    if s == null then
      None
    else
      val trimmed = inline if unicode then s.trimUnicode else s.trim
      if trimmed.nonEmpty then
        Some( if trim then trimmed else s )
      else
        None

  def toOptionNotBlank( trim : Boolean ) : Option[String] = toOptionNotBlank( false )( trim )

  def toOptionNotBlank : Option[String] = toOptionNotBlank( true )

  def toOptionNotBlankUnicode( trim : Boolean ) : Option[String] = toOptionNotBlank( true )( trim )

  def toOptionNotBlankUnicode : Option[String] = toOptionNotBlankUnicode( true )

def pathJoin( pathSeparator : Char )( path0 : String, path1 : String ) : String =
  val firstEndsWith    = path0.last == pathSeparator
  val secondBeginsWith = path1.head == pathSeparator
  (firstEndsWith, secondBeginsWith ) match
    case (true, true)                  => path0 + path1.tail
    case (true, false) | (false, true) => path0 + path1
    case (false, false)                => (path0 + pathSeparator) + path1

def pathJoin( path0 : String, path1 : String ) : String = pathJoin('/')(path0, path1)

private def commaListXXX(xxx : String)( seq : Seq[String] ) : Option[String] =
  seq.length match
    case 0 => None
    case 1 => Some( seq.head )
    case 2 => Some( seq.head + s" $xxx " + seq.last )
    case n =>
      val anded = seq.init :+ s"$xxx ${seq.last}"
      Some( anded.mkString(", ") )

private def parseCommaListXXX(xxx : String)( listText : String ) : Seq[String] =
  val uncommaed : Array[String] = listText.split("""\s*\,\s*""").flatMap( _.split(raw"""\s*\b${xxx}\b\s*""") ).filter( _.trimUnicode.nonEmpty )
  println( uncommaed.mkString("|") )
  if uncommaed.length == 1 then
    Seq(uncommaed(0))
  else
    (uncommaed.init :+ uncommaed.last.stripPrefix(xxx).trim).toSeq

def commaListAnd( seq : Seq[String] ) : Option[String] = commaListXXX("and")(seq)
def commaListOr( seq : Seq[String] )  : Option[String] = commaListXXX("or")(seq)

def parseCommaListAnd( listText : String ) = parseCommaListXXX("and")(listText)
def parseCommaListOr( listText : String ) = parseCommaListXXX("or")(listText)

private val Limit_Bytes = 1024
private val Limit_KiB   = 1024 * Limit_Bytes
private val Limit_MiB   = 1024 * Limit_KiB
private val Limit_GiB   = 1024 * Limit_MiB
private val Limit_TiB   = 1024 * Limit_GiB

def humanReadableByteLength( len : Long, decimals : Int = 2 ) : String =
  require( len >= 0, s"Byte lengths must be positive, $len is invalid.")
  if len < Limit_Bytes then s"${len} bytes"
  else if len < Limit_KiB then String.format( s"%.${decimals}f KiB", len.toDouble / Limit_Bytes )
  else if len < Limit_MiB then String.format( s"%.${decimals}f MiB", len.toDouble / Limit_KiB )
  else if len < Limit_GiB then String.format( s"%.${decimals}f GiB", len.toDouble / Limit_MiB )
  else String.format( s"%.${decimals}f TiB", len.toDouble / Limit_GiB )


