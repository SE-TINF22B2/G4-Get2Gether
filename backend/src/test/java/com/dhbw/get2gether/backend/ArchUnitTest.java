package com.dhbw.get2gether.backend;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchUnitTest {

    private static final JavaClasses NON_TEST_CLASSES = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.dhbw.get2gether.backend");

    @Test
    void model_classes_should_not_depend_on_other_classes() {
        ArchRule rule = noClasses()
                .that()
                .resideInAnyPackage("..model..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage("..application..", "..adapter..", "..helper..");
        rule.check(NON_TEST_CLASSES);
    }

    /**
     * Methods in Service classes that are named 'findSomething' should return an Optional.
     */
    @Test
    void find_methods_should_return_optional() {
        ArchRule rule = methods()
                .that()
                .haveNameStartingWith("find")
                .and()
                .arePublic()
                .and()
                .areDeclaredInClassesThat()
                .resideInAnyPackage("..application..")
                .and()
                .areDeclaredInClassesThat()
                .haveSimpleNameEndingWith("Service")
                .should()
                .haveRawReturnType(Optional.class)
                .because("Methods with return type Optional should have method name starting with 'find'.");
        rule.check(NON_TEST_CLASSES);
    }

    /**
     * Methods in Service classes that are named 'getSomething' should not return an Optional.
     */
    @Test
    void get_methods_should_not_return_optional() {
        ArchRule rule = methods()
                .that()
                .haveNameStartingWith("get")
                .and()
                .arePublic()
                .and()
                .areDeclaredInClassesThat()
                .resideInAnyPackage("..application..")
                .and()
                .areDeclaredInClassesThat()
                .haveSimpleNameEndingWith("Service")
                .should()
                .notHaveRawReturnType(Optional.class)
                .because("Methods with return type Optional should have method name starting with 'find'.");
        rule.check(NON_TEST_CLASSES);
    }
}
