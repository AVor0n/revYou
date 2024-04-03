package hh.crossreview.entity.enums;

public enum ReviewDuration {
  ONE_DAY(24),
  TWO_DAYS(48);

  private final Integer hours;

  ReviewDuration(Integer hours) {
    this.hours = hours;
  }

  public Integer getHours() {
    return hours;
  }

  public static ReviewDuration ofHours(Integer hours) {
    return switch (hours) {
      case 24 -> ONE_DAY;
      case 48 -> TWO_DAYS;
      default -> null;
    };
  }

}
