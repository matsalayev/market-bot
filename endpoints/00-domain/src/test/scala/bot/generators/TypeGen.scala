package bot.generators

import org.scalacheck.Gen

import bot.Language
import bot.domain._
import bot.domain.enums.Gender
import bot.domain.enums.Role
import bot.test.generators.Generators

trait TypeGen { this: Generators =>
  val personIdGen: Gen[PersonId] = idGen(PersonId.apply)
  val assetIdGen: Gen[AssetId] = idGen(AssetId.apply)
  val roleGen: Gen[Role] = Gen.oneOf(Role.values)
  val genderGen: Gen[Gender] = Gen.oneOf(Gender.values)
  val languageGen: Gen[Language] = Gen.oneOf(Language.values)
}
