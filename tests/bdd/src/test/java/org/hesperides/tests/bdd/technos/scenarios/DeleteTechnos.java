package org.hesperides.tests.bdd.technos.scenarios;

import cucumber.api.java8.En;
import org.hesperides.tests.bdd.commons.HesperidesScenario;
import org.hesperides.tests.bdd.modules.ModuleBuilder;
import org.hesperides.tests.bdd.technos.TechnoBuilder;
import org.hesperides.tests.bdd.technos.TechnoClient;
import org.hesperides.tests.bdd.templatecontainers.builders.ModelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import static org.hesperides.tests.bdd.commons.HesperidesScenario.*;

public class DeleteTechnos extends HesperidesScenario implements En {

    @Autowired
    private TechnoClient technoClient;
    @Autowired
    private TechnoBuilder technoBuilder;
    @Autowired
    private ModuleBuilder moduleBuilder;
    @Autowired
    private ModelBuilder modelBuilder;

    public DeleteTechnos() {

        When("^I( try to)? delete this techno$", (String tryTo) -> {
            testContext.responseEntity = technoClient.delete(technoBuilder.build(), getResponseType(tryTo, ResponseEntity.class));
            moduleBuilder.removeTechno(technoBuilder.build());
            modelBuilder.removeProperties(technoBuilder.getProperties());
        });

        Then("^the techno is successfully deleted$", () -> {
            assertOK();
            testContext.responseEntity = technoClient.get(technoBuilder.build(), String.class);
            assertNotFound();
        });

        Then("^the techno deletion is rejected with a not found error$", () -> {
            assertNotFound();
        });

        Then("^this techno templates are also deleted$", () -> {
            //s'assurer que la techno à été bien supprimé dans le 1er step => Given
            assertOK();
            testContext.responseEntity = technoClient.getTemplates(technoBuilder.build(), String.class);
            assertNotFound();
            testContext.responseEntity = technoClient.getTemplate(technoBuilder.build().getName(), technoBuilder.build(), String.class);
            assertNotFound();
        });

    }
}
