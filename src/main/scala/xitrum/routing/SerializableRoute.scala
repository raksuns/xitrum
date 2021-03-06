package xitrum.routing

import java.io.Serializable
import org.jboss.netty.handler.codec.http.HttpMethod
import xitrum.Action

class SerializableRoute(
  // In
  val httpMethod: String, val compiledPattern: Seq[RouteToken],

  // Out
  val actionClass: String, var cacheSecs: Int
) extends Serializable
{
  /** @return A new route with the prefix */
  def addPrefix(prefix: String): SerializableRoute = {
    val prefixTokens = RouteCompiler.compile(prefix)
    val firstToken   = compiledPattern.head

    // Replace "" with prefix, but for DotRouteToken, ".:foo" is OK
    // (its first NonDotRouteToken is empty)
    val newCompiledPattern =
      if (firstToken.isInstanceOf[NonDotRouteToken]) {
        if (firstToken.asInstanceOf[NonDotRouteToken].value.isEmpty)
          prefixTokens
        else
          prefixTokens ++ compiledPattern
      } else {
        prefixTokens ++ compiledPattern
      }

    new SerializableRoute(httpMethod, newCompiledPattern, actionClass, cacheSecs)
  }

  def toRoute: Route = {
    new Route(
      new HttpMethod(httpMethod), compiledPattern,
      Class.forName(actionClass).asInstanceOf[Class[Action]], cacheSecs
    )
  }
}
