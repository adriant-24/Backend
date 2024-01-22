package com.shop.coreutils.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class EmailDetails {

    String recepient;

    String subject;

    String body;
}
