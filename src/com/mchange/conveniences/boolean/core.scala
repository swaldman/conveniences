package com.mchange.conveniences.boolean

val UnitOption : Option[Unit] = Some( () )

extension ( b : Boolean )
  def toOption : Option[Unit] = if b then UnitOption else None
