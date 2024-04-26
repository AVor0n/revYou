package hh.crossreview.external.gitlab;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hh.crossreview.dao.SolutionDao;
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
  private final SolutionDao solutionDao;

  @Value("${gitlab.url}")
  private String gitlabUrl;
  @Value("${gitlab.api-url}")
  private String gitlabApiUrl;
  @Value("${gitlab.token}")
  private String rootToken;

  public GitlabService(ObjectMapper objectMapper, SolutionDao solutionDao) {
    this.objectMapper = objectMapper;
    this.solutionDao = solutionDao;
  }

  public String validateBranchLink(String branchLink) {
    try {
      branchLink = trimBranchLink(branchLink);
      requireBranchLinkUnique(branchLink);
      getBranchRequest(branchLink);
      return branchLink;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new BadRequestException("Branch link is not accessible");
    }
  }

  private void requireBranchLinkUnique(String branchLink) {
    if (solutionDao.findByBranchLink(branchLink).isPresent()) {
      throw new BadRequestException("Branch link already send by another user or to another homework");
    }
  }

  private String trimBranchLink(String branchLink) {
    String branchLinkRegex = String.format("(%s/[^/]+/[^/]+/-/tree/[\\w\\-./]+).*", gitlabUrl);
    Pattern pattern = Pattern.compile(branchLinkRegex);
    Matcher matcher = pattern.matcher(branchLink);
    if (matcher.find()) {
      return matcher.group(1);
    }
    throw new BadRequestException("Branch link isn't valid");
  }

  private String getBranchRequest(String branchLink) {
    List<String> result = parseBranchLink(branchLink);
    if (result.isEmpty()) {
      throw new BadRequestException("Branch link isn't valid");
    }
    String repository = urlEncodePath(result.get(0));
    String branch = urlEncodePath(result.get(1));
    URI uri = URI
        .create(new StringJoiner("/")
        .add(gitlabApiUrl)
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

  private String urlEncodePath(String param) {
    return param.replace("/", "%2F");
  }

  public String retrieveCommitId(String branchLink) {
    try {
      return objectMapper
          .readTree(getBranchRequest(branchLink))
          .get("commit")
          .get("id")
          .asText();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

}
