/*
 *
 *  * This file is part of the Hesperides distribution.
 *  * (https://github.com/voyages-sncf-technologies/hesperides)
 *  * Copyright (c) 2016 VSCT.
 *  *
 *  * Hesperides is free software: you can redistribute it and/or modify
 *  * it under the terms of the GNU General Public License as
 *  * published by the Free Software Foundation, version 3.
 *  *
 *  * Hesperides is distributed in the hope that it will be useful, but
 *  * WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  * General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 */

package com.vsct.dt.hesperides.indexation;

/**
 * Created by william_montaz on 06/06/14.
 */
public final class ESServiceException extends RuntimeException {

    public ESServiceException(final String message) {
        super(message);
    }

    public ESServiceException(final String message, final String url, final String request, final String esHost, final int esPort, final String esIndex) {
        super(String.format(message + "\n" +
                "\t\t esHost  :%1$s\n" +
                "\t\t esPort  :%2$s\n" +
                "\t\t index   :%3$s\n" +
                "\t\t url     :%4$s\n" +
                "\t\t request :%5$s\n", esHost, esPort, esIndex, url, request));
    }

    public ESServiceException(final String message, final String url, final String request, final String esHost, final int esPort, final String esIndex, final Exception cause) {
        super(String.format(message + "\n" +
                "\t\t esHost  :%1$s\n" +
                "\t\t esPort  :%2$s\n" +
                "\t\t index   :%3$s\n" +
                "\t\t url     :%4$s\n" +
                "\t\t request :%5$s\n", esHost, esPort, esIndex, url, request), cause);
    }

}
