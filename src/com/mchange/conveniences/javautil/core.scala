package com.mchange.conveniences.javautil

import java.io.{BufferedInputStream, File, FileInputStream}
import java.nio.file.Path
import java.util.Properties
import scala.util.Using
import scala.jdk.CollectionConverters.*

def loadProperties( file : File, defaults : Option[Properties] ) : Properties =
  Using.resource( new BufferedInputStream( new FileInputStream( file ) ) ): is =>
    val p = defaults.fold( new Properties() )( d => new Properties(d) )
    p.load(is)
    p

def loadProperties( file : File ) : Properties                                = loadProperties(file, None)
def loadProperties( path : Path, defaults : Option[Properties] ) : Properties = loadProperties( path.toFile(), defaults )
def loadProperties( path : Path ) : Properties                                = loadProperties( path, None )

extension ( p : Properties )
  def toMap : Map[String,String] = p.asScala.toMap

extension ( m : Map[String,String] )
  def toProperties : Properties =
    val p = new Properties()
    m.foreach( (k,v) => p.setProperty(k,v) )
    p
