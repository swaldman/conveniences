package com.mchange.conveniences.boolean

val UnitOption : Option[Unit] = Some( () )

extension ( b : Boolean )
  def toOption : Option[Unit] = if b then UnitOption else None
  def tf[A]( ifTrue : =>A)( ifFalse : =>A ) = if b then ifTrue else ifFalse

