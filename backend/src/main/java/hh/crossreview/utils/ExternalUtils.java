package hh.crossreview.utils;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class ExternalUtils {

  // Для получения данных с сайтов с самоподписанным сертификатом
  public static RestTemplate getRestTemplateWithoutSSLCheck() throws NoSuchAlgorithmException, KeyManagementException {
    SSLContext sslContext = SSLContext.getInstance("TLS");
    TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
      public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {
        // Empty method just because
      }
      public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {
        // Empty method just because
      }
      public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
      }
    }};
    sslContext.init(null, trustManagers, null);
    HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    return new RestTemplateBuilder()
        .requestFactory(SimpleClientHttpRequestFactory.class)
        .build();
  }

}
