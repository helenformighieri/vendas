package br.com.helenformighieri.anotacao;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColunaTabela {

    String dbName();

    String setJavaName();
}