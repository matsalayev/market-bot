import cats.Monad
import cats.effect.std.Random
import cats.implicits.catsSyntaxApplicativeId
import cats.implicits.toFlatMapOps
import cats.implicits.toFunctorOps
import eu.timepit.refined.W
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.MatchesRegex

package object bot {
  type Login = String Refined MatchesRegex[W.`"(^[A-Za-z]{3,16})$"`.T]
  type Phone = String Refined MatchesRegex[W.`"""[+][\\d]{12}+"""`.T]
  type Digits = String Refined MatchesRegex[W.`"""[\\d]+"""`.T]
//  lazy val AppDomain: String =
//    Mode.current match {
//      case Mode.Development => "http://dev.tm"
//      case Mode.Test => "http://dev.tm"
//      case Mode.Production => "https://tm.it-forelead.uz"
//      case Mode.Staging => "http://tm"
//    }

  def randomStr[F[_]: Random: Monad](n: Int, cond: Char => Boolean = _ => true): F[String] = {
    def makeString(size: Int, string: String): F[String] =
      if (size == 0) string.pure[F]
      else {
        val charF = Random[F].nextAlphaNumeric
        Monad[F]
          .ifM(charF.map(cond))(
            charF.flatMap(char => makeString(size - 1, string + char)),
            makeString(size, string),
          )
      }
    makeString(n, "")
  }
}
