
import slick.jdbc.MySQLProfile.api._

import java.util.concurrent.{ExecutorService, Executors}
import scala.concurrent.{ExecutionContext, Future}
import scala.util.{Failure, Success, Try}

object Main {

  val testUser: User = User(1, "Username", 1112346)
  val executor: ExecutorService = Executors.newFixedThreadPool(4)
  implicit val ec : ExecutionContext = ExecutionContext.fromExecutorService(executor)

  def main(args: Array[String]): Unit = {
//    insertUser(testUser)
    updateUser(1)
    getAllUsers()
    getUserByName("name")
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

  def getUserByName(username : String): Unit ={
    val res : Future[Seq[User]] = Connection.db.run(SlickTables.userTable.filter(_.name.like(username)).result) // => select * from users where name like :username
    res.onComplete{
      case Success(users) => println(s"Query Successfully Done: $users")
      case Failure(ex) => println(s"Query Failed Because of : $ex")
    }
    Thread.sleep(10000)
  }

  def updateUser(id :Long): Unit ={
    val queryDesc = SlickTables.userTable.filter(_.id === id).update(testUser.copy(number = 45120))
    val futureId : Future[Int] = Connection.db.run(queryDesc)
    futureId.onComplete{
      case Success(userNewId) => println(s"Query Successfully Done, new id is: $userNewId")
      case Failure(ex) => println(s"Query Failed Because of : $ex")
    }
    Thread.sleep(10000);
  }
}
