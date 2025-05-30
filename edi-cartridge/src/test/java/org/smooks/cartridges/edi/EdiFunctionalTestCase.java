/*-
 * ========================LICENSE_START=================================
 * smooks-edi-cartridge
 * %%
 * Copyright (C) 2020 Smooks
 * %%
 * Licensed under the terms of the Apache License Version 2.0, or
 * the GNU Lesser General Public License version 3.0 or later.
 *
 * SPDX-License-Identifier: Apache-2.0 OR LGPL-3.0-or-later
 *
 * ======================================================================
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * ======================================================================
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 * =========================LICENSE_END==================================
 */
package org.smooks.cartridges.edi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smooks.Smooks;
import org.smooks.support.StreamUtils;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.smooks.support.SmooksUtil.filterAndSerialize;
import static org.smooks.testkit.Assertions.compareCharStreams;

public class EdiFunctionalTestCase {

    private Smooks smooks;

    @BeforeEach
    public void beforeEach() {
        smooks = new Smooks();
    }

    @AfterEach
    public void afterEach() {
        smooks.close();
    }

    @Test
    public void testSmooksConfigGivenParser() throws Exception {
        smooks.addResourceConfigs("/smooks-parser-config.xml");
        String result = filterAndSerialize(smooks.createExecutionContext(), getClass().getResourceAsStream("/data/edi-input.txt"), smooks);

        assertTrue(compareCharStreams(StreamUtils.readStreamAsString(getClass().getResourceAsStream("/data/expected.xml"), "UTF-8"), result));
    }

    @Test
    public void testSmooksConfigGivenParserWithDefaultSchemaUri() throws Exception {
        smooks.addResourceConfigs("/smooks-default-schemaUri-parser-config.xml");
        String result = filterAndSerialize(smooks.createExecutionContext(), getClass().getResourceAsStream("/data/edi-input.txt"), smooks);

        assertTrue(compareCharStreams(StreamUtils.readStreamAsString(getClass().getResourceAsStream("/data/expected-default.xml"), "UTF-8"), result));
    }

    @Test
    public void testSmooksConfigGivenUnparser() throws Exception {
        smooks.addResourceConfigs("/smooks-unparser-config.xml");
        String result = filterAndSerialize(smooks.createExecutionContext(), getClass().getResourceAsStream("/data/expected.xml"), smooks);

        assertTrue(compareCharStreams(StreamUtils.readStreamAsString(getClass().getResourceAsStream("/data/edi-input.txt"), "UTF-8"), result));
    }

    @Test
    public void testSmooksConfigGivenUnparserWithDefaultSchemaUri() throws Exception {
        smooks.addResourceConfigs("/smooks-default-schemaUri-unparser-config.xml");
        String result = filterAndSerialize(smooks.createExecutionContext(), getClass().getResourceAsStream("/data/expected-default.xml"), smooks);

        assertTrue(compareCharStreams(StreamUtils.readStreamAsString(getClass().getResourceAsStream("/data/edi-input.txt"), "UTF-8"), result));
    }
}
