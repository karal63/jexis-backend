package com.jexis.jexis_backend.member.presentation;

import java.util.List;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jexis.jexis_backend.common.dtoHelpers.DtoHelper;
import com.jexis.jexis_backend.member.application.dto.CreateMemberDto;
import com.jexis.jexis_backend.member.application.dto.EditMemberDto;
import com.jexis.jexis_backend.member.application.dto.MemberResponseDto;
import com.jexis.jexis_backend.member.application.useCases.AddMemberUseCase;
import com.jexis.jexis_backend.member.application.useCases.EditMemberUseCase;
import com.jexis.jexis_backend.member.application.useCases.GetAccountMembersUseCase;
import com.jexis.jexis_backend.member.application.useCases.GetMemberUseCase;
import com.jexis.jexis_backend.member.application.useCases.GetMembersUseCase;
import com.jexis.jexis_backend.member.application.useCases.RemoveMemberUseCase;
import com.jexis.jexis_backend.member.domain.entities.Member;

@RestController
@RequestMapping("/")
public class MemberController {
    private final GetMemberUseCase getMemberUseCase;
    private final GetMembersUseCase getMembersUseCase;
    private final AddMemberUseCase addMemberUseCase;
    private final EditMemberUseCase editMemberUseCase;
    private final RemoveMemberUseCase removeMemberUseCase;
    private final DtoHelper dtoHelper;
    private final GetAccountMembersUseCase getAccountMembersUseCase;

    public MemberController(
            GetMemberUseCase getMemberUseCase,
            GetMembersUseCase getMembersUseCase,
            AddMemberUseCase addMemberUseCase,
            EditMemberUseCase editMemberUseCase,
            RemoveMemberUseCase removeMemberUseCase,
            DtoHelper dtoHelper,
            GetAccountMembersUseCase getAccountMembersUseCase) {
        this.getMemberUseCase = getMemberUseCase;
        this.getMembersUseCase = getMembersUseCase;
        this.addMemberUseCase = addMemberUseCase;
        this.editMemberUseCase = editMemberUseCase;
        this.removeMemberUseCase = removeMemberUseCase;
        this.dtoHelper = dtoHelper;
        this.getAccountMembersUseCase = getAccountMembersUseCase;
    }

    // ADMIN GLOBAL
    @GetMapping("/list")
    public List<MemberResponseDto> listAccountMembers() {
        List<Member> members = getMembersUseCase.execute();
        return members.stream().map(dtoHelper::toMemberDto).toList();
    }

    // @GetMapping("/list/{accountId}/{id}")
    // public MemberResponseDto get(@PathVariable UUID accountId, @PathVariable UUID
    // id) {
    // Member member = getMemberUseCase.execute(id);
    // return dtoHelper.toMemberDto(member);
    // }

    @PostMapping("/member/add")
    @PreAuthorize("""
            @hasRoleUseCase.execute(authentication.principal.id(), #body.accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).OWNER)
            or
            @hasRoleUseCase.execute(authentication.principal.id(), #body.accountId, T(com.jexis.jexis_backend.member.domain.enums.Role).ADMIN)
            """)
    public MemberResponseDto add(@RequestBody CreateMemberDto body) {
        Member member = addMemberUseCase.execute(body);
        return dtoHelper.toMemberDto(member);
    }

    @GetMapping("/accounts/{id}/members")
    @PreAuthorize("@canAccessUseCase.execute(authentication.principal.id(), #id)")
    public List<MemberResponseDto> getMembersByAccount(@PathVariable UUID id) {
        List<Member> member = getAccountMembersUseCase.execute(id);
        return member.stream().map(dtoHelper::toMemberDto).toList();
    }

    // @PatchMapping("/edit/{id}")
    // @PreAuthorize("@memberAuthorization.canEdit(authentication.principal.id(),
    // #id)")
    // public MemberResponseDto edit(@PathVariable UUID id, @RequestBody
    // EditMemberDto body) {
    // Member member = editMemberUseCase.execute(id, body);
    // return dtoHelper.toMemberDto(member);
    // }

    // @PostMapping("/remove/{id}")
    // @PreAuthorize("@memberAuthorization.canEdit(authentication.principal.id(),
    // #id)")
    // public void remove(@PathVariable UUID id) {
    // removeMemberUseCase.execute(id);
    // }
}
