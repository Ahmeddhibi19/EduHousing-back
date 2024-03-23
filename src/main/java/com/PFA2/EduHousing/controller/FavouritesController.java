package com.PFA2.EduHousing.controller;

import com.PFA2.EduHousing.controller.api.FavouritesApi;
import com.PFA2.EduHousing.dto.Favouritesdto;
import com.PFA2.EduHousing.services.FavouritesService.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FavouritesController implements FavouritesApi {
    private final FavouritesService favouritesService;
    @Autowired
    public FavouritesController(FavouritesService favouritesService){
        this.favouritesService=favouritesService;
    }
    @Override
    public Favouritesdto addToFavourites(Favouritesdto favouritesdto, Integer studentId, Integer apartmentId) {
        return favouritesService.addToFavourites(favouritesdto,studentId,apartmentId);
    }

    @Override
    public void delete(Integer favouritesId) {
        favouritesService.delete(favouritesId);
    }

    @Override
    public List<Favouritesdto> findByStudentId(Integer studentId) {
        return favouritesService.findByStudentId(studentId);
    }
}
