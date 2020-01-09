package com.subbu.boot.vertx.entities;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Entity
@Table(name="AUTHORS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/**
 * Need this annotation for the json serialization.
 * Please refer - https://stackoverflow.com/questions/26957554/jsonmappingexception-could-not-initialize-proxy-no-session
 */
@Proxy(lazy = false)
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("email")
    private String email;

    @SerializedName("gender")
    private String gender;

    @SerializedName("ip_address")
    private String ipAddress;
}
