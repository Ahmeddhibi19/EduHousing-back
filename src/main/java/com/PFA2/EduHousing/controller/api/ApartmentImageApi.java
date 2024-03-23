package com.PFA2.EduHousing.controller.api;

import com.PFA2.EduHousing.dto.ApartmentImagedto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "apartmentImages")
@RequestMapping("/api")
public interface ApartmentImageApi {
    @PostMapping(value = APP_ROOT+"/apartmentImages/create/{apartment_id}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Save apartment image", description = "Save apartment image with the specified apartment ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment image saved successfully "),
            @ApiResponse(responseCode = "400",description = "the object apartment image is not valid !!!")
    })
    public String save(@RequestParam("file") MultipartFile file, @PathVariable("apartment_id") Integer apartmentId)throws IOException;
    @GetMapping(value = APP_ROOT+"/apartmentImages/{apartment_image_id}",produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "search one apartment image", description = "search apartment image with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartment image found"),
            @ApiResponse(responseCode = "404",description = "apartment image not found !!!")
    })
    public byte[] findById(@PathVariable("apartment_image_id") Integer id);
    @GetMapping(value = APP_ROOT+"/apartmentImages/apartment/{apartment_id}",produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "search list of apartmentImages", description = "search apartmentImages with the specified  apartment iD ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartmentImages list found")

    })
    public List<byte[]> findAllByApartmentId(@PathVariable("apartment_id") Integer id);
    @GetMapping(value = APP_ROOT+"/apartmentImages/findAll",produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "search list of apartmentImages  ", description = "search all apartmentImages ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "apartmentImages list found")

    })
    public List<byte[]> findAll();
    @DeleteMapping(value = APP_ROOT+"/apartmentImages/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete apartment image by id... ",description = "needs an existing apartment image id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "apartment image deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);
}
