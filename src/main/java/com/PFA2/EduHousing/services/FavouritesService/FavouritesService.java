package com.PFA2.EduHousing.services.FavouritesService;

import com.PFA2.EduHousing.dto.Favouritesdto;

import java.util.List;

public interface FavouritesService {
    public Favouritesdto addToFavourites(Favouritesdto favouritesdto,Integer studentId,Integer apartmentId);
    public void delete(Integer favouritesId);
    public List<Favouritesdto> findByStudentId(Integer studentId);
}
