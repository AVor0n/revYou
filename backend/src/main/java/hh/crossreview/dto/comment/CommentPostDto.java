package hh.crossreview.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(
    name = "CommentPostDto",
    requiredProperties = {"content"}
)
public class CommentPostDto {
  @NotBlank(message = "Content cannot be empty")
  private String content;

  public String getContent() {
    return content;
  }

  public CommentPostDto setContent(String content) {
    this.content = content;
    return this;
  }
}
