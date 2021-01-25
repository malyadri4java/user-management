package com.dailycode.user.management.model;

import com.dailycode.user.management.util.TimeUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Setter
@Getter
@Entity
@Table (name = "confirmation_token")
public class ConfirmationToken {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column (name = "token_id")
    private long tokenId;

    @Column (name = "token")
    private String confirmationToken;

    @Column (name = "created_date")
    private String createdDate;

    @OneToOne (targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn (nullable = false, name = "user_id")
    private User user;

    @Column (name = "expiry_date")
    private String expiryDate;

    public ConfirmationToken() {
    }

    public ConfirmationToken(User user, String token) {
        this.user = user;
        this.createdDate = TimeUtil.dateAndTimeAsString();
        this.confirmationToken = token;
        this.expiryDate = calculateExpiryDate(30);
    }

    private String calculateExpiryDate(int expiryTimeInMinutes) {
        return TimeUtil.dateAndTimeAsString(LocalDateTime.now().plusMinutes(expiryTimeInMinutes));
    }

    public boolean isExpiryDate() {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(TimeUtil.toLocalDateTime(expiryDate));
    }
}