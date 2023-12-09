

case class User(id: Long, Name: String, number: Long)

object SlickTables {

  import slick.jdbc.MySQLProfile.api._

  /*
  tag = > type parameter that is used to define the table schema
  user = > schema name
  user_table => table name that we will use in our code
   */

  lazy val userTable = TableQuery[UserTable]

  class UserTable(tag: Tag) extends Table[User](tag, Some("test"), "user_table") {
    /*
     Column definition
     def col_name = column[col_type]("col_name", col_properties (E.g pk, default=?)
     */

    def id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("user_name")

    def number = column[Long]("user_phone_number")

    /*
      * => Method used to mab the case class "User" to database entity
      <> => mapping operator
      User.tuple => build a user out of a tuple
      User.unapply => build a tuple out of a user
      user.tuple and user,unapply are used to construct and deconstruct an object in mapping
     */
    override def * = (id, name, number) <> (User.tupled, User.unapply)


  }
}
