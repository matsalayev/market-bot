package bot.repositories.sql

import bot.domain.CorporateId
import bot.domain.ProjectId
import bot.domain.project.Project
import bot.support.skunk.Sql
import bot.support.skunk.codecs.nes
import bot.support.skunk.codecs.zonedDateTime
import eu.timepit.refined.types.string.NonEmptyString
import skunk._
import skunk.implicits._

private[repositories] object ProjectsSql extends Sql[ProjectId] {
  private[repositories] val codec: Codec[Project] =
    (id *: zonedDateTime *: PeopleSql.id *: CorporationsSql.id *: nes *: nes.opt)
      .to[Project]

  val insert: Command[Project] =
    sql"""INSERT INTO projects VALUES ($codec)""".command

  def getAll(corporateId: CorporateId): AppliedFragment =
    sql"""SELECT *, COUNT(*) OVER() FROM projects WHERE corporate_id = ${CorporationsSql.id}"""
      .apply(corporateId)

  val findById: Query[ProjectId, Project] =
    sql"""SELECT * FROM projects WHERE id = $id LIMIT 1""".query(codec)

  val findByName: Query[NonEmptyString, Project] =
    sql"""SELECT * FROM projects WHERE name = $nes LIMIT 1""".query(codec)

  val update: Command[Project] =
    sql"""UPDATE projects
      SET
        name = $nes,
        description = ${nes.opt}
      WHERE id = $id"""
      .command
      .contramap { (p: Project) =>
        p.name *: p.description *: p.id *: EmptyTuple
      }

  val delete: Command[ProjectId] =
    sql"""DELETE FROM projects WHERE id = $id""".command
}
