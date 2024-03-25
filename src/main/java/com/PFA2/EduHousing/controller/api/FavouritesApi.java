package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.Favouritesdto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "favourites")
@RequestMapping("/api")
public interface FavouritesApi {
    @PostMapping(value = APP_ROOT+"/favourites/student/create/{student_id}/{apartment_id}",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save favourites", description = "Save favourites with the specified student ID and apartment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "favourites saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object favourites is not valid !!!")
    })
    public Favouritesdto addToFavourites(@RequestBody Favouritesdto favouritesdto, @PathVariable("student_id") Integer studentId,@PathVariable("apartment_id") Integer apartmentId);
    @DeleteMapping(value = APP_ROOT+"/favourites/student/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete favourites by id... ",description = "needs an existing favourites id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "favourites deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void delete(@PathVariable("id") Integer favouritesId);
    @GetMapping(value = APP_ROOT+"/favourites/student/{student_id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "search list of favourites", description = "search favourites with the specified  homeowner iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "favourites list found")

    })
    public List<Favouritesdto> findByStudentId(@PathVariable("student_id") Integer studentId);
}
