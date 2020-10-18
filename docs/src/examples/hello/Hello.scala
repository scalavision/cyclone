package examples.hello

import com.raquo.laminar.api.L._
import cyclone.Cyclone
import cyclone.Effects._

object Hello {

  case class SayHello(to: String)

  val cycle: Cyclone[SayHello, String, Nothing] =
    Cyclone[SayHello, String, Nothing](
      initState = "World",
      inHandler = {
        case SayHello(name) if name.trim.isEmpty =>
          updateTo("World")

        case SayHello(name) =>
          updateTo(name.toUpperCase())
      }
    )

  val view: Div =
    div(
      cycle.bind(),
      "Hello ",
      child.text <-- cycle.state,
      br(),
      input(
        placeholder := "Enter your name",
        inContext { input => onKeyUp.mapTo(SayHello(input.ref.value)) --> cycle }
      )
    )

}
