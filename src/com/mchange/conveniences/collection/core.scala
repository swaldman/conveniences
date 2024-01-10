package com.mchange.conveniences.collection

import scala.collection.*

object NotUnique:
  case object Empty extends NotUnique
  case class Multiple( n : Int ) extends NotUnique
sealed trait NotUnique

extension[T,U <: Seq[T] | Set[T]] ( c : U )
  def uniqueOr( notUnique : (U, NotUnique) => T ) : T =
    c.size match
      case 0 => notUnique( c, NotUnique.Empty )
      case 1 => c.head
      case n => notUnique( c, NotUnique.Multiple(n) )

extension[A,CC[_],C] ( io : IterableOnceOps[Option[A],CC,C] )
  def actuals : CC[A] = io.collect { case Some( a ) => a }


