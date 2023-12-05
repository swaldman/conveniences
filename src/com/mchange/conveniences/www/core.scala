package com.mchange.conveniences.www

import java.net.{URLDecoder,URLEncoder}

private val CharsetUTF8 = scala.io.Codec.UTF8.charSet

class FormDecodeException( msg : String, cause : Throwable = null ) extends Exception(msg,cause)

private def decodeBinding( eb : String ) : Tuple2[String,String] =
  val arr = eb.split("=")
  if arr.length == 2 then
    Tuple2( URLDecoder.decode(arr(0),CharsetUTF8), URLDecoder.decode(arr(1),CharsetUTF8) )
  else
    throw new FormDecodeException(s"'${eb}' has no '=' to separate key and value.")

def wwwFormEncodeUTF8( tups : Tuple2[String,String]* ) : String =
  val encodedBindings = tups.map( (k,v) => URLEncoder.encode(k,CharsetUTF8) + "=" + URLEncoder.encode(v,CharsetUTF8) )
  encodedBindings.mkString("&")

def wwwFormDecodeUTF8( s : String ) : Seq[Tuple2[String,String]] =
  s.split("&").map( decodeBinding ).toSeq
