package com.PFA2.EduHousing.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "profile image")
@RequestMapping("/api")
public interface ProfileImageApi {
    @PostMapping(value = APP_ROOT + "/profileImage/create/{user_id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Save profile image", description = "Save profile image with the specified user ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image saved successfully "),
            @ApiResponse(responseCode = "400", description = "The object profile image is not valid !!!")
    })
    public String save(@RequestParam("file") MultipartFile file, @PathVariable("user_id") Integer userId) throws IOException ;
    @GetMapping(value = APP_ROOT+"/profileImage/admin/{profile_image_id}",produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "search one profile image", description = "search profile image with the specified  ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "profile image found"),
            @ApiResponse(responseCode = "404",description = "profile image not found !!!")
    })
    public byte[] findById(@PathVariable("profile_image_id") Integer id);
    @GetMapping(value = APP_ROOT+"/profileImage/userId/{user_id}",produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "search one profile image", description = "search profile image with the specified user ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "profile image found"),
            @ApiResponse(responseCode = "404",description = "profile image not found !!!")
    })
    public byte[] findByUserId(@PathVariable("user_id") Integer userId);
    @DeleteMapping(value = APP_ROOT+"/profileImage/admin/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete profile image by id... ",description = "needs an existing profile image id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "profile image deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteById(@PathVariable("id") Integer id);

    @DeleteMapping(value = APP_ROOT+"/profileImage/delete_by_user_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete profile image by id... ",description = "needs an existing profile image id.."/*,response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "profile image deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public void deleteByUserId(@PathVariable("id") Integer id);
    @GetMapping(value = APP_ROOT+"/profileImage/user_email/{userEmail}",produces = MediaType.IMAGE_PNG_VALUE)
    @Operation(summary = "search one profile image", description = "search profile image with the specified user ID ")
    @ApiResponses(value = {
            @ApiResponse(responseCode  = "200",description = "profile image found"),
            @ApiResponse(responseCode = "404",description = "profile image not found !!!")
    })
    public byte[] findByUserEmail(@PathVariable("userEmail") String email);
}
