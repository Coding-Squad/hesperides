/*
 *
 * This file is part of the Hesperides distribution.
 * (https://github.com/voyages-sncf-technologies/hesperides)
 * Copyright (c) 2016 VSCT.
 *
 * Hesperides is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, version 3.
 *
 * Hesperides is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */
package org.hesperides.test.bdd.commons;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class HesperidesScenario {

    //TODO Quel est l'intérêt de cette classe ? Est-ce qu'on peut pas tout passer dans TestContext ?

    @Autowired
    protected TestContext testContext;
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected AuthorizationCredentialsConfig authorizationCredentialsConfig;

    public static Class getResponseType(String tryTo, Class defaultResponseType) {
        return StringUtils.isEmpty(tryTo) ? defaultResponseType : String.class;
    }

    public void assertOK() {
        assertEquals(HttpStatus.OK, testContext.getResponseStatusCode());
    }

    public void assertCreated() {
        assertEquals(HttpStatus.CREATED, testContext.getResponseStatusCode());
    }

    public void assertNotFound() {
        assertEquals(HttpStatus.NOT_FOUND, testContext.getResponseStatusCode());
    }

    public void assertConflict() {
        assertEquals(HttpStatus.CONFLICT, testContext.getResponseStatusCode());
    }

    protected void assertMethodNotAllowed() {
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, testContext.getResponseStatusCode());
    }

    public void assertBadRequest() {
        assertEquals(HttpStatus.BAD_REQUEST, testContext.getResponseStatusCode());
    }

    void assertUnauthorized() {
        assertEquals(HttpStatus.UNAUTHORIZED, testContext.getResponseStatusCode());
    }

    void assertForbidden() {
        assertEquals(HttpStatus.FORBIDDEN, testContext.getResponseStatusCode());
    }

    protected void assertNoContent() {
        assertEquals(HttpStatus.NO_CONTENT, testContext.getResponseStatusCode());
    }

    protected void assertInternalServerError() {
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, testContext.getResponseStatusCode());
    }
}
