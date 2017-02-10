/*
 * Copyright 2016 Crown Copyright
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
package uk.gov.gchq.gaffer.hbasestore;

import org.junit.Ignore;
import uk.gov.gchq.gaffer.commonutil.StreamUtil;
import uk.gov.gchq.gaffer.integration.AbstractStoreITs;
import uk.gov.gchq.gaffer.store.StoreProperties;

@Ignore("This requires a standalone instance of hbase running in localhost")
public class StandaloneHBaseStoreITs extends AbstractStoreITs {
    private static final StoreProperties STORE_PROPERTIES = StoreProperties.loadStoreProperties(StreamUtil.openStream(StandaloneHBaseStoreITs.class, "standalone.store.properties"));

    public StandaloneHBaseStoreITs() {
        super(STORE_PROPERTIES);
    }
}
