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
  def uniqueOrNone : Option[T] =
    c.size match
      case 0 => None
      case 1 => Some(c.head)
      case n => None
  def zeroOrOne : Either[Int,Option[T]] =
    c.size match
      case 0 => Right(None)
      case 1 => Right(Some(c.head))
      case n => Left(n)

// annoying to repeat this, but a bit less annoying than
// using immutable.ArraySeq to write it in terms of Seq.uniqueOr
extension[T]( a : Array[T] )
  def uniqueOr( notUnique : (Array[T], NotUnique) => T ) : T =
    a.size match
      case 0 => notUnique( a, NotUnique.Empty )
      case 1 => a.head
      case n => notUnique( a, NotUnique.Multiple(n) )
  def uniqueOrNone : Option[T] =
    a.size match
      case 0 => None
      case 1 => Some(a.head)
      case n => None
  def zeroOrOne : Either[Int,Option[T]] =
    a.size match
      case 0 => Right(None)
      case 1 => Right(Some(a.head))
      case n => Left(n)

extension[A,CC[_],C] ( io : IterableOnceOps[Option[A],CC,C] )
  def actuals : CC[A] = io.collect { case Some( a ) => a } // a bit embarrassingly, just calling standard method flatten does the same work


