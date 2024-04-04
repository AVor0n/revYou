package hh.crossreview.entity.enums.converters;

import hh.crossreview.entity.enums.ReviewDuration;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.ws.rs.BadRequestException;

@Converter
public class ReviewDurationAttributeConverter implements AttributeConverter<ReviewDuration, Integer> {

  @Override
  public Integer convertToDatabaseColumn(ReviewDuration attribute) {
    if (attribute == null) {
      throw new BadRequestException("Field 'reviewDuration' is not valid");
    }
    return attribute.getHours();
  }

  @Override
  public ReviewDuration convertToEntityAttribute(Integer dbData) {
    return ReviewDuration.ofHours(dbData);
  }

}
