package repository

import javax.inject.Inject

import models.Vehicle
import play.modules.reactivemongo.ReactiveMongoApi
import reactivemongo.api.Cursor
import reactivemongo.bson.{BSONDocument => Doc}
import reactivemongo.play.json.collection.JSONCollection

import scala.concurrent.Future

class VehicleMongoRepository @Inject()(reactiveMongoApi: ReactiveMongoApi) extends VehicleRepository {

  import play.modules.reactivemongo.json._

  import scala.concurrent.ExecutionContext.Implicits.global

  private implicit lazy val collection = reactiveMongoApi.database.map(_.collection[JSONCollection]("vehicles"))

  private def execute[T](op: JSONCollection => Future[T]): Future[T] = implicitly[Future[JSONCollection]] flatMap op

  override def find(registration: String) =
    execute(_.find(Doc("reg" -> registration)).cursor[Vehicle]().collect[List](maxDocs = 1, Cursor.FailOnError[List[Vehicle]]()))
}