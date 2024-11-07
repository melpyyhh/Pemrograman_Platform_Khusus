package com.melpy.perpus.repository;

import com.melpy.perpus.entity.Member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "member", path = "member")
public interface MemberRepository extends PagingAndSortingRepository<Member, Long>, CrudRepository<Member, Long> {
        @Operation(summary = "Find members by name")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "List of members", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access Forbidden", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Members not found", content = @Content)
        })
        List<Member> findByName(@Param("name") String name);

        @Operation(summary = "Find members by ID")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Member found", content = {
                                        @Content(mediaType = "application/json", schema = @Schema(implementation = Member.class)) }),
                        @ApiResponse(responseCode = "403", description = "Access Forbidden", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Member not found", content = @Content)
        })
        List<Member> findByMemberID(@Param("member_id") String memberID);
}