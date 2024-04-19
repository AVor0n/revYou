package hh.crossreview.external.gitlab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hh.crossreview.utils.ExternalUtils;
import jakarta.inject.Named;
import jakarta.inject.Singleton;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.InternalServerErrorException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Named
@Singleton
public class GitlabService {

  private final ObjectMapper objectMapper;

  @Value("${gitlab.url}")
  private String gitlabUrl;

  @Value("${gitlab.token}")
  private String rootToken;

  public GitlabService(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public void checkBranchLink(String branchLink) {
    try {
      getBranch(branchLink);
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new BadRequestException("Branch link is not accessible");
    }
  }



  public String retrieveCommitId(String branchLink) {
    try {
      return objectMapper
          .readTree(getBranch(branchLink))
          .get("commit")
          .get("id")
          .asText();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

  private String getBranch(String branchLink) {
    List<String> result = parseBranchLink(branchLink);
    if (result.isEmpty()) {
      throw new BadRequestException("Branch link isn't valid");
    }
    String repository = urlEncodeString(result.get(0));
    String branch = urlEncodeString(result.get(1));
    URI uri = URI
        .create(new StringJoiner("/")
        .add(gitlabUrl)
        .add("projects")
        .add(repository)
        .add("repository")
        .add("branches")
        .add(branch)
        .toString());

    String response;
    try {
      response = RestClient
          .create(ExternalUtils.getRestTemplateWithoutSSLCheck())
          .get()
          .uri(uri)
          .header("PRIVATE-TOKEN", rootToken)
          .retrieve()
          .body(String.class);
      return response;
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new InternalServerErrorException(e);
    }
  }

  private List<String> parseBranchLink(String branchLink) {
    String branchLinkRegex = "/([^/]+/[^/]+)/-/tree/([\\w\\-./]+)";
    Pattern pattern = Pattern.compile(branchLinkRegex);
    Matcher matcher = pattern.matcher(branchLink);
    List<String> result = new ArrayList<>();
    if (matcher.find()) {
      result.add(matcher.group(1));
      result.add(matcher.group(2));
    }
    return result;
  }

  private String urlEncodeString(String param) {
    return param.replace("/", "%2F");
  }

}
