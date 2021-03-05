package com.lambdaschool.foundation.services;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Plant;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.repository.PlantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "plantService")
public class PlantServiceImpl implements PlantService {

    @Autowired
    private UserService userService;


    @Autowired
    private PlantRepository plantRepository;

    @Autowired
    private HelperFunctions helperFunctions;


    @Transactional
    @Override
    public Plant save(long userid, Plant newplant)
    {
        User currentUser = userService.findUserById(userid);
        Plant savePlant = new Plant();

        savePlant.setNickname(newplant.getNickname());
        savePlant.setSpecies(newplant.getSpecies());
        savePlant.setH20(newplant.getH20());
        savePlant.setPlantimg(newplant.getPlantimg());
        savePlant.setUser(currentUser);

       return plantRepository.save(savePlant);
    }

    @Transactional
    @Override
    public Plant update(Plant updatePlant, long plantid) {

        Plant currentPlant = plantRepository.findById(plantid)
                .orElseThrow(() -> new ResourceNotFoundException("Plant does not exist"));

        if (plantRepository.findById(plantid)
        .isPresent())
        {
            if (helperFunctions.isAuthorizedToMakeChange(updatePlant.getUser().getUsername()))
            {
                if (updatePlant.getNickname() != null)
                {
                    currentPlant.setNickname(updatePlant.getNickname());
                }
                if (updatePlant.getSpecies() != null)
                {
                    currentPlant.setSpecies(updatePlant.getSpecies());
                }
                if (updatePlant.getH20() != null)
                {
                    currentPlant.setH20(updatePlant.getH20());
                }
                if (updatePlant.getPlantimg() != null)
                {
                    currentPlant.setPlantimg(updatePlant.getPlantimg());
                }

                if (updatePlant.getUser() != null)
                {
                    currentPlant.setUser(updatePlant.getUser());
                }
            }

        }

        return plantRepository.save(currentPlant);


    }

    @Override
    public void delete(long plantid) {
        plantRepository.findById(plantid)
                .orElseThrow(() -> new ResourceNotFoundException("Plant id " + plantid + " is not found"));
        plantRepository.deleteById(plantid);
    }
}
