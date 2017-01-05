package repository

import com.google.inject.ImplementedBy
import models.Vehicle

import scala.concurrent.Future

@ImplementedBy(classOf[VehicleMongoRepository])
trait VehicleRepository {

  def find(registration: String): Future[Vector[Vehicle]]

}
