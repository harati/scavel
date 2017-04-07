package ru.harati.scavel

/**
 * Created by loki on 06.04.2017.
 */
trait SelfPointed {
  implicit def self: this.type = this
}
