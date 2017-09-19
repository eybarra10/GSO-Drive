package services

import javax.inject.Inject
import com.github.mauricio.async.db.pool.{ConnectionPool, PoolConfiguration}
import com.github.mauricio.async.db.mysql.MySQLConnection
import com.github.mauricio.async.db.mysql.pool.MySQLConnectionFactory
import com.github.mauricio.async.db.mysql.util.URLParser
import play.api.{Configuration, Logger}

class Mysql @Inject() (configuration: Configuration)  {

  val connectionPool: ConnectionPool[MySQLConnection] = {
    val mysqlConfig = configuration.getString("db.default.url").map(url => URLParser.parse(url))
    if (mysqlConfig.isEmpty) {
      Logger.error("Missing configuration for mysql database!")
    }

    val connectionFactory = new MySQLConnectionFactory(mysqlConfig.get)
    new ConnectionPool(connectionFactory, PoolConfiguration.Default)
  }
}
