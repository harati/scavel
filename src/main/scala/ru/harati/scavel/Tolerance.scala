package ru.harati.scavel

/**
 * Creation date: 17.08.2016
 * Copyright (c) harati
 */
object Tolerance {

  implicit class DTDTol(val i: Double) extends AnyVal {
    def ~=(o: Double, e: Double = 10E-6) = (i - o).abs < e
  }

}