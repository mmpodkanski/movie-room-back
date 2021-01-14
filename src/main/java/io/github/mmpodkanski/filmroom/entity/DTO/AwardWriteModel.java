package io.github.mmpodkanski.filmroom.entity.DTO;

import io.github.mmpodkanski.filmroom.entity.Award;
import lombok.Data;

@Data
public class AwardWriteModel {
    private String name;

    public Award toAward() {
        return new Award(name);
    }
}
