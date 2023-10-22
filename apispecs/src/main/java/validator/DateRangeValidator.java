package validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DateRangeValidator implements ConstraintValidator<DateRange, Object> {

    private String from;
    private String to;

    public void initialize(DateRange constraintAnnotation) {
        this.from = constraintAnnotation.from();
        this.to = constraintAnnotation.to();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fromValue = new BeanWrapperImpl(value).getPropertyValue(from);
        Object toValue = new BeanWrapperImpl(value).getPropertyValue(to);
        if (fromValue instanceof String && toValue instanceof String) {
            String strFrom = extractNumberic((String) fromValue);
            String strTo = extractNumberic((String) toValue);
            return strTo.compareTo(strFrom) >= 0;
        }
        if (fromValue instanceof LocalDate dateFrom && toValue instanceof LocalDate dateTo) {
            return !dateFrom.isAfter(dateTo);
        }
        if (fromValue instanceof LocalDateTime dateFrom && toValue instanceof LocalDateTime dateTo) {
            return !dateFrom.isAfter(dateTo);
        }
        return  true;
    }
    private String extractNumberic(String value) {
        return value.replaceAll( "[^\\d]", "");
    }
}