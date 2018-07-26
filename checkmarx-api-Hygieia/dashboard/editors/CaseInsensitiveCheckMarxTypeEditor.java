package com.capitalone.dashboard.editors;

import codesecurity.api.editors.CaseInsensitiveCodeSecurityTypeEditor;
import com.capitalone.dashboard.model.CheckMarxType;

public class CaseInsensitiveCheckMarxTypeEditor extends CaseInsensitiveCodeSecurityTypeEditor<CheckMarxType> {
    protected CheckMarxType getText(String text) {
        return CheckMarxType.fromString(text);
    }
}
