package com.jexis.jexis_backend.common.dtoHelpers;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.dto.AccountResponseDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.card.application.dto.CardResponseDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.cardholder.application.dto.CardHolderResponseDto;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.member.application.dto.MemberResponseDto;
import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.user.application.dto.UserResponseDto;
import com.jexis.jexis_backend.user.domain.entities.User;
import com.jexis.jexis_backend.wallet.application.dto.WalletResponseDto;
import com.jexis.jexis_backend.wallet.domain.entities.Wallet;

@Service
public class DtoHelper {
    public UserResponseDto toUserDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRoles(),
                user.getIsActivated(),
                user.getCreatedAt(),
                user.getUpdatedAt());
    }

    public AccountResponseDto toAccountDto(Account account) {
        return new AccountResponseDto(
                account.getId(),
                account.getName(),
                account.getEmail(),
                account.getConnectAccountId(),
                account.getAccountLink(),
                toUserDto(account.getOwner()),
                account.getCreatedAt(),
                account.getUpdatedAt());
    }

    public CardHolderResponseDto toCardHolderDto(CardHolder cardHolder) {
        return new CardHolderResponseDto(
                cardHolder.getId(),
                toAccountDto(cardHolder.getAccount()),
                toUserDto(cardHolder.getUser()),
                cardHolder.getName(),
                cardHolder.getAddressLine1(),
                cardHolder.getCity(),
                cardHolder.getState(),
                cardHolder.getCountry(),
                cardHolder.getPostalCode(),
                cardHolder.getCreatedAt(),
                cardHolder.getUpdatedAt());
    }

    public WalletResponseDto toWalletDto(Wallet wallet) {
        return new WalletResponseDto(
                wallet.getId(),
                wallet.getName(),
                toAccountDto(wallet.getAccount()),
                wallet.getAvailableBalance(),
                wallet.getCreatedAt(),
                wallet.getUpdatedAt());
    }

    public CardResponseDto toCardDto(Card card) {
        return new CardResponseDto(
                card.getId(),
                toCardHolderDto(card.getCardHolder()),
                toWalletDto(card.getTreasuryAccount()),
                toUserDto(card.getUser()),
                card.getLast4(),
                card.getStatus(),
                card.getSpendingLimits(),
                card.getBrand(),
                card.getType(),
                card.getCurrency(),
                card.getExpYear(),
                card.getCreatedAt());
    }

    public MemberResponseDto toMemberDto(Member member) {
        System.out.println(member.toString());
        return new MemberResponseDto(
                member.getId(),
                toAccountDto(member.getAccount()),
                toUserDto(member.getUser()),
                member.getRole());
    }
}
