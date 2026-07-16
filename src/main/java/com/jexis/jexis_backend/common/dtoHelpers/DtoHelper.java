package com.jexis.jexis_backend.common.dtoHelpers;

import org.springframework.stereotype.Service;

import com.jexis.jexis_backend.account.application.dto.AccountResponseDto;
import com.jexis.jexis_backend.account.domain.entities.Account;
import com.jexis.jexis_backend.authorization.application.dto.AuthorizationResponseDto;
import com.jexis.jexis_backend.authorization.domain.entities.Authorization;
import com.jexis.jexis_backend.card.application.dto.CardResponseDto;
import com.jexis.jexis_backend.card.domain.entities.Card;
import com.jexis.jexis_backend.cardholder.application.dto.CardHolderResponseDto;
import com.jexis.jexis_backend.cardholder.domain.entities.CardHolder;
import com.jexis.jexis_backend.member.application.dto.MemberResponseDto;
import com.jexis.jexis_backend.member.domain.entities.Member;
import com.jexis.jexis_backend.transaction.application.dto.TransactionResponseDto;
import com.jexis.jexis_backend.transaction.domain.entities.Transaction;
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
                account.getFirstName(),
                account.getLastName(),
                account.getCity(),
                account.getCountry(),
                account.getLine1(),
                account.getLine2(),
                account.getPostalCode(),
                account.getState(),
                account.getPhone(),
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
                cardHolder.getBillingAddressLine1(),
                cardHolder.getBillingAddressLine2(),
                cardHolder.getBillingCity(),
                cardHolder.getBillingState(),
                cardHolder.getBillingCountry(),
                cardHolder.getBillingPostalCode(),
                cardHolder.getSpendingLimits(),
                cardHolder.getStatus(),
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
        return new MemberResponseDto(
                member.getId(),
                toAccountDto(member.getAccount()),
                toUserDto(member.getUser()),
                member.getRole());
    }

    public TransactionResponseDto toTransactionDto(Transaction transaction) {
        return new TransactionResponseDto(
                transaction.getId(),
                toWalletDto(transaction.getWallet()),
                transaction.getStripeTransactionId(),
                transaction.getStripeObjectId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCurrency(),
                transaction.getStatus(),
                transaction.getDirection(),
                transaction.getBankName(),
                transaction.getBankAccountLast4(),
                transaction.getRoutingNumber(),
                transaction.getPaymentMethod(),
                transaction.getCard() != null ? toCardDto(transaction.getCard()) : null,
                transaction.getMerchantName(),
                transaction.getMerchantCategory(),
                transaction.getMerchantCity(),
                transaction.getMerchantCountry(),
                transaction.getCreatedAt());
    }

    public AuthorizationResponseDto toAuthorizationDto(Authorization authorization) {
        return new AuthorizationResponseDto(
                authorization.getId(),
                toWalletDto(authorization.getWallet()),
                authorization.getStripeAuthorizationId(),
                toCardDto(authorization.getCard()),
                authorization.getApproved(),
                authorization.getAmount(),
                authorization.getCurrency(),
                authorization.getStatus(),
                authorization.getMerchantName(),
                authorization.getMerchantCategory(),
                authorization.getMerchantCity(),
                authorization.getMerchantCountry(),
                authorization.getCreatedAt());
    }
}
