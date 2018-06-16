/*
 * Copyright 2017 mk
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

package mk.gdx.firebase.html.database;

import mk.gdx.firebase.database.queries.GdxFireappQuery;
import mk.gdx.firebase.html.firebase.ScriptRunner;

/**
 * Provides flow for html firebase call.
 */
public abstract class GwtDatabaseQuery extends GdxFireappQuery<Database, Void> {

    protected String databaseReference;

    public GwtDatabaseQuery(Database databaseDistribution) {
        super(databaseDistribution);
    }

    @Override
    protected void prepare() {
        databaseReference = databaseDistribution.databaseReference();
    }

    @Override
    protected Void run() {
        ScriptRunner.firebaseScript(new ScriptRunner.ScriptDBAction(databaseReference) {
            @Override
            public void run() {
                runJS();
            }
        });
        return null;
    }

    @Override
    protected void applyFilters() {

    }

    @Override
    protected void terminate() {
        databaseDistribution.terminateOperation();
        databaseReference = null;
    }

    /**
     * Execute database operation after firebase.js was loaded.
     */
    protected abstract void runJS();
}
