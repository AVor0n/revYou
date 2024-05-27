package hh.crossreview.dto.review.info;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;

@Schema(name = "ReviewDuration", requiredProperties = {"hours", "minutes"})
@SuppressWarnings({"unused"})
public class ReviewDurationDto {

  private Long hours;
  private Long minutes;

  public ReviewDurationDto(Duration duration) {
    long hoursDuration = duration.toHours();
    long minutesDuration = duration.minusHours(hoursDuration).toMinutes();
    this.hours = hoursDuration;
    this.minutes = minutesDuration;
  }

  public ReviewDurationDto setHours(Long hours) {
    this.hours = hours;
    return this;
  }

  public ReviewDurationDto setMinutes(Long minutes) {
    this.minutes = minutes;
    return this;
  }

  public Long getHours() {
    return hours;
  }

  public Long getMinutes() {
    return minutes;
  }

}
