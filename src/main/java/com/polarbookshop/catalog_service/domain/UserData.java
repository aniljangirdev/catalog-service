package com.polarbookshop.catalog_service.domain;

import jakarta.validation.constraints.NotNull;
import org.postgresql.jdbc.PgBlob;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;

import java.sql.Blob;
import java.time.Instant;

public record UserData(

        @Id
        Long id,

        @NotNull(message = "username is required!")
        String username,

        @NotNull(message = "emailId is required!")
        String emailId,

        byte[] image_pic,

        @CreatedDate
        Instant createdDate,

        @LastModifiedDate
        Instant lastModifiedDate,
        @Version
        Integer version
) {

        public static UserData of(String username, String emailId, byte[] image_pic){
                return new UserData(null,username, emailId, image_pic, null, null, null);
        }

}
