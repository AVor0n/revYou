package hh.crossreview.external.gitlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.gitlab.DiffDto;
import hh.crossreview.dto.gitlab.DiffsWrapperDto;
import hh.crossreview.external.gitlab.entity.ParsedGitlabLink;
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

  public ParsedGitlabLink validateBranchLink(String branchLink) {
    try {
      branchLink = trimBranchLink(branchLink);
      requireBranchLinkUnique(branchLink);
      ParsedGitlabLink parsedGitlabLink = parseBranchLink(branchLink);
      getBranchRequest(parsedGitlabLink);
      return parsedGitlabLink;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new BadRequestException("Branch link is not accessible");
    }
  }

  public String retrieveCommitId(String repository, String branch) {
    ParsedGitlabLink parsedGitlabLink = new ParsedGitlabLink(repository, branch, "");
    try {
      return objectMapper
          .readTree(getBranchRequest(parsedGitlabLink))
          .get("commit")
          .get("id")
          .asText();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public DiffsWrapperDto retrieveDiffs(Integer repositoryId, String from, String to) {
    String jsonTree = getDiffsRequest(repositoryId, from, to);
    DiffsWrapperDto diffsWrapperDto = new DiffsWrapperDto();
    List<DiffDto> diffsDto = new ArrayList<>();
    try {
      ArrayNode diffs = (ArrayNode) objectMapper
          .readTree(jsonTree)
          .get("diffs");
      for(JsonNode diff : diffs) {
        DiffDto diffDto = new DiffDto();
        diffDto
            .setOldPath(diff.get("old_path").asText())
            .setNewPath(diff.get("new_path").asText())
            .setNewFile(diff.get("new_file").asBoolean())
            .setRenamedFile(diff.get("renamed_file").asBoolean())
            .setDeletedFile(diff.get("deleted_file").asBoolean())
            .setGeneratedFile(diff.get("generated_file").asBoolean());
        diffsDto.add(diffDto);
      }
      diffsWrapperDto.setDiffs(diffsDto);
      return diffsWrapperDto;
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public String retrieveRawFile(Integer projectId, String filePath, String ref) {
    filePath = urlEncodeFullPath(filePath);
    return getRawFileRequest(projectId, filePath, ref);
  }

  private String getBranchRequest(ParsedGitlabLink parsedGitlabLink) {
    String repository = urlEncodeSlashes(parsedGitlabLink.repository());
    String branch = urlEncodeSlashes(parsedGitlabLink.branch());
    URI uri = URI
        .create(new StringJoiner("/")
            .add(gitlabApiUrl)
            .add("projects")
            .add(repository)
            .add("repository")
            .add("branches")
            .add(branch)
            .toString());
    return getRequest(uri);
  }

  private String getDiffsRequest(Integer repositoryId, String from, String to) {
    URI uri = URI
        .create(new StringJoiner("/", "", "?")
            .add(gitlabApiUrl)
            .add("projects")
            .add(repositoryId.toString())
            .add("repository")
            .add("compare") +
            String.format("from=%s&to=%s", from, to));
    return getRequest(uri);
  }


  private String getRawFileRequest(Integer projectId, String filePath, String ref) {
    URI uri = URI
        .create(new StringJoiner("/", "", "?")
            .add(gitlabApiUrl)
            .add("projects")
            .add(projectId.toString())
            .add("repository")
            .add("files")
            .add(filePath)
            .add("raw") +
            String.format("ref=%s", ref));
    return getRequest(uri);
  }

  private String getRequest(URI uri) {
    try {
      return RestClient
          .create(ExternalUtils.getRestTemplateWithoutSSLCheck())
          .get()
          .uri(uri)
          .header("PRIVATE-TOKEN", rootToken)
          .retrieve()
          .body(String.class);
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      throw new InternalServerErrorException(e);
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

  private void requireBranchLinkUnique(String branchLink) {
    if (solutionDao.findByBranchLink(branchLink).isPresent()) {
      throw new BadRequestException("Branch link already send by another user or to another homework");
    }
  }

  private ParsedGitlabLink parseBranchLink(String branchLink) {
    String branchLinkRegex = "/([^/]+/[^/]+)/-/tree/([\\w\\-./]+)";
    Pattern pattern = Pattern.compile(branchLinkRegex);
    Matcher matcher = pattern.matcher(branchLink);
    if (!matcher.find()) {
      throw new BadRequestException("Branch link isn't valid");
    }
    return new ParsedGitlabLink(
        matcher.group(1),
        matcher.group(2),
        branchLink
    );
  }

  private String urlEncodeSlashes(String param) {
    return param.replace("/", "%2F");
  }

  private String urlEncodeFullPath(String param) {
    param = urlEncodeSlashes(param);
    return param.replace(".", "%2E");
  }

}
