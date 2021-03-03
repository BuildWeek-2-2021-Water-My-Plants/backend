package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.models.Plant;

public interface PlantService {
    Plant save(long userid, Plant newplant);

    Plant update(Plant updatePlant, long plantid);

    void delete(long plantid);
}
