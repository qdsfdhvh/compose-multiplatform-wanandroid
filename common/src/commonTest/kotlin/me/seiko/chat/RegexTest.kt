import me.seiko.chat.HttpRegex
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RegexTest {
  @Test
  fun http_regex() {
    assertTrue { HttpRegex.matches("https://juejin.im/user/5a3ba9375188252bca050ade") }
    assertTrue { HttpRegex.matches("https://www.betaqr.com/") }
    assertTrue { HttpRegex.matches("https://fir.im/") }
    assertTrue { HttpRegex.matches("https://developer.android.google.cn/") }
    assertTrue { HttpRegex.matches("https://developer.android.google.cn/reference/kotlin/android/") }
    assertTrue { HttpRegex.matches("https://developer.android.google.cn/reference/kotlin/android/widget/TextView?hl=en") }
    assertFalse { HttpRegex.matches("www.aaa###.com") }
    assertFalse { HttpRegex.matches("127.0.0.1") }
  }
}
