package com.mchange.conveniences.javanio

import java.nio.file.Path

private def assertHomeDirStr( hdContainingPath : Path ) : String =
  sys.props.get("user.home").getOrElse:
    throw new UnsupportedOperationException(s"Trying to resolve ~ home dirs from '${hdContainingPath}', but System property 'user.home' is not set.")

extension ( path : Path )
  def resolveTildeAsHome : Path =
    val fs = path.getFileSystem()
    val sep = fs.getSeparator()
    val pathStr = path.toString.trim
    val homePrefix = "~" + sep
    if pathStr.startsWith( homePrefix ) then
      val homeDirStr = assertHomeDirStr(path)
      val homeDir = Path.of( homeDirStr )
      homeDir.resolve( Path.of( pathStr.substring( homePrefix.length ) ) )
    else
      val homeSegment = sep + "~" + sep
      val i = pathStr.indexOf( homeSegment )
      if i >= 0 then
        val homeDirStr = assertHomeDirStr(path)
        val homeDir = Path.of( homeDirStr )
        homeDir.resolve( Path.of( pathStr.substring( i + homeSegment.length ) ) )
      else
        path

