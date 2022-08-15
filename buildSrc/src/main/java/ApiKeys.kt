import java.util.Properties
import java.io.FileInputStream

object ApiKeys {
    const val TRAKT_CLIENT_ID = "TRAKT_CLIENT_ID"
    const val TMDB_API_KEY = "TMDB_API_KEY"

    val getTraktClientID: String = getAccessKey(TRAKT_CLIENT_ID) ?: ""
    val getTmdbApiKey: String = getAccessKey(TMDB_API_KEY) ?: ""

    private fun getAccessKey(name: String): String? {
        val properties = Properties()
        val fis = FileInputStream("api_keys.properties")
        properties.load(fis)
        return properties.getProperty(name)
    }
}