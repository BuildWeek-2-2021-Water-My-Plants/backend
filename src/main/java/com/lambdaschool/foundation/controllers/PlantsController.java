package com.lambdaschool.foundation.controllers;

import com.lambdaschool.foundation.exceptions.ResourceNotFoundException;
import com.lambdaschool.foundation.models.Plant;
import com.lambdaschool.foundation.models.User;
import com.lambdaschool.foundation.services.PlantService;
import com.lambdaschool.foundation.services.UserService;
import org.h2.table.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/users")
@RestController
public class PlantsController {

    @Autowired
    private PlantService plantService;

    @Autowired
    private UserService userService;


    // Save a plant
    // Update a plant - patch
    // Delete a plant

    // https://watermyplantsbackend2021.herokuapp.com/users/plants
    // *Get all plants by User
    @GetMapping(value = "/plants", produces = {"application/json"})
    public ResponseEntity<?> listPlantsByUser()
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentuser = userService.findByName(username);

        return new ResponseEntity(currentuser.getPlants(), HttpStatus.OK);
    }

    // https://watermyplantsbackend2021.herokuapp.com/users/plants
    // *Post a plant from logged in user
    @PostMapping(value = "/plants", produces = {"application/json"})
    public ResponseEntity<?> addPlant(
            @RequestBody
                    Plant newplant)
    {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentuser = userService.findByName(username);

        newplant.setPlantid(0);
        newplant = plantService.save(currentuser.getUserid(), newplant);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPlantURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/userinfo")
                .buildAndExpand(newplant.getPlantid())
                .toUri();
        responseHeaders.setLocation(newPlantURI);

        return new ResponseEntity<>(newplant, responseHeaders, HttpStatus.CREATED);
    }

    // https://watermyplantsbackend2021.herokuapp.com/users/plants/{id}
    // *Update a plants info
    @PatchMapping(value = "/plants/{id}", produces = {"application/json"})
    public ResponseEntity<?> updatePlant(@RequestBody Plant updatePlant, @PathVariable long id) throws Exception
    {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userService.findByName(username);


        if (currentUser.getUserid() == updatePlant.getUser().getUserid())
        {
            plantService.update(updatePlant, updatePlant.getPlantid());
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            throw new ResourceNotFoundException("User is not authorized to change this plant");

    }


    }

    // https://watermyplantsbackend2021.herokuapp.com/users/plants{id}
    // *Delete a plant
    @DeleteMapping(value = "/plants/{plantid}", produces = {"application/json"})
    public ResponseEntity<?> deletePlant(@PathVariable long plantid)
    {
        plantService.delete(plantid);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
