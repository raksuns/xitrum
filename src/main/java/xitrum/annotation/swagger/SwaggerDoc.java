package xitrum.annotation.swagger;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SwaggerDoc {
  /** Brief description of the operation */
  String summary() default "";

  /** Long description of the operation */
  String notes() default "";

  /** Parameters */
  SwaggerParameter[] parameters() default {};

  /** Errors */
  SwaggerErrorResponse[] errorResponses() default {};
}