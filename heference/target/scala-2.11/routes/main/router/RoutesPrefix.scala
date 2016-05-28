
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/Ikarious/dev/scala/project/playframework/scalaWebPro/conf/routes
// @DATE:Sat May 14 14:55:55 KST 2016


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
