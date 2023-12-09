
import slick.jdbc.MySQLProfile.api._

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object Main {

  val testUser: User = User(1, "Username", 1112346)
  val executor: ExecutorService = Executors.newFixedThreadPool(4)
  implicit val ec : ExecutionContext = ExecutionContext.fromExecutorService(executor)

  def main(args: Array[String]): Unit = {
    insertUser(testUser)
    getAllUsers()
  }

  def insertUser(user: User): Unit ={
    val insertQuery = SlickTables.userTable += user
    val futureId : Future[Int] = Connection.db.run(insertQuery)
    futureId.onComplete{
        case Success(userNewId) => println(s"Query Successfully Done, new id is: $userNewId")
        case Failure(ex) => println(s"Query Failed Because of : $ex")
    }
    Thread.sleep(10000);
  }

  def getAllUsers(): Unit ={
    val res : Future[Seq[User]] = Connection.db.run(SlickTables.userTable.result) // => select * from users
    res.onComplete{
      case Success(users) => println(s"Query Successfully Done: $users")
      case Failure(ex) => println(s"Query Failed Because of : $ex")
    }
    Thread.sleep(10000)
  }


}
