package io.github.mmpodkanski.filmroom.models.request;

import io.github.mmpodkanski.filmroom.models.Award;
import lombok.Data;

@Data
public class AwardWriteModel {
    private String name;

    public Award toAward() {
        return new Award(name);
    }
}
