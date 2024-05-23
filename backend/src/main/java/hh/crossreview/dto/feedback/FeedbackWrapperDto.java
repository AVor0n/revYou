package hh.crossreview.dto.feedback;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "FeedbackWrapper", requiredProperties = {"data"})
public class FeedbackWrapperDto {
    private List<FeedbackDto> data;

    public FeedbackWrapperDto(List<FeedbackDto> data) {
        this.data = data;
    }

    public List<FeedbackDto> getData() {
        return data;
    }
}
