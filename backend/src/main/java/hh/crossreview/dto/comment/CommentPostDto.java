package hh.crossreview.dto.comment;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CommentPostDto")
public class CommentPostDto {


  private String content;

  public String getContent() {
    return content;
  }

  public CommentPostDto setContent(String content) {
    this.content = content;
    return this;
  }
}
