package com.capitalone.dashboard.editors;

import com.capitalone.dashboard.model.BlackDuckType;
import codesecurity.api.editors.CaseInsensitiveCodeSecurityTypeEditor;

public class CaseInsensitiveBlackDuckTypeEditor extends CaseInsensitiveCodeSecurityTypeEditor<BlackDuckType> {
    protected BlackDuckType getText(String text) {
        return BlackDuckType.fromString(text);
    }
}
