import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.util.List;

/**
 * @author Stanislav Rakitov in 2022
 */
public class Main {
  private static final String REMOTE_SERVICE_URI =
      "https://raw.githubusercontent.com/netology-code/jd-homeworks/master/http/task1/cats";
  private static final ObjectMapper mapper = new ObjectMapper();

  public static void main(String[] args) throws IOException {

    CloseableHttpClient httpClient =
        HttpClientBuilder.create()
            .setDefaultRequestConfig(
                RequestConfig.custom()
                    .setConnectTimeout(5000) // максимальное время ожидание подключения к серверу
                    .setSocketTimeout(30000) // максимальное время ожидания получения данных
                    .setRedirectsEnabled(false) // возможность следовать редиректу в ответе
                    .build())
            .build();

    HttpGet request = new HttpGet(REMOTE_SERVICE_URI);

    CloseableHttpResponse response = httpClient.execute(request);
    List<Post> posts =
        mapper.readValue(response.getEntity().getContent(), new TypeReference<>() {});
    //    posts.forEach(System.out::println);
    posts.stream()
        .filter(post -> post.getUpvotes() != null && Integer.parseInt(post.getUpvotes()) > 0)
        .forEach(System.out::println);
  }
}
