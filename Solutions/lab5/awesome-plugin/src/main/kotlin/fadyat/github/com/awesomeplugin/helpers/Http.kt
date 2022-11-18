package fadyat.github.com.awesomeplugin.helpers

import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse


enum class HttpMethod {
    GET, POST, PUT, DELETE,
}

fun doRequest(
    method: HttpMethod,
    url: String,
    headers: Map<String, String> = mapOf(),
    body: String = "",
): HttpResponse<String> {
    val client = HttpClient.newHttpClient()
    val requestBuilder = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .method(method.name, HttpRequest.BodyPublishers.ofString(body))

    headers.forEach { (key, value) ->
        requestBuilder.header(key, value)
    }

    val request = requestBuilder.build()
    return client.send(request, HttpResponse.BodyHandlers.ofString())
}
