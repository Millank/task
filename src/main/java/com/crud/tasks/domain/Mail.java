package com.crud.tasks.domain;

import com.crud.tasks.service.MailMessageType;
import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Mail {
    @NonNull
    private MailMessageType messageType;

    @NonNull
    private String receiverEmail;

    @NonNull
    private String subject;

    @NonNull
    private String message;

    private String toCc;
}
