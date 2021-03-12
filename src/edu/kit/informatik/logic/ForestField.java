package edu.kit.informatik.logic;

import java.util.ArrayList;
import java.util.List;

public class ForestField implements Field {
    private List<FireEngine> fireEngines;
    private FireState fireState;

    public ForestField(FireState initialState) {
        this.fireEngines = new ArrayList<>();
        this.fireState = initialState;
    }
}
