// Copyright 2019 Akiomi Kamakura
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package cats.nio.file

import java.io.{BufferedReader, BufferedWriter, InputStream, OutputStream}
import java.nio.charset.Charset
import java.nio.file.{Files => JFiles, _}
import java.nio.file.attribute._
import java.nio.channels.SeekableByteChannel
import java.util.stream.{Stream => JStream}

import scala.collection.JavaConverters._ // TODO: use scala.jdk.CollectionConverters._
import scala.collection.mutable
import scala.compat.java8.FunctionConverters._
import scala.language.higherKinds

import cats.effect.{Sync, IO}

class Files[F[_]](implicit F: Sync[F]) {
  def copy(in: InputStream, target: Path, options: CopyOption*): F[Long] =
    F.delay(JFiles.copy(in, target, options: _*))

  def copy(source: Path, out: OutputStream): F[Long] =
    F.delay(JFiles.copy(source, out))

  def copy(source: Path, target: Path, options: CopyOption*): F[Path] =
    F.delay(JFiles.copy(source, target, options: _*))

  def createDirectories(dir: Path, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createDirectories(dir, attrs: _*))

  def createDirectory(dir: Path, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createDirectory(dir, attrs: _*))

  def createFile(path: Path, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createFile(path, attrs: _*))

  def createLink(link: Path, existing: Path): F[Path] =
    F.delay(JFiles.createLink(link, existing))

  def createSymbolicLink(link: Path, target: Path, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createSymbolicLink(link, target, attrs: _*))

  def createTempDirectory(dir: Path, prefix: String, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createTempDirectory(dir, prefix, attrs: _*))

  def createTempDirectory(prefix: String, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createTempDirectory(prefix, attrs: _*))

  def createTempFile(dir: Path, prefix: String, suffix: String, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createTempFile(dir, prefix, suffix, attrs: _*))

  def createTempFile(prefix: String, suffix: String, attrs: FileAttribute[_]*): F[Path] =
    F.delay(JFiles.createTempFile(prefix, suffix, attrs: _*))

  def delete(path: Path): F[Unit] = F.delay(JFiles.delete(path))

  def deleteIfExists(path: Path): F[Boolean] = F.delay(JFiles.deleteIfExists(path))

  def exists(path: Path): F[Boolean] = F.delay(JFiles.exists(path))

  def find(start: Path, maxDepth: Int, matcher: (Path, BasicFileAttributes) => Boolean, options: FileVisitOption*): F[JStream[Path]] =
    F.delay(JFiles.find(start, maxDepth, matcher.asJava, options: _*))

  def getAttribute(path: Path, attribute: String, options: LinkOption*): F[Object] =
    F.delay(JFiles.getAttribute(path, attribute, options: _*))

  def getFileAttributeView[A <: FileAttributeView](path: Path, `type`: Class[A], options: LinkOption*): F[A] =
    F.delay(JFiles.getFileAttributeView(path, `type`, options: _*))

  def getFileStore(path: Path): F[FileStore] = F.delay(JFiles.getFileStore(path))

  def getLastModifiedTime(path: Path, options: LinkOption*): F[FileTime] =
    F.delay(JFiles.getLastModifiedTime(path, options: _*))

  def getOwner(path: Path, options: LinkOption*): F[UserPrincipal] =
    F.delay(JFiles.getOwner(path, options: _*))

  def getPosixFilePermissions(path: Path, options: LinkOption*): F[mutable.Set[PosixFilePermission]] =
    F.delay(JFiles.getPosixFilePermissions(path, options: _*).asScala)

  def isDirectory(path: Path, options: LinkOption*): F[Boolean] =
    F.delay(JFiles.isDirectory(path, options: _*))

  def isExecutable(path: Path): F[Boolean] = F.delay(JFiles.isExecutable(path))

  def isHidden(path: Path): F[Boolean] = F.delay(JFiles.isHidden(path))

  def isReadable(path: Path): F[Boolean] = F.delay(JFiles.isReadable(path))

  def isRegularFile(path: Path, options: LinkOption*): F[Boolean] =
    F.delay(JFiles.isRegularFile(path, options: _*))

  def isSameFile(path: Path, path2: Path): F[Boolean] = F.delay(JFiles.isSameFile(path, path2))

  def isWritable(path: Path): F[Boolean] = F.delay(JFiles.isWritable(path))

  def lines(path: Path): F[JStream[String]] = F.delay(JFiles.lines(path))

  def lines(path: Path, cs: Charset): F[JStream[String]] = F.delay(JFiles.lines(path, cs))

  def list(dir: Path): F[JStream[Path]] = F.delay(JFiles.list(dir))

  def move(source: Path, target: Path, options: CopyOption*): F[Path] =
    F.delay(JFiles.move(source, target, options: _*))

  def newBufferedReader(path: Path): F[BufferedReader] =
    F.delay(JFiles.newBufferedReader(path))

  def newBufferedReader(path: Path, cs: Charset): F[BufferedReader] =
    F.delay(JFiles.newBufferedReader(path, cs))

  def newBufferedWriter(path: Path, cs: Charset, options: OpenOption*): F[BufferedWriter] =
    F.delay(JFiles.newBufferedWriter(path, cs, options: _*))

  def newBufferedWriter(path: Path, options: OpenOption*): F[BufferedWriter] =
    F.delay(JFiles.newBufferedWriter(path, options: _*))

  def newByteChannel(path: Path, options: OpenOption*): F[SeekableByteChannel] =
    F.delay(JFiles.newByteChannel(path, options: _*))

  def newByteChannel(path: Path, options: Set[_ <: OpenOption], attrs: FileAttribute[_]*): F[SeekableByteChannel] =
    F.delay(JFiles.newByteChannel(path, options.asJava, attrs: _*))

  def newDirectoryStream(dir: Path): F[DirectoryStream[Path]] =
    F.delay(JFiles.newDirectoryStream(dir))

  def newDirectoryStream(dir: Path, filter: DirectoryStream.Filter[_ >: Path]): F[DirectoryStream[Path]] =
    F.delay(JFiles.newDirectoryStream(dir, filter))

  def newDirectoryStream(dir: Path, glob: String): F[DirectoryStream[Path]] =
    F.delay(JFiles.newDirectoryStream(dir, glob))

  def newInputStream(path: Path, options: OpenOption*): F[InputStream] =
    F.delay(JFiles.newInputStream(path, options: _*))

  def newOutputStream(path: Path, options: OpenOption*): F[OutputStream] =
    F.delay(JFiles.newOutputStream(path, options: _*))

  def notExists(path: Path, options: LinkOption*): F[Boolean] =
    F.delay(JFiles.notExists(path, options: _*))

  def probeContentType(path: Path): F[String] = F.delay(JFiles.probeContentType(path))

  def readAllBytes(path: Path): F[Array[Byte]] = F.delay(JFiles.readAllBytes(path))

  def readAllLines(path: Path): F[mutable.Buffer[String]] = F.delay(JFiles.readAllLines(path).asScala)

  def readAttributes[A <: BasicFileAttributes](path: Path, `type`: Class[A], options: LinkOption*): F[A] =
    F.delay(JFiles.readAttributes(path, `type`, options: _*))

  def readAttributes(path: Path, attributes: String, options: LinkOption*): F[mutable.Map[String, Object]] =
    F.delay(JFiles.readAttributes(path, attributes, options: _*).asScala)

  def readSymbolicLink(link: Path): F[Path] = F.delay(JFiles.readSymbolicLink(link))

  def setAttribute(path: Path, attribute: String, value: Object, options: LinkOption*): F[Path] =
    F.delay(JFiles.setAttribute(path, attribute, value, options: _*))

  def setLastModifiedTime(path: Path, time: FileTime): F[Path] =
    F.delay(JFiles.setLastModifiedTime(path, time))

  def setOwner(path: Path, owner: UserPrincipal): F[Path] = F.delay(JFiles.setOwner(path, owner))

  def setPosixFilePermissions(path: Path, perms: Set[PosixFilePermission]): F[Path] =
    F.delay(JFiles.setPosixFilePermissions(path, perms.asJava))

  def size(path: Path): F[Long] = F.delay(JFiles.size(path))

  def walk(start: Path, options: FileVisitOption*): F[JStream[Path]] =
    F.delay(JFiles.walk(start, options: _*))

  def walk(start: Path, maxDepth: Int, options: FileVisitOption*): F[JStream[Path]] =
    F.delay(JFiles.walk(start, maxDepth, options: _*))

  def walkFileTree(start: Path, visitor: FileVisitor[_ >: Path]): F[Path] =
    F.delay(JFiles.walkFileTree(start, visitor))

  def walkFileTree(start: Path, options: Set[FileVisitOption], maxDepth: Int, visitor: FileVisitor[_ >: Path]): F[Path] =
    F.delay(JFiles.walkFileTree(start, options.asJava, maxDepth, visitor))

  def write(path: Path, bytes: Array[Byte], options: OpenOption*): F[Path] =
    F.delay(JFiles.write(path, bytes, options: _*))

  def write(path: Path, lines: Iterable[_ <: CharSequence], cs: Charset, options: OpenOption*): F[Path] =
    F.delay(JFiles.write(path, lines.asJava, cs, options: _*))

  def write(path: Path, lines: Iterable[_ <: CharSequence], options: OpenOption*): F[Path] =
    F.delay(JFiles.write(path, lines.asJava, options: _*))
}

object Files {
  implicit val ioFiles: Files[IO] = new Files[IO]

  def apply[F[_]](implicit instance: Files[F]) = instance
}
