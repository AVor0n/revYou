package hh.crossreview.external.gitlab.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import hh.crossreview.dao.SolutionDao;
import hh.crossreview.dto.gitlab.DiffDto;
import hh.crossreview.dto.gitlab.DiffsWrapperDto;
import hh.crossreview.external.gitlab.entity.RepositoryInfo;
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

  public RepositoryInfo validateSolutionBranchLink(String branchLink) {
    try {
      RepositoryInfo repositoryInfo = parseBranchLink(branchLink);
      Integer projectId = retrieveProjectId(repositoryInfo.getProjectPath());
      repositoryInfo.setProjectId(projectId);
//      requireBranchUnique(repositoryInfo);
      getBranchRequest(repositoryInfo);
      return repositoryInfo;
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new BadRequestException("Branch link is not accessible");
    }
  }

  public String validateHomeworkBranchLink(String branchLink) {
    try {
      RepositoryInfo repositoryInfo = parseBranchLink(branchLink);
      Integer projectId = retrieveProjectId(repositoryInfo.getProjectPath());
      return retrieveCommitId(projectId, repositoryInfo.getBranch());
    } catch (HttpClientErrorException | HttpServerErrorException e) {
      throw new BadRequestException("Branch link is not accessible");
    }
  }

  public String retrieveCommitId(Integer projectId, String branch) {
    RepositoryInfo repositoryInfo = new RepositoryInfo(projectId, branch);
    try {
      return objectMapper
          .readTree(getBranchRequest(repositoryInfo))
          .get("commit")
          .get("id")
          .asText();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public Integer retrieveProjectId(String projectPath) {
    try {
      return objectMapper
          .readTree(getProjectRequest(projectPath))
          .get("id")
          .asInt();
    } catch (JsonProcessingException e) {
      throw new InternalServerErrorException(e);
    }
  }

  public DiffsWrapperDto retrieveDiffs(Integer projectId, String from, String to) {
    String jsonTree = getDiffsRequest(projectId, from, to);
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

  private String getBranchRequest(RepositoryInfo repositoryInfo) {
    Integer projectId = repositoryInfo.getProjectId();
    String branch = urlEncodeSlashes(repositoryInfo.getBranch());
    URI uri = URI.create(String.format(
        "%s/projects/%d/repository/branches/%s",
        gitlabApiUrl,
        projectId,
        branch
    ));
    return getRequest(uri);
  }

  private String getDiffsRequest(Integer projectId, String from, String to) {
    URI uri = URI.create(String.format(
        "%s/projects/%d/repository/compare?from=%s&to=%s",
        gitlabApiUrl,
        projectId,
        from,
        to
    ));
    return getRequest(uri);
  }


  private String getRawFileRequest(Integer projectId, String filePath, String ref) {
    URI uri = URI.create(String.format(
        "%s/projects/%d/repository/files/%s/raw?ref=%s",
        gitlabApiUrl,
        projectId,
        filePath,
        ref
    ));
    return getRequest(uri);
  }

  private String getProjectRequest(String projectPath) {
    projectPath = urlEncodeSlashes(projectPath);
    URI uri = URI.create(String.format(
        "%s/projects/%s",
        gitlabApiUrl,
        projectPath
    ));
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

  private RepositoryInfo parseBranchLink(String branchLink) {
    String branchLinkRegex = String.format("%s/([^/]+/[^/]+)/-/tree/([\\w\\-./]+).*", gitlabUrl);
    Pattern pattern = Pattern.compile(branchLinkRegex);
    Matcher matcher = pattern.matcher(branchLink);
    if (!matcher.find()) {
      throw new BadRequestException("Branch link isn't valid");
    }
    return new RepositoryInfo(
        matcher.group(1),
        matcher.group(2)
    );
  }

  private void requireBranchUnique(RepositoryInfo repositoryInfo) {
    Integer projectId = repositoryInfo.getProjectId();
    String branch = repositoryInfo.getBranch();
    if (solutionDao.findByProjectIdAndBranch(projectId, branch).isPresent()) {
      throw new BadRequestException("Branch link already send by another user or to another homework");
    }
  }

  private String urlEncodeSlashes(String param) {
    return param.replace("/", "%2F");
  }

  private String urlEncodeFullPath(String param) {
    param = urlEncodeSlashes(param);
    return param.replace(".", "%2E");
  }

}
