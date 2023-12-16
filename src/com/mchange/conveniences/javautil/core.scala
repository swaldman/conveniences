package com.mchange.conveniences.javautil

import java.io.{BufferedInputStream, File, FileInputStream}
import java.nio.file.Path
import java.util.Properties
import scala.util.Using

def loadProperties( file : File, defaults : Option[Properties] ) : Properties =
  Using.resource( new BufferedInputStream( new FileInputStream( file ) ) ): is =>
    val p = defaults.fold( new Properties() )( d => new Properties(d) )
    p.load(is)
    p

def loadProperties( file : File ) : Properties                                = loadProperties(file, None)
def loadProperties( path : Path, defaults : Option[Properties] ) : Properties = loadProperties( path.toFile(), defaults )
def loadProperties( path : Path ) : Properties                                = loadProperties( path, None )
