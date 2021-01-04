package net.jackbauer.lombak;

import lombok.Data;

import java.util.Date;

/*
    @Data : @ToString, @EqualsAndHashCode, @Getter, @Setter
    @NotNull
    @Cleanup
    @Synchronized
    @SneakyThrows
 */

@Data
public class Person {
    private final String firstName;
    private final String lastName;
    private String address;
    private String state;
    private String zip;
    private boolean employed;
    private final Date dateofBirth;
}
