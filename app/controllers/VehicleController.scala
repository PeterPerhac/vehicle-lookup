package controllers

import javax.inject.Inject

import play.api.libs.json.Json
import play.api.mvc._
import repository.VehicleRepository

import scala.concurrent.Future

class VehicleController @Inject()(val repository: VehicleRepository) extends Controller {

  import scala.concurrent.ExecutionContext.Implicits.global

  def list(reg: String) = Action.async { _ =>
    for {
      vehicles <- repository.find(reg)
      vehicle <- Future.successful(vehicles.headOption)
    } yield vehicle match {
      case Some(v) => Ok(Json.toJson(vehicle))
      case None => NotFound
    }
  }

}
