/*
 * Copyright 2016-2020 Crown Copyright
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
 */

package uk.gov.gchq.gaffer.integration.graph;

import org.junit.jupiter.api.Test;

import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.data.elementdefinition.exception.SchemaException;
import uk.gov.gchq.gaffer.graph.Graph;
import uk.gov.gchq.gaffer.graph.GraphConfig;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GraphIT {

    @Test
    public void shouldCloseStreamsIfExceptionThrownWithStoreProperties() throws IOException {
        // Given
        final InputStream storePropertiesStream = createMockStream();
        final InputStream elementsSchemaStream = createMockStream();
        final InputStream typesSchemaStream = createMockStream();
        final InputStream aggregationSchemaStream = createMockStream();
        final InputStream validationSchemaStream = createMockStream();

        // When
        assertThrows(SchemaException.class, () -> new Graph.Builder()
                .storeProperties(storePropertiesStream)
                .addSchema(elementsSchemaStream)
                .addSchema(typesSchemaStream)
                .addSchema(aggregationSchemaStream)
                .addSchema(validationSchemaStream)
                .build());

        // Then
        verify(storePropertiesStream, atLeastOnce()).close();
        verify(elementsSchemaStream, atLeastOnce()).close();
        verify(typesSchemaStream, atLeastOnce()).close();
        verify(aggregationSchemaStream, atLeastOnce()).close();
        verify(validationSchemaStream, atLeastOnce()).close();
    }

    @Test
    public void shouldCloseStreamsIfExceptionThrownWithElementSchema() throws IOException {
        // Given
        final InputStream storePropertiesStream = StreamUtil.storeProps(getClass());
        final InputStream elementSchemaStream = createMockStream();
        final InputStream typesSchemaStream = createMockStream();
        final InputStream serialisationSchemaStream = createMockStream();
        final InputStream aggregationSchemaStream = createMockStream();

        // When
        assertThrows(SchemaException.class, () -> new Graph.Builder()
                .config(new GraphConfig.Builder()
                        .graphId("graph1")
                        .build())
                .storeProperties(storePropertiesStream)
                .addSchema(elementSchemaStream)
                .addSchema(typesSchemaStream)
                .addSchema(serialisationSchemaStream)
                .addSchema(aggregationSchemaStream)
                .build());

        // Then
        verify(elementSchemaStream, atLeastOnce()).close();
        verify(typesSchemaStream, atLeastOnce()).close();
        verify(serialisationSchemaStream, atLeastOnce()).close();
        verify(aggregationSchemaStream, atLeastOnce()).close();
    }

    @Test
    public void shouldCloseStreamsIfExceptionThrownWithTypesSchema() throws IOException {
        // Given
        final InputStream storePropertiesStream = StreamUtil.storeProps(getClass());
        final InputStream elementSchemaStream = StreamUtil.elementsSchema(getClass());
        final InputStream typesSchemaStream = createMockStream();
        final InputStream aggregationSchemaStream = createMockStream();
        final InputStream serialisationSchemaStream = createMockStream();

        // When
        assertThrows(SchemaException.class, () -> new Graph.Builder()
                .storeProperties(storePropertiesStream)
                .addSchema(elementSchemaStream)
                .addSchema(typesSchemaStream)
                .addSchema(aggregationSchemaStream)
                .addSchema(serialisationSchemaStream)
                .build());

        // Then
        verify(typesSchemaStream, atLeastOnce()).close();
        verify(aggregationSchemaStream, atLeastOnce()).close();
        verify(serialisationSchemaStream, atLeastOnce()).close();
    }

    @Test
    public void shouldCloseStreamsWhenSuccessful() {
        // Given
        final InputStream storePropertiesStream = StreamUtil.storeProps(getClass());
        final InputStream elementsSchemaStream = StreamUtil.elementsSchema(getClass());
        final InputStream typesSchemaStream = StreamUtil.typesSchema(getClass());

        // When
        new Graph.Builder()
                .config(new GraphConfig.Builder()
                        .graphId("graphId")
                        .build())
                .storeProperties(storePropertiesStream)
                .addSchema(elementsSchemaStream)
                .addSchema(typesSchemaStream)
                .build();

        // Then
        checkClosed(storePropertiesStream);
        checkClosed(elementsSchemaStream);
        checkClosed(typesSchemaStream);
    }

    private void checkClosed(final InputStream stream) {
        final Exception exception = assertThrows(IOException.class, stream::read);
        assertEquals("Stream closed", exception.getMessage());
    }

    private InputStream createMockStream() {
        final InputStream mock = mock(InputStream.class);
        try {
            given(mock.read()).willReturn(-1);
            given(mock.read(any(byte[].class))).willReturn(-1);
            given(mock.read(any(byte[].class), anyInt(), anyInt())).willReturn(-1);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }

        return mock;
    }
}
