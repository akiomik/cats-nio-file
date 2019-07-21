package cats.nio.file

import java.util.stream.{Stream => JStream}

import scala.collection.JavaConverters._ // TODO: use scala.jdk.CollectionConverters._
import scala.language.higherKinds

import cats.effect.{Resource, Sync}

package object implicits {
  implicit class JavaStreamOps[F[_]: Sync, A](stream: F[JStream[A]]) {
    def resource: Resource[F, JStream[A]] = Resource.fromAutoCloseable(stream)
  }
}
