package bot.repositories

import bot.generators._
import bot.repositories.sql._
import bot.support.skunk.syntax.all.skunkSyntaxCommandOps
import cats.effect.IO
import cats.effect.Resource
import cats.implicits.toFoldableOps
import skunk.Session

object data extends Generators with PeopleGenerators {
  object people {
    val person1: dto.Person = personGen
    val person2: dto.Person = personGen
    val values: List[dto.Person] = List(person1, person2)
  }

  def setup(implicit session: Resource[IO, Session[IO]]): IO[Unit] =
    setupPersons

  private def setupPersons(implicit session: Resource[IO, Session[IO]]): IO[Unit] =
    people.values.traverse_ { data =>
      PeopleSql.insert.execute(data)
    }
}
