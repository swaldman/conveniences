package com.mchange.conveniences.www

import java.net.{URLDecoder,URLEncoder}

private val CharsetUTF8 = scala.io.Codec.UTF8.charSet

class FormDecodeException( msg : String, cause : Throwable = null ) extends Exception(msg,cause)

private def decodeBinding( eb : String ) : Tuple2[String,String] =
  val equalsIndex = eb.indexOf("=")
  if equalsIndex >= 0 then
    Tuple2( URLDecoder.decode(eb.substring(0,equalsIndex),CharsetUTF8), URLDecoder.decode(eb.substring(equalsIndex+1),CharsetUTF8) )
  else
    throw new FormDecodeException(s"'${eb}' has no '=' to separate key and value.")

def wwwFormEncodeUTF8( tups : Tuple2[String,String]* ) : String =
  val encodedBindings = tups.map( (k,v) => URLEncoder.encode(k,CharsetUTF8) + "=" + URLEncoder.encode(v,CharsetUTF8) )
  encodedBindings.mkString("&")

def wwwFormDecodeUTF8( s : String ) : Seq[Tuple2[String,String]] =
  s.split("&").map( decodeBinding ).toSeq

def wwwFormFindAllValues( key : String, decoded : Seq[Tuple2[String,String]] ) : Seq[String] = decoded.filter( _(0) == key ).map( _(1) )
def wwwFormFindFirstValue( key : String, decoded : Seq[Tuple2[String,String]] ) : Option[String] = decoded.find( _(0) == key ).map( _(1) )
