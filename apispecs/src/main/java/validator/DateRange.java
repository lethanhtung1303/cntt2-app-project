package validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateRangeValidator.class)
@Target({ElementType.TYPE} )
@Retention(RetentionPolicy.RUNTIME)
public @interface DateRange {

        String message() default "Correlation check error for date start end inconsistency";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};

        String from();

        String to();

        @Target({ElementType.TYPE})
        @Retention(RetentionPolicy.RUNTIME)
        @interface List{

                DateRange[] value();
        }
}
