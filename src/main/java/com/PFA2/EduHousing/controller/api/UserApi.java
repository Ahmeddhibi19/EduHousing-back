package com.PFA2.EduHousing.controller.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.PFA2.EduHousing.Utils.Constants.APP_ROOT;

@Tag(name = "user")
@RequestMapping("/api")
public interface UserApi {
    @GetMapping(value = APP_ROOT+"/admin/total_number_user",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "return total number of users... ",description = "return total number of users .."/*,response = Admindto.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "users found"),
            @ApiResponse(responseCode = "404",description = "something went wrong!!!")
    })
    public Integer totalUser();
    @DeleteMapping(value = APP_ROOT+"/user/admin/delete_by_id/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "delete user by id... ",description = "needs an existing user id.."/*response = Void.class*/)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "user deleted successfully"),
            @ApiResponse(responseCode = "400",description = "something went wrong !!!")
    })
    public String deleteById(@PathVariable("id") Integer id);

}
